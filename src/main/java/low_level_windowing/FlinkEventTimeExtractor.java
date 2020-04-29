package low_level_windowing;


import high_level_windowing.IConfigurableWindow;
import org.apache.flink.streaming.api.functions.KeyedProcessFunction;

public class FlinkEventTimeExtractor <T extends IConfigurableWindow>
        extends FlinkTimeExtractor<T> {

    @Override
    public long getTimestampFromElement(T element, KeyedProcessFunction.Context context) {
        return element.getTimestamp();
    }

    @Override
    public long getCurrentFlinkTime(KeyedProcessFunction.Context context) {
        return context.timerService().currentWatermark();
    }
}
