package test.sources;

import messages.data.InputMessage;
import org.apache.flink.streaming.api.functions.source.SourceFunction;

public class DataSource implements SourceFunction<InputMessage> {
    @Override
    public void run(SourceContext<InputMessage> sourceContext) throws Exception {
        //TODO read from a txt file with json-s that are the subject to the analysis
    }

    @Override
    public void cancel() {

    }
}
