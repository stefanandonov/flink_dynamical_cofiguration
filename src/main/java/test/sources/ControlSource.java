package test.sources;

import messages.control.ControlMessage;
import org.apache.flink.streaming.api.functions.source.SourceFunction;

public class ControlSource implements SourceFunction<ControlMessage> {
    @Override
    public void run(SourceContext<ControlMessage> sourceContext) throws Exception {
        //TODO read from a txt file with json control messages created by myself
    }

    @Override
    public void cancel() {

    }
}
