
package messages.control;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import messages.data.InputMessage;


public class Rule implements Serializable
{

    @SerializedName("ruleid")
    @Expose
    private String ruleid;
    @SerializedName("filters")
    @Expose
    private List<Filter> filters = new ArrayList<Filter>();
    @SerializedName("rule_output_topic")
    @Expose
    private String ruleOutputTopic;
    @SerializedName("aggregation_config")
    @Expose
    private AggregationConfig aggregationConfig;
    @SerializedName("window_config")
    @Expose
    private WindowConfig windowConfig;
    private final static long serialVersionUID = -7200391488940784690L;

    public String getRuleid() {
        return ruleid;
    }

    public void setRuleid(String ruleid) {
        this.ruleid = ruleid;
    }

    public List<Filter> getFilters() {
        return filters;
    }

    public void setFilters(List<Filter> filters) {
        this.filters = filters;
    }

    public String getRuleOutputTopic() {
        return ruleOutputTopic;
    }

    public void setRuleOutputTopic(String ruleOutputTopic) {
        this.ruleOutputTopic = ruleOutputTopic;
    }

    public AggregationConfig getAggregationConfig() {
        return aggregationConfig;
    }

    public void setAggregationConfig(AggregationConfig aggregationConfig) {
        this.aggregationConfig = aggregationConfig;
    }

    public WindowConfig getWindowConfig() {
        return windowConfig;
    }

    public void setWindowConfig(WindowConfig windowConfig) {
        this.windowConfig = windowConfig;
    }


    public boolean checkRule(InputMessage inputMessage) {
        return filters.stream().allMatch(filter -> filter.checkFilter(inputMessage));
    }

    public List<String> getRulesDescription() {
        return filters.stream()
                .map(Filter::getFilterDescription)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

    }
}
