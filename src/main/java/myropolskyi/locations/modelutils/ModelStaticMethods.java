package myropolskyi.locations.modelutils;

import com.fasterxml.jackson.databind.ObjectMapper;
import myropolskyi.locations.model.Artefact;
import myropolskyi.locations.model.JsonArtefactsWrapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

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
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * class for static service functions with json
 */
public class ModelStaticMethods {
    private ModelStaticMethods() {
    }

    private static final Logger LOG = LogManager.getLogger(ModelStaticMethods.class);

    /**
     * from json-file with artefacts to list of artefacts from file
     * @param pathToFile
     * @param encoding
     * @return list of artefacts
     * @throws IOException
     */
    public static List<Artefact> getListArtefactForRegion(String pathToFile, Charset encoding) throws IOException {
        String jsonContent = readFile(pathToFile, encoding);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonArtefactsWrapper jsonArtefactsWrapper = objectMapper.readValue(jsonContent, JsonArtefactsWrapper.class);
        LOG.debug("jsonArtefactsWrapper was filled successfully.");
        return jsonArtefactsWrapper.getArtefacts();
    }

    /**
     * from json-file with artefacts to list of artefacts from InputStream
     * @param isr
     * @return list of artefacts
     * @throws IOException
     */
    public static List<Artefact> getListArtefactForRegion(InputStreamReader isr) throws IOException {
        String jsonContent = new BufferedReader(isr)
                .lines().collect(Collectors.joining("\n"));
        ObjectMapper objectMapper = new ObjectMapper();
        JsonArtefactsWrapper jsonArtefactsWrapper = objectMapper.readValue(jsonContent, JsonArtefactsWrapper.class);
        LOG.debug("jsonArtefactsWrapper was filled successfully.");
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
     * @param artefact
     * @return
     * @throws IOException
     */
    public static String getResumeFromWiki(Artefact artefact) throws IOException {
        /*it depends on domain... de, en, so on*/
        String domainName = artefact.getWeb_reference_wiki().replaceAll("http(s)?://|www\\.|/.*", "");
        String strUrl = "http://" + domainName + "/w/api.php?action=query&prop=extracts&format=json&titles="
                + URLEncoder.encode(artefact.getArtefacts_name(), StandardCharsets.UTF_8.toString());
        URL url = new URL(strUrl);

        StringBuilder result = new StringBuilder();
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

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
        return getExtractFromJsonWiki(result.toString(), new StringBuilder());
    }

    /**
     * recursive: if extract is not empty, stop it end return it
     * We don't know, where is our key 'extract' exactly (on what stock)
     * @param jsonString
     * @return
     */
    public static String getExtractFromJsonWiki(String jsonString, StringBuilder extract) {
        JSONObject jsonObject = new JSONObject(jsonString.trim());
        Iterator<String> keys = jsonObject.keys();

        while (keys.hasNext()) {
            if (!extract.toString().trim().isEmpty()) {
                return extract.toString();
            }
            String key = keys.next();
            Object currentValue = jsonObject.get(key);
            if (key.trim().equals("extract")) {
                extract.append(jsonObject.get("extract").toString());
                break;
            }
            if (jsonObject.get(key) instanceof JSONObject) {
                getExtractFromJsonWiki(currentValue.toString(), extract);
            }
        }
        return extract.toString();
    }
}
