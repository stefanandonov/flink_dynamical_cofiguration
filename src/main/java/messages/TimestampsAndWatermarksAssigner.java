package messages;

import messages.control.ControlMessage;
import messages.data.InputMessage;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.functions.AssignerWithPunctuatedWatermarks;
import org.apache.flink.streaming.api.functions.timestamps.AscendingTimestampExtractor;
import org.apache.flink.streaming.api.watermark.Watermark;
import project_settings.ProjectSettings;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;

public class TimestampsAndWatermarksAssigner {
    public static DataStream<InputMessage> assignProtocolMessagesTimestamps(ParameterTool parameterTool, DataStream<InputMessage> dataStream) {
        Long maxOutOfOrderliness = parameterTool.getLong(ProjectSettings.DEFAULT_OUT_OF_ORDER_TIME_NAME, ProjectSettings.DEFAULT_OUT_OF_ORDER_TIME);

        String timestampProperty = parameterTool.get(ProjectSettings.TIMESTAMP_PROPERTY_NAME, ProjectSettings.DEFAULT_TIMESTAMP_PROPERTY);
        return dataStream
                .map(s -> s)
                .assignTimestampsAndWatermarks(new BoundedOutOfOrdernessPunctuatedWatermark<InputMessage>(maxOutOfOrderliness) {
                    @Override
                    public long extractTimestamp(InputMessage element) {

                        ZonedDateTime zonedDateTime = ZonedDateTime.parse(element.getFieldValue(timestampProperty));
                        element.setTimestampMs(zonedDateTime.toInstant().toEpochMilli());
                        return zonedDateTime.toInstant().toEpochMilli();

                    };
                });
    }

    public static DataStream<ControlMessage> assignControlMessagesTimestamps(DataStream<ControlMessage> controlStream) {
        return controlStream
                .map(s -> s)
                .assignTimestampsAndWatermarks(new AscendingTimestampExtractor<ControlMessage>() {
                    @Override
                    public long extractAscendingTimestamp(ControlMessage element) {
                        return Long.MAX_VALUE;
                    }
                });
    }
}
