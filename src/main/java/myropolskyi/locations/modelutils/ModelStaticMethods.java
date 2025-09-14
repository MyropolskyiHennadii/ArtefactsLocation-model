package myropolskyi.locations.modelutils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import myropolskyi.locations.exceptions.JsonReadingException;
import myropolskyi.locations.model.Artefact;
import myropolskyi.locations.model.JsonArtefactsWrapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

/**
 * class for static service functions with json
 */
public class ModelStaticMethods {
    private ModelStaticMethods() {
    }

    private static final Logger log = LogManager.getLogger(ModelStaticMethods.class);

    /**
     * from json-file with artefacts to list of artefacts from file
     *
     * @param pathToFile
     * @param encoding
     * @return list of artefacts
     * @throws IOException
     */
    public static List<Artefact> getListArtefactForRegion(String pathToFile, Charset encoding) throws IOException {
        String jsonContent = readFile(pathToFile, encoding);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonArtefactsWrapper jsonArtefactsWrapper = objectMapper.readValue(jsonContent, JsonArtefactsWrapper.class);
        log.debug("jsonArtefactsWrapper was filled successfully.");
        return jsonArtefactsWrapper.getArtefacts();
    }

    /**
     * from json-file with artefacts to list of artefacts from InputStream
     *
     * @param isr
     * @return list of artefacts
     * @throws IOException
     */
    public static List<Artefact> getListArtefactForRegion(InputStreamReader isr) throws IOException {
        String jsonContent = new BufferedReader(isr)
                .lines().collect(Collectors.joining("\n"));
        ObjectMapper objectMapper = new ObjectMapper();
        JsonArtefactsWrapper jsonArtefactsWrapper = objectMapper.readValue(jsonContent, JsonArtefactsWrapper.class);
        log.debug("jsonArtefactsWrapper was filled successfully.");
        return jsonArtefactsWrapper.getArtefacts();
    }

    /**
     * returns content of file as string
     *
     * @param path
     * @param encoding
     * @return
     * @throws IOException
     */
    public static String readFile(String path, Charset encoding)
            throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

    /**
     * gets from wiki-api extract for the artefact's page
     *
     * @param artefact
     * @return wiki-resume
     * @throws IOException
     */
    public static String getResumeFromWiki(Artefact artefact) throws IOException {
        /*it depends on domain... de, en, so on*/
        String domainName = artefact.getWeb_reference_wiki().replaceAll("http(s)?://|www\\.|/.*", "");
        String strUrl = "http://" + domainName + "/w/api.php?action=query&prop=extracts&format=json&titles="
                + URLEncoder.encode(artefact.getArtefacts_name(), StandardCharsets.UTF_8.toString());
        return getResultFromWikiAPI(strUrl);
    }

    /**
     * gets from wiki-api extract for the artefact's page
     *
     * @param wikiPage     url wiki
     * @param artefactName artefacts name in language url wiki
     * @return wiki-resume
     * @throws IOException
     */
    public static String getResumeFromWiki(String wikiPage, String artefactName) throws IOException {
        /*it depends on domain... de, en, so on*/
        String domainName = wikiPage.replaceAll("http(s)?://|www\\.|/.*", "");
        String strUrl = "http://" + domainName + "/w/api.php?action=query&prop=extracts&format=json&titles="
                + URLEncoder.encode(artefactName, StandardCharsets.UTF_8.toString());
        return getResultFromWikiAPI(strUrl);
    }

    /**
     * gets result from wiki-API for resume
     *
     * @param strUrl
     * @return
     */
    public static String getResultFromWikiAPI(String strUrl) throws IOException {
        URL url = new URL(strUrl);

        StringBuilder result = new StringBuilder();
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36");

        int status = conn.getResponseCode();
        if (status == HttpURLConnection.HTTP_MOVED_TEMP
                || status == HttpURLConnection.HTTP_MOVED_PERM) {
            String location = conn.getHeaderField("Location");
            URL newUrl = new URL(location);
            conn = (HttpURLConnection) newUrl.openConnection();
        }

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(conn.getInputStream()))) {
            for (String line; (line = reader.readLine()) != null; ) {
                result.append(line);
            }
        }
        //we get json-string. Now we have to get exactly extract from this json
        try {
            return getExtractFromJsonWiki(result.toString());
        } catch (JsonReadingException e) {
            return "Error by reading json from wiki: " + e.getMessage();
        }

    }

    /**
     * gets extract from wiki-page-json
     * @param jsonString input json
     * @return string with extract
     */
    public static String getExtractFromJsonWiki(String jsonString) throws JsonReadingException {
        Map<String, String> keyValuePaar = new HashMap<>();
        try {
            getAllKeysInJsonUsingJsonNodeFieldNames(jsonString, new ObjectMapper(), keyValuePaar);
            return(keyValuePaar.get("extract"));
        } catch (JsonProcessingException e) {
            throw new JsonReadingException("Impossible to read  extract from wiki: " + e.getMessage());
        }
    }

    /**
     * gets all keys from json and fills map with key-value
     * @param json
     * @param mapper
     * @param keyValuePaar
     * @return
     * @throws JsonProcessingException
     */
    public static List<String> getAllKeysInJsonUsingJsonNodeFieldNames(String json, ObjectMapper mapper, Map<String, String> keyValuePaar) throws JsonProcessingException {
        List<String> keys = new ArrayList<>();
        JsonNode jsonNode = mapper.readTree(json);
        getAllKeysUsingJsonNodeFields(jsonNode, keys, keyValuePaar);
        return keys;
    }

    public static void getAllKeysUsingJsonNodeFields(JsonNode jsonNode, List<String> keys, Map<String, String> keyValuePaar) {
        if (jsonNode.isObject()) {
            Iterator<Map.Entry<String, JsonNode>> fields = jsonNode.fields();
            fields.forEachRemaining(field -> {
                keys.add(field.getKey());
                keyValuePaar.put(field.getKey(), field.getValue().asText());
                getAllKeysUsingJsonNodeFields(field.getValue(), keys, keyValuePaar);
            });
        } else if (jsonNode.isArray()) {
            ArrayNode arrayField = (ArrayNode) jsonNode;
            arrayField.forEach(node -> {
                getAllKeysUsingJsonNodeFields(node, keys, keyValuePaar);
            });
        }
    }
}
