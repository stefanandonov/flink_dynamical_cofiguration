package high_level_windowing;

import org.apache.flink.api.common.ExecutionConfig;
import org.apache.flink.api.common.typeutils.TypeSerializer;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.WindowAssigner;
import org.apache.flink.streaming.api.windowing.triggers.Trigger;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;

import java.util.Collection;

public class GenericWindowAssigner<T extends IConfigurableWindow<E>,E> extends WindowAssigner<T, TimeWindow> {
    private boolean eventTime;
    private static final String TUMBLING = "tumbling";
    private static final String SLIDING = "sliding";

    public GenericWindowAssigner(boolean eventTime) {
        this.eventTime = eventTime;
    }

    @Override
    public Collection<TimeWindow> assignWindows(T element, long timestamp, WindowAssignerContext windowAssignerContext) {
        String windowType = element.getWindowType();
        if (!eventTime) {
            timestamp = windowAssignerContext.getCurrentProcessingTime();
        } else if (timestamp == Long.MIN_VALUE) {
            throw new RuntimeException("Record has Long.MIN_VALUE timestamp (= no timestamp marker). " +
                    "Is the time characteristic set to 'ProcessingTime', or did you forget to call " +
                    "'DataStream.assignTimestampsAndWatermarks(...)'?");
        }

        return TimeWindowCollectionFactory.create(element, timestamp, windowType);    }

    @Override
    public Trigger<T, TimeWindow> getDefaultTrigger(StreamExecutionEnvironment streamExecutionEnvironment) {
        return DynamicalTrigger.of(eventTime);
    }

    @Override
    public TypeSerializer<TimeWindow> getWindowSerializer(ExecutionConfig executionConfig) {
        return new TimeWindow.Serializer();
    }

    @Override
    public boolean isEventTime() {
        return false;
    }
}
