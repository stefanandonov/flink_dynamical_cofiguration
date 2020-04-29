package high_level_windowing;

import org.apache.flink.streaming.api.windowing.triggers.EventTimeTrigger;
import org.apache.flink.streaming.api.windowing.triggers.ProcessingTimeTrigger;
import org.apache.flink.streaming.api.windowing.triggers.Trigger;

public class DynamicalTrigger {

    public static <T extends IConfigurableWindow> Trigger of(boolean eventTime) {
        if (eventTime) {
            return EventTimeTrigger.create();
        }
        else {
            return ProcessingTimeTrigger.create();
        }
    }

//    @Override
//    public abstract TriggerResult onElement(T element, long timestamp, TimeWindow window, TriggerContext ctx) throws Exception;
//
//    @Override
//    public abstract TriggerResult onProcessingTime(long time, TimeWindow window, TriggerContext ctx) throws Exception;
//
//    @Override
//    public abstract TriggerResult onEventTime(long time, TimeWindow window, TriggerContext ctx) throws Exception;
//
//    @Override
//    public abstract void clear(TimeWindow window, TriggerContext ctx) throws Exception;
//
//    @Override
//    public boolean canMerge() {
//        return true;
//    }
//
//    @Override
//    public abstract void onMerge(TimeWindow window, OnMergeContext ctx) throws Exception;


}
