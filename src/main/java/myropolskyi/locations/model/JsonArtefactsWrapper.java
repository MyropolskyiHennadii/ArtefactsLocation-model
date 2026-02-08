package myropolskyi.locations.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

/**
 * class for reading list of artefacts from json-file
 * (defined structure and downloaded from ArtefactsLocation-API)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
public class JsonArtefactsWrapper {

    private String thema;
    private String region;
    private String error;
    private Announcement announcement;
    private Set<Category> categories;
    private List<Artefact> artefacts;
    private Set<WebAuthor> webAuthors;
    private String version;
    private String stringLocalDate;

    public JsonArtefactsWrapper(List<Artefact> artefacts, Set<Category> categories, Set<WebAuthor> webAuthors) {
        this.artefacts = artefacts;
        this.categories = categories;
        this.webAuthors = webAuthors;
    }

    public JsonArtefactsWrapper(String date,
                                String version,
                                String thema,
                                String region,
                                String error,
                                Set<Category> categories,
                                List<Artefact> artefacts,
                                Set<WebAuthor> webAuthors) {
        this.stringLocalDate = date;
        this.version = version;
        this.thema = thema;
        this.region = region;
        this.error = error;
        this.categories = categories;
        this.artefacts = artefacts;
        this.webAuthors = webAuthors;
    }

}
