
package messages.control;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AggregationConfig implements Serializable
{

    @SerializedName("aggregation_property")
    @Expose
    private String aggregationProperty;
    @SerializedName("aggregation_type")
    @Expose
    private String aggregationType;
    private final static long serialVersionUID = 2430269706472287102L;

    public String getAggregationProperty() {
        return aggregationProperty;
    }

    public void setAggregationProperty(String aggregationProperty) {
        this.aggregationProperty = aggregationProperty;
    }

    public String getAggregationType() {
        return aggregationType;
    }

    public void setAggregationType(String aggregationType) {
        this.aggregationType = aggregationType;
    }



}
