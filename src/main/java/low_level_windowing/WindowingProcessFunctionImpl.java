package low_level_windowing;

import messages.MatchedMessage;
import messages.data.InputMessage;

public class WindowingProcessFunctionImpl extends WindowingProcessFunction<MatchedMessage, InputMessage> {
    public WindowingProcessFunctionImpl(boolean eventTime) {
        super(eventTime);
    }
}
