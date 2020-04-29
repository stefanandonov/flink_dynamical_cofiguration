package messages.output;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RuleOutputMessage {

    @SerializedName("user_id")
    private String userId;

    @SerializedName("rule_id")
    private String ruleId;

    @SerializedName("timestamp")
    private Long timestamp;

    @SerializedName("stats")
    private String stats;

    @SerializedName("rule_description")
    private List<String> ruleDescription;

    public RuleOutputMessage(String userId, String ruleId, String stats, List<String> ruleDescription) {
        this.userId = userId;
        this.ruleId = ruleId;
        this.timestamp = timestamp;
        this.stats = stats;
        this.ruleDescription = ruleDescription;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this,RuleOutputMessage.class);
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
