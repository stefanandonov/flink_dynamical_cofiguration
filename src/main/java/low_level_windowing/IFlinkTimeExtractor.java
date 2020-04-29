package low_level_windowing;

import high_level_windowing.IConfigurableWindow;
import org.apache.flink.streaming.api.functions.KeyedProcessFunction;

public interface IFlinkTimeExtractor <T extends IConfigurableWindow> {

    long getTimestampFromElement (T element, KeyedProcessFunction.Context context);

    long getCurrentFlinkTime (KeyedProcessFunction.Context context);

}
