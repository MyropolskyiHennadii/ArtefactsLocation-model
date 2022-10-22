package myropolskyi.locations.modelutils;

import com.fasterxml.jackson.databind.ObjectMapper;
import myropolskyi.locations.model.Artefact;
import myropolskyi.locations.model.JsonArtefactsWrapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
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
    static String readFile(String path, Charset encoding)
            throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }
}
