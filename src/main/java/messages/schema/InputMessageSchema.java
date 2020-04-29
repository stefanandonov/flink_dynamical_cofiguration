package messages.schema;

import messages.data.InputMessage;
import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;

import java.io.IOException;

public class InputMessageSchema implements DeserializationSchema<InputMessage> {
    @Override
    public InputMessage deserialize(byte[] bytes) throws IOException {
        return new InputMessage(new String(bytes));
    }

    @Override
    public boolean isEndOfStream(InputMessage inputMessage) {
        return false;
    }

    @Override
    public TypeInformation<InputMessage> getProducedType() {
        return TypeInformation.of(InputMessage.class);
    }
}
