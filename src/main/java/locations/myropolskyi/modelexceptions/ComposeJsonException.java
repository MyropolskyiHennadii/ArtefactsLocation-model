package locations.myropolskyi.modelexceptions;

import org.json.JSONException;

/**
 * occurs if one of the field/object transforming to json is null
 */
public class ComposeJsonException extends JSONException {
    public ComposeJsonException(String message) {
        super(message);
    }

    public ComposeJsonException(String message, Throwable cause) {
        super(message, cause);
    }
}
