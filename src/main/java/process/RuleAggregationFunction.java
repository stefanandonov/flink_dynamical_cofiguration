package process;

import messages.MatchedMessage;
import messages.output.RuleOutputMessage;
import org.apache.flink.api.common.functions.AggregateFunction;
import org.apache.flink.api.java.tuple.Tuple2;

public class RuleAggregationFunction implements AggregateFunction<MatchedMessage, RuleAccumulator, Tuple2<RuleOutputMessage, String>> {


    @Override
    public RuleAccumulator createAccumulator() {
        return new RuleAccumulator();
    }

    @Override
    public RuleAccumulator add(MatchedMessage matchedMessage, RuleAccumulator matchedMessageAccumulator) {
        return matchedMessageAccumulator.add(matchedMessage);
    }

    @Override
    public Tuple2<RuleOutputMessage, String> getResult(RuleAccumulator matchedMessageAccumulator) {
        return matchedMessageAccumulator.getResult();
    }

    @Override
    public RuleAccumulator merge(RuleAccumulator matchedMessageAccumulator, RuleAccumulator acc1) {
        return matchedMessageAccumulator.merge(acc1);
    }
}
