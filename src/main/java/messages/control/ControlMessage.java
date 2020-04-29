
package messages.control;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ControlMessage implements Serializable
{

    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("rules")
    @Expose
    private List<Rule> rules = new ArrayList<Rule>();
    private final static long serialVersionUID = 2450282062009846100L;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<Rule> getRules() {
        return rules;
    }

    public void setRules(List<Rule> rules) {
        this.rules = rules;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this, ControlMessage.class);
    }

    public static ControlMessage of (String input) {
        Gson gson = new Gson();
        return gson.fromJson(input, ControlMessage.class);
    }

//    public static void main(String[] args) {
//        String string = "{\"user_id\":\"User_123\",\"rules\":[{\"ruleid\":\"rule_id\",\"filters\":[{\"filter_property\":\"type\",\"operator\":\"EQ\",\"filter_value\":\"pm10\"},{\"filter_property\":\"value\",\"operator\":\"EQ\",\"filter_value\":\"100\"}],\"rule_output_topic\":\"user123-topic\",\"aggregation_config\":{\"aggregation_property\":\"value\",\"aggregation_type\":\"min, max, sum\"},\"window_config\":{\"window_type\":\"sliding\",\"window_size_unit\":\"ms\",\"window_size\":14400000,\"window_slide\":3600000}}]}";
//        ControlMessage controlMessage = ControlMessage.of(string);
//        System.out.println(controlMessage);
//    }
}
