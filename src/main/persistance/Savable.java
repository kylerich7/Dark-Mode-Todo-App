package persistance;

import org.json.JSONObject;

// represents a savable interface
public interface Savable {

    // EFFECTS: returns this as a JSONObject
    JSONObject toJsonObject();
}
