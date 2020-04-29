package messages.schema;

import messages.control.ControlMessage;
import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;

import java.io.IOException;

public class ControlMessageSchema implements DeserializationSchema<ControlMessage> {
    @Override
    public ControlMessage deserialize(byte[] bytes) throws IOException {
        return ControlMessage.of(new String(bytes));
    }

    @Override
    public boolean isEndOfStream(ControlMessage controlMessage) {
        return false;
    }

    @Override
    public TypeInformation<ControlMessage> getProducedType() {
        return TypeInformation.of(ControlMessage.class);
    }
}
