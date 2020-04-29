package factories;

import messages.data.InputMessage;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import test.sources.DataSource;

import static project_settings.ProjectSettings.TEST_MODE;

public class SourceDataStreamFactory {
    public static DataStream<InputMessage> getStream(ParameterTool parameterTool, StreamExecutionEnvironment env) {
        boolean testMode = parameterTool.getBoolean(TEST_MODE, true);

        if (testMode) {
            return env.addSource(new DataSource());
        } else {
            return KafkaStreamGenerator.getStreamData(parameterTool, env);
        }
    }
}
