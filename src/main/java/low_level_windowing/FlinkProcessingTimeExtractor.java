package low_level_windowing;

import high_level_windowing.IConfigurableWindow;
import org.apache.flink.streaming.api.functions.KeyedProcessFunction;

public class FlinkProcessingTimeExtractor <T extends IConfigurableWindow>
        extends FlinkTimeExtractor<T>{
    @Override
    public long getTimestampFromElement(T element, KeyedProcessFunction.Context context) {
        return context.timerService().currentProcessingTime();
    }

    @Override
    public long getCurrentFlinkTime(KeyedProcessFunction.Context context) {
        return context.timerService().currentProcessingTime();
    }
}
