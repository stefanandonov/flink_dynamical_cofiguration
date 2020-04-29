package factories;

import messages.control.ControlMessage;
import messages.data.InputMessage;
import messages.schema.ControlMessageSchema;
import messages.schema.InputMessageSchema;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;

import javax.sound.midi.ControllerEventListener;
import java.util.regex.Pattern;

import static project_settings.ProjectSettings.*;

public class KafkaStreamGenerator {

    public static DataStream<ControlMessage> getStreamControl(ParameterTool parameterTool, StreamExecutionEnvironment env) {
        String regexTopic = parameterTool.get(DEFAULT_CONTROL_TOPIC_NAME, DEFAULT_CONTROL_TOPIC);
        Pattern pattern = Pattern.compile(regexTopic);

        FlinkKafkaConsumer<ControlMessage> kafkaConsumer = new
                FlinkKafkaConsumer<>(pattern,
                new ControlMessageSchema(),
                parameterTool.getProperties());

        int parallelism = parameterTool.getInt(DEFAULT_KAFKA_CONTROL_PARALLELISM_NAME, DEFAULT_KAFKA_CONTROL_PARALLELISM);
        return env.addSource(kafkaConsumer).setParallelism(parallelism);
    }

    public static DataStream<InputMessage> getStreamData(ParameterTool parameterTool, StreamExecutionEnvironment env) {
        String regexTopic = parameterTool.get(DEFAULT_CONTROL_TOPIC_NAME, DEFAULT_CONTROL_TOPIC);
        Pattern pattern = Pattern.compile(regexTopic);

        FlinkKafkaConsumer<InputMessage> kafkaConsumer = new
                FlinkKafkaConsumer<>(pattern,
                new InputMessageSchema(),
                parameterTool.getProperties());

        int parallelism = parameterTool.getInt(DEFAULT_KAFKA_DATA_PARALLELISM_NAME, DEFAULT_KAFKA_DATA_PARALLELISM);
        return env.addSource(kafkaConsumer).setParallelism(parallelism);
    }
}
