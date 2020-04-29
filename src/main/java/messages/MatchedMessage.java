package messages;

import messages.control.Rule;
import messages.data.InputMessage;
import high_level_windowing.IConfigurableWindow;

public class MatchedMessage implements IConfigurableWindow<InputMessage> {
    private InputMessage inputMessage;
    private String userId;
    private Rule rule;

    public MatchedMessage(InputMessage inputMessage, String userId, Rule rule) {
        this.inputMessage = inputMessage;
        this.userId = userId;
        this.rule = rule;
    }

    public InputMessage getInputMessage() {
        return inputMessage;
    }

    public void setInputMessage(InputMessage inputMessage) {
        this.inputMessage = inputMessage;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Rule getRule() {
        return rule;
    }

    public void setRule(Rule rule) {
        this.rule = rule;
    }

    @Override
    public InputMessage getElement() {
        return inputMessage;
    }

    @Override
    public Long getWindowSize() {
        return rule.getWindowConfig().getWindowSize();
    }

    @Override
    public String getWindowType() {
        return rule.getWindowConfig().getWindowType();
    }

    @Override
    public Long getWindowSlide() {
        return rule.getWindowConfig().getWindowSlide();
    }

    @Override
    public long getTimestamp() {
        return inputMessage.getTimestampMs();
    }

    @Override
    public void setTimestamp(long timestamp) {
        inputMessage.setTimestampMs(timestamp);
    }
}
