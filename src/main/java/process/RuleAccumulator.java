package process;


import com.tdunning.math.stats.TDigest;
import com.tdunning.math.stats.TreeDigest;
import messages.MatchedMessage;
import messages.control.Rule;
import messages.data.InputMessage;
import messages.output.RuleOutputMessage;
import org.apache.flink.api.java.tuple.Tuple2;

import java.util.*;
import java.util.stream.Collectors;

interface IStatisticalSetter {
    void setStatistic();
}

public class RuleAccumulator {

    private DoubleSummaryStatistics summaryStatistics;
    private TDigest tDigest;

    private static final double MEDIAN = 0.5;
    private static final double Q1 = 0.25;
    private static final double Q3 = 0.75;
    private static final double DIGEST_COMPRESSION = 100;


    private String statsOutputTopic;
    private List<String> necessaryStatistics;
    private String ruleId;
    private String userId;
    private List<String> ruleDescription;
    private StatisticsBuilder statisticsBuilder;

    public RuleAccumulator() {
        summaryStatistics = new DoubleSummaryStatistics();
        tDigest = new TreeDigest(DIGEST_COMPRESSION);
        statisticsBuilder = new StatisticsBuilder();
    }

    public RuleAccumulator add(MatchedMessage matchedMessage) {
        Rule rule = matchedMessage.getRule();
        InputMessage inputData = matchedMessage.getInputMessage();

        if (userId == null)
            userId = matchedMessage.getUserId();

        double value = Double.parseDouble(inputData.getFieldValue(rule.getAggregationConfig().getAggregationProperty()));

        summaryStatistics.accept(value);
        tDigest.add(value);

        if (statsOutputTopic == null)
            this.statsOutputTopic = rule.getRuleOutputTopic();

        if (this.ruleId == null)
            ruleId = rule.getRuleid();

        if (this.ruleDescription == null) {
            ruleDescription = rule.getRulesDescription();
        }

        if (this.necessaryStatistics == null) {
            this.necessaryStatistics = Arrays.stream(rule.getAggregationConfig()
                    .getAggregationType()
                    .split(",")
            )
                    .collect(Collectors.toList());
        }

        return this;
    }

    public Tuple2<RuleOutputMessage, String> getResult() {

        Map<String, IStatisticalSetter> dict = new LinkedHashMap<>();
        dict.put("count", () -> statisticsBuilder.setCount(summaryStatistics.getCount()));
        dict.put("min", () -> statisticsBuilder.setMin(summaryStatistics.getMin()));
        dict.put("max", () -> statisticsBuilder.setMax(summaryStatistics.getMax()));
        dict.put("median", () -> statisticsBuilder.setMedian(tDigest.quantile(MEDIAN)));
        dict.put("average", () -> statisticsBuilder.setAverage(summaryStatistics.getAverage()));
        dict.put("q1", () -> statisticsBuilder.setQ1(tDigest.quantile(Q1)));
        dict.put("q3", () -> statisticsBuilder.setQ3(tDigest.quantile(Q3)));

        dict.forEach((type, setter) -> {
            if (necessaryStatistics.contains(type))
                setter.setStatistic();
        });

        String statisticsJson = statisticsBuilder.getResult();
        if (statisticsJson == null)
            return null;
        else return Tuple2.of(
                new RuleOutputMessage(userId, ruleId, statisticsBuilder.getResult(), ruleDescription),
                statsOutputTopic
        );
    }

    public RuleAccumulator merge(RuleAccumulator acc1) {
        this.summaryStatistics.combine(acc1.summaryStatistics);
        this.tDigest.add(acc1.tDigest);
        return this;
    }
}
