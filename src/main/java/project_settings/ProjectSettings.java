package project_settings;

public class ProjectSettings {
    public static final java.lang.String DEFAULT_PROCESS_PARALLELISM_NAME = "process_parallelism";
    public static final int DEFAULT_PROCESS_PARALLELISM = 4;

    public static final String DEFAULT_DATA_TOPIC_NAME = "data_topic";
    public static final String DEFAULT_DATA_TOPIC = "data_bus";

    public static final String DEFAULT_CONTROL_TOPIC_NAME = "control_topic";
    public static final String DEFAULT_CONTROL_TOPIC = "control_bus";

    public static final String DEFAULT_SINK_TOPIC_NAME = "sink_topic";
    public static final String DEFAULT_SINK_TOPIC = "sink_bus";

    public static final String DEFAULT_KAFKA_DATA_PARALLELISM_NAME = "kafka_data_stream_parallelism";
    public static final int DEFAULT_KAFKA_DATA_PARALLELISM = 1;

    public static final String DEFAULT_KAFKA_CONTROL_PARALLELISM_NAME = "kafka_control_stream_parallelism";
    public static final int DEFAULT_KAFKA_CONTROL_PARALLELISM = 1;

    public static final String EVENT_TIME_PARAMETER = "event_time";
    public static final Boolean DEFAULT_EVENT_TIME = Boolean.TRUE;

    public static final String TIMESTAMP_PROPERTY_NAME = "timestamp_property";
    public static final String DEFAULT_TIMESTAMP_PROPERTY = "stamp";

    public static final String DEFAULT_OUT_OF_ORDER_TIME_NAME = "ordering_window";
    public static final long DEFAULT_OUT_OF_ORDER_TIME = 2 * 1000L ;

    public static final String TEST_MODE = "test";
    public static final String DELIMITER_FOR_KEY_BY = "~~~";
}
