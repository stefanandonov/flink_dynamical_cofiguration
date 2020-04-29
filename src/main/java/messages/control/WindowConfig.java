
package messages.control;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WindowConfig implements Serializable
{

    @SerializedName("window_type")
    @Expose
    private String windowType;
    @SerializedName("window_size_unit")
    @Expose
    private String windowSizeUnit;
    @SerializedName("window_size")
    @Expose
    private Long windowSize;
    @SerializedName("window_slide")
    @Expose
    private Long windowSlide;
    private final static long serialVersionUID = -4516704536853314400L;

    public String getWindowType() {
        return windowType;
    }

    public void setWindowType(String windowType) {
        this.windowType = windowType;
    }

    public String getWindowSizeUnit() {
        return windowSizeUnit;
    }

    public void setWindowSizeUnit(String windowSizeUnit) {
        this.windowSizeUnit = windowSizeUnit;
    }

    public Long getWindowSize() {
        return windowSize;
    }

    public void setWindowSize(Long windowSize) {
        this.windowSize = windowSize;
    }

    public Long getWindowSlide() {
        return windowSlide;
    }

    public void setWindowSlide(Long windowSlide) {
        this.windowSlide = windowSlide;
    }

}
