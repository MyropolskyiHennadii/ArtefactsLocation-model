package myropolskyi.locations.modelutils;

import myropolskyi.locations.model.Artefact;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * class for static service functions
 */
public class ModelToJsonTransformations {
    private ModelToJsonTransformations() {
    }

    private static final Logger LOG = LogManager.getLogger(ModelToJsonTransformations.class);

    /**
     * forms JSONArray from Artefacts collection
     *
     * @param artefacts
     * @param thema
     * @return
     */
    public static JSONArray getJsonFromArtefactsCollection(Collection<Artefact> artefacts, String thema) {
//TODO how to do the first query with сategories filter?
        final JSONArray artefactsJson = new JSONArray();
        long count = artefacts.stream()
                //remain categories only for defined thema
                .map(a -> {
                    a.setCategories(
                            a.getCategories().stream()
                                    .filter(b -> b.getCategory().getThema().getThema_name().equals(thema))
                                    .collect(Collectors.toSet())); return a;
                })
                .filter(a -> !a.getCategories().isEmpty())//not empty set of categories
                .map(a -> artefactsJson.put(a.composeJsonObject()))//add json-Artefact to JsonArray
                .count();//just to terminate stream
        LOG.debug("Length of artefacts: {}, artefacts-json {}", count, artefactsJson.length());
        return artefactsJson;
    }

    /**
     * decomposes JSONarray to List<Artefact>
     * @param jsonArtefacts
     * @return
     */
    public static List<Artefact> getListArtefactFromJSONArtefactsArray(JSONArray jsonArtefacts) throws NumberFormatException, JSONException {
        List<Artefact> listArtefact = new ArrayList<>();
        for (int i = 0; i < jsonArtefacts.length(); i++) {
            listArtefact.add(new Artefact().decomposeJsonObject((JSONObject) jsonArtefacts.get(i)));
        }
        LOG.debug("After decomposing size of List<Artefacts>: {}", listArtefact.size());
        return listArtefact;
    }

    /**
     * forms JSON array with used in Artefacts collection categories (only categories ID)
     *
     * @param artefacts
     * @return
     */
    public static JSONArray getJsonIdUsedCategories(Collection<Artefact> artefacts) {
        final JSONArray artefactsCategoryIdSet = new JSONArray();
        artefacts.stream()
                .flatMap(a -> a.getCategories().stream())
                .map(a -> a.getCategory().getId_category())
                .collect(Collectors.toSet())
                .forEach(artefactsCategoryIdSet::put);
        LOG.debug("Length of artefacts ID categories-json {}", artefactsCategoryIdSet.length());
        return artefactsCategoryIdSet;
    }
}
