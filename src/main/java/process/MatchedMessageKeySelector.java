package process;

import messages.MatchedMessage;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.kafka.common.protocol.types.Field;
import project_settings.ProjectSettings;

import javax.xml.crypto.*;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;

public class MatchedMessageKeySelector implements KeySelector<MatchedMessage, String> {

    @Override
    public String getKey(MatchedMessage matchedMessage) {
        return matchedMessage.getUserId() + ProjectSettings.DELIMITER_FOR_KEY_BY + matchedMessage.getRule().getRuleid();
    }
}
