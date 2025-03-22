package myropolskyi.locations.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * class for reading list of artefacts from json-file
 * (defined structure and downloaded from ArtefactsLocation-API)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
public class JsonArtefactsWrapper {

    private String date;
    private String subversion;
    private String thema;
    private String region;
    private String error;
    private List<Category> categories;
    private List<Artefact> artefacts;
    private List<WebAuthor> webAuthors;

    public JsonArtefactsWrapper(String date, String subversion, String thema, String region, String error, List<Category> categories, List<Artefact> artefacts) {
        this.date = date;
        this.subversion = subversion;
        this.thema = thema;
        this.region = region;
        this.error = error;
        this.categories = categories;
        this.artefacts = artefacts;
    }

    @JsonSetter("categories")
    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

}
