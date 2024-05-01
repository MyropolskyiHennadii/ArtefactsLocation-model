package myropolskyi.locations.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.util.List;

/**
 * class for reading list of artefacts from json-file
 * (defined structure and downloaded from ArtefactsLocation-API)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
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

    public JsonArtefactsWrapper() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSubversion() {
        return subversion;
    }

    public void setSubversion(String subversion) {
        this.subversion = subversion;
    }

    public String getThema() {
        return thema;
    }

    public void setThema(String thema) {
        this.thema = thema;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<Artefact> getArtefacts() {
        return artefacts;
    }

    public void setArtefacts(List<Artefact> artefacts) {
        this.artefacts = artefacts;
    }

    public List<Category> getCategories() {
        return categories;
    }
    @JsonSetter("categories")
    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<WebAuthor> getWebAuthors() {
        return webAuthors;
    }

    public void setWebAuthors(List<WebAuthor> webAuthors) {
        this.webAuthors = webAuthors;
    }
}
