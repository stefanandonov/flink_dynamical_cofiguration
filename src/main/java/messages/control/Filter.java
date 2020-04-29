
package messages.control;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import messages.data.InputMessage;

public class Filter implements Serializable
{

    private static final String EQUAL = "EQ";
    private static final String CONTAINS = "CONTAINS";
    private static final String NOT_EQUAL = "NOT_EQ";

    @SerializedName("filter_property")
    @Expose
    private String filterProperty;
    @SerializedName("operator")
    @Expose
    private String operator;
    @SerializedName("filter_value")
    @Expose
    private String filterValue;
    private final static long serialVersionUID = 8688035991299657291L;

    public String getFilterProperty() {
        return filterProperty;
    }

    public void setFilterProperty(String filterProperty) {
        this.filterProperty = filterProperty;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getFilterValue() {
        return filterValue;
    }

    public void setFilterValue(String filterValue) {
        this.filterValue = filterValue;
    }

    boolean checkFilter (InputMessage inputMessage) {
        String value = null;

        if (inputMessage.hasField(filterProperty))
            value = inputMessage.getFieldValue(filterProperty);
        else
            return false;

        switch (operator) {
            case EQUAL:
                return value.equals(filterValue);
            case CONTAINS:
                return value.contains(filterValue);
            case NOT_EQUAL:
                return !value.equals(filterValue);
        }
        return false;
    }


    public  String getFilterDescription() {
        if (operator.equals(EQUAL))
            return "Field "+ filterProperty + " should be EQUAL with: " + filterValue;
        if (operator.equals(CONTAINS))
            return "Field "+ filterProperty + " should CONTAIN the substring: " + filterValue;
        if (operator.equals(NOT_EQUAL))
            return "Field "+ filterProperty + " should NOT be EQUAL with: " + filterValue;
        else
            return null;
    }
}
