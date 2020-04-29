package factories;

import messages.control.ControlMessage;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import test.sources.ControlSource;

import static project_settings.ProjectSettings.TEST_MODE;

public class SourceControlStreamFactory {

    public static DataStream<ControlMessage> getStream(ParameterTool parameterTool, StreamExecutionEnvironment env) {
        boolean testMode = parameterTool.getBoolean(TEST_MODE, true);
        if (testMode) {
            return env.addSource(new ControlSource());
        } else {
            return KafkaStreamGenerator.getStreamControl(parameterTool, env);
        }
    }
}
