import factories.SourceControlStreamFactory;
import factories.SourceDataStreamFactory;
import low_level_windowing.WindowingProcessFunctionImpl;
import messages.MatchedMessage;
import messages.TimestampsAndWatermarksAssigner;
import messages.control.ControlMessage;
import messages.data.InputMessage;
import messages.output.RuleOutputMessage;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.BroadcastStream;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.ProcessFunction;
import org.apache.flink.util.Collector;
import process.*;
import project_settings.ProjectSettings;
import states.StateDescriptors;
import high_level_windowing.GenericWindowAssigner;

import java.util.Collection;
import java.util.Objects;

public class MainClass {

    public static void main(String[] args) throws Exception {

        final ParameterTool parameterTool = ParameterTool.fromArgs(args);

        // set up the execution environment
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();


        env.setParallelism(parameterTool.getInt(ProjectSettings.DEFAULT_PROCESS_PARALLELISM_NAME,
                ProjectSettings.DEFAULT_PROCESS_PARALLELISM));

        DataStream<InputMessage> dataStream = SourceDataStreamFactory.getStream(parameterTool, env);
        DataStream<ControlMessage> controlStream = SourceControlStreamFactory.getStream(parameterTool, env);

        boolean eventTime = parameterTool.getBoolean(ProjectSettings.EVENT_TIME_PARAMETER, ProjectSettings.DEFAULT_EVENT_TIME);
        if (eventTime) {
            env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
            dataStream = TimestampsAndWatermarksAssigner.assignProtocolMessagesTimestamps(parameterTool, dataStream);
            controlStream = TimestampsAndWatermarksAssigner.assignControlMessagesTimestamps(controlStream);
        }

        BroadcastStream<ControlMessage> broadcastStream =
                controlStream.broadcast(StateDescriptors.CONTROL_MESSAGE_BROADCAST_STATE_DESCRIPTOR);

        DataStream<MatchedMessage> matchedMessages = dataStream.connect(broadcastStream).process(new MatchingProcess());

        DataStream<Tuple2<RuleOutputMessage, String>> outputStream = matchedMessages
                .keyBy(new MatchedMessageKeySelector())
                .window(new GenericWindowAssigner<>(eventTime))
                .aggregate(new RuleAggregationFunction(), new WindowTimestampAssigner())
                .filter(Objects::nonNull);

        DataStream<Tuple2<RuleOutputMessage, String>> stream = matchedMessages.keyBy(new MatchedMessageKeySelector())
                .process(new WindowingProcessFunctionImpl(eventTime))
                .process(new AggregateProcessFunction());



        env.execute("real time data analysys");

    }
}
