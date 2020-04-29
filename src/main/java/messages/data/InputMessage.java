package messages.data;

import org.json.JSONObject;

public class InputMessage {

    JSONObject jsonObject;
    private Long timestampMs;

    public InputMessage(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public InputMessage(String jsonString) {
        this.jsonObject = new JSONObject(jsonString);
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public boolean hasField(String field) {
        return jsonObject.has(field);
    }

    public String getFieldValue(String field) {
        if (hasField(field)) {
            return jsonObject.get(field).toString();
        } else {
            return null;
        }
    }

    public void setTimestampMs(Long timestampMs) {
        this.timestampMs = timestampMs;
    }

    public Long getTimestampMs() {
        return timestampMs;
    }
}
