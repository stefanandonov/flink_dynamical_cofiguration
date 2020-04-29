package states;

import messages.control.ControlMessage;
import messages.control.Rule;
import org.apache.flink.api.common.state.MapStateDescriptor;
import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.common.typeinfo.Types;

import java.util.List;

public class StateDescriptors {

    public static final String BROADCAST_STATE_NAME = "ControlMessageState";

    public static final MapStateDescriptor<String, ControlMessage> CONTROL_MESSAGE_BROADCAST_STATE_DESCRIPTOR =
            new MapStateDescriptor<>(
                    BROADCAST_STATE_NAME, TypeInformation.of(String.class), TypeInformation.of(new TypeHint<ControlMessage>() {
            })
            );
}
