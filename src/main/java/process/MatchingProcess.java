package process;

import messages.MatchedMessage;
import messages.control.ControlMessage;
import messages.data.InputMessage;
import org.apache.flink.streaming.api.functions.co.BroadcastProcessFunction;
import org.apache.flink.util.Collector;
import org.apache.kafka.common.protocol.types.Field;
import states.StateDescriptors;

import java.util.HashMap;
import java.util.Map;

public class MatchingProcess extends BroadcastProcessFunction<InputMessage, ControlMessage, MatchedMessage> {

    @Override
    public void processElement(InputMessage inputMessage, ReadOnlyContext readOnlyContext, Collector<MatchedMessage> collector) throws Exception {
        if (inputMessage == null)
            return ;

        Iterable<Map.Entry<String, ControlMessage>> iterable = readOnlyContext
                .getBroadcastState(StateDescriptors.CONTROL_MESSAGE_BROADCAST_STATE_DESCRIPTOR)
                .immutableEntries();

        Map<String, ControlMessage> controlMessageByUserMap = createMapFromState(iterable);

        if (controlMessageByUserMap.size()==0)
            return ;

        controlMessageByUserMap.values().stream()
                .flatMap(controlMessage -> controlMessage.getRules()
                        .stream()
                        .filter(rule -> rule.checkRule(inputMessage))
                        .map(rule -> new MatchedMessage(inputMessage, controlMessage.getUserId(), rule)))
                .forEach(collector::collect);
    }

    private Map<String, ControlMessage> createMapFromState(Iterable<Map.Entry<String, ControlMessage>> iterable) {
        Map<String, ControlMessage> result = new HashMap<>();
        for (Map.Entry<String, ControlMessage> entry : iterable) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }

    @Override
    public void processBroadcastElement(ControlMessage controlMessage, Context context, Collector<MatchedMessage> collector) throws Exception {
        if (controlMessage == null)
            return ;

        context.getBroadcastState(StateDescriptors.CONTROL_MESSAGE_BROADCAST_STATE_DESCRIPTOR)
                .put(controlMessage.getUserId(), controlMessage);
    }
}
