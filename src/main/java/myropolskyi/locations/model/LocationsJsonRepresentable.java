package myropolskyi.locations.model;

import org.json.JSONObject;

public interface LocationsJsonRepresentable {

    /**
     * composes json from class exemplar
     *
     * @return
     */
    JSONObject composeJsonObject();

    /**
     * decomposes json to class exemplar
     *
     * @param json
     * @return
     */
    LocationsJsonRepresentable decomposeJsonObject(JSONObject json);
}
