package low_level_windowing;


import high_level_windowing.IConfigurableWindow;

public abstract class FlinkTimeExtractor <T extends IConfigurableWindow>
        implements IFlinkTimeExtractor<T> {

    public  static <T extends IConfigurableWindow> FlinkTimeExtractor<T> of (boolean eventTime) {
        if (eventTime)
            return new FlinkEventTimeExtractor<T>();
        else
            return new FlinkProcessingTimeExtractor<T>();
    }
}
