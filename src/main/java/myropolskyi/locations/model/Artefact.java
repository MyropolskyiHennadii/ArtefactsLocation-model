package myropolskyi.locations.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import myropolskyi.locations.modelexceptions.ComposeJsonException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

//main class of artefacts
@Entity
@Table(name = "artefacts")
public class Artefact implements LocationsJsonRepresentable {

    private static final Logger LOG = LogManager.getLogger(Artefact.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_artefacts;
    @Column
    private String artefacts_name;
    @Column
    private String web_reference_wiki;//wiki-page, unique
    @Column
    private String page_language;//language of wiki-page
    @Column
    private int updated;//1 = was updated, 0 = wasn't
    @Column
    private int deleted;//1 = was marked as deleted, 0 = wasn't

    //orphanRemoval = true to refresh all synonyms
    @OneToMany(targetEntity = ArtefactsAuthor.class, mappedBy = "artefact", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference//!!! important to prevent infinite loop with json references
    private Set<ArtefactsAuthor> authors = new HashSet<>();// foreign key in database. One Artefact = many Authors

    //orphanRemoval = true to refresh all synonyms
    @OneToMany(targetEntity = ArtefactsEvent.class, mappedBy = "artefact", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference//!!! important to prevent infinite loop with json references
    private Set<ArtefactsEvent> events = new HashSet<>();// foreign key in database. One Artefact = many events

    //orphanRemoval = true to refresh all synonyms
    @OneToMany(targetEntity = ArtefactsSynonym.class, mappedBy = "artefact", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference//!!! important to prevent infinite loop with json references
    private Set<ArtefactsSynonym> synonyms = new HashSet<>();// foreign key in database. One Artefact = many synonyms

    //orphanRemoval = true to refresh all synonyms
    @OneToMany(targetEntity = ArtefactsCategory.class, mappedBy = "artefact", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference//!!! important to prevent infinite loop with json references
    private Set<ArtefactsCategory> categories = new HashSet<>();// foreign key in database. One Artefact = many categories

    //orphanRemoval = true to refresh all synonyms
    @OneToOne(targetEntity = ArtefactsLocation.class, mappedBy = "artefact", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference//!!! important to prevent infinite loop with json references
    private ArtefactsLocation artefactsLocation;//foreign key in database

    //orphanRemoval = true to refresh all synonyms
    @OneToOne(targetEntity = ArtefactsImage.class, mappedBy = "artefact", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference//!!! important to prevent infinite loop with json references
    private ArtefactsImage artefactsImage;//foreign key in database

    public Artefact() {
    }

    public Artefact(String artefacts_name, String web_reference_wiki, String page_language) {
        this.artefacts_name = artefacts_name;
        this.web_reference_wiki = web_reference_wiki;
        this.page_language = page_language;
        this.updated = 1;//always for new exemplar (for database exchange)
    }

    public String getArtefacts_name() {
        return artefacts_name;
    }

    public void setArtefacts_name(String artefacts_name) {
        this.artefacts_name = artefacts_name;
    }

    public String getWeb_reference_wiki() {
        return web_reference_wiki;
    }

    public void setWeb_reference_wiki(String web_reference_wiki) {
        this.web_reference_wiki = web_reference_wiki;
    }

    public int getUpdated() {
        return updated;
    }

    public void setUpdated(int updated) {
        this.updated = updated;
    }

    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }

    public Set<ArtefactsAuthor> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<ArtefactsAuthor> authors) {
        this.authors = authors;
    }

    public Set<ArtefactsEvent> getEvents() {
        return events;
    }

    public void setEvents(Set<ArtefactsEvent> events) {
        this.events = events;
    }

    public Set<ArtefactsSynonym> getSynonyms() {
        return synonyms;
    }

    public void setSynonyms(Set<ArtefactsSynonym> synonyms) {
        this.synonyms = synonyms;
    }

    public Set<ArtefactsCategory> getCategories() {
        return categories;
    }

    public void setCategories(Set<ArtefactsCategory> categories) {
        this.categories = categories;
    }

    public int getId_artefacts() {
        return id_artefacts;
    }

    public String getPage_language() {
        return page_language;
    }

    public void setPage_language(String page_language) {
        this.page_language = page_language;
    }

    public ArtefactsLocation getArtefactsLocation() {
        return artefactsLocation;
    }

    public void setArtefactsLocation(ArtefactsLocation artefactsLocation) {
        this.artefactsLocation = artefactsLocation;
    }

    public ArtefactsImage getArtefactsImage() {
        return artefactsImage;
    }

    public void setArtefactsImage(ArtefactsImage artefactsImage) {
        this.artefactsImage = artefactsImage;
    }

    /**
     * composes json-representation for Artefact-exemplar
     */
    public JSONObject composeJsonObject(){
        JSONObject jsonArtefact = new JSONObject();
        LOG.trace("Compose Artefact with id {}", id_artefacts);
        jsonArtefact.put("id_artefacts", id_artefacts);
        jsonArtefact.put("artefacts_name", artefacts_name);
        jsonArtefact.put("web_reference_wiki", web_reference_wiki);
        jsonArtefact.put("page_language", page_language);
        //impossible, but:
        if(artefactsLocation == null){
            LOG.warn("Empty location. Artefact's id={}", id_artefacts);
            throw new ComposeJsonException("Empty location. Artefact's id=" + id_artefacts);
        }
        jsonArtefact.put("artefactsLocation", artefactsLocation.composeJsonObject());
        if(artefactsImage != null){
            jsonArtefact.put("artefactsImage", artefactsImage.composeJsonObject());
        } else {
            jsonArtefact.put("artefactsImage", "");
        }
        //authors
        JSONArray authorsJson = new JSONArray();
        for (ArtefactsAuthor author: getAuthors()) {
            authorsJson.put(author.composeJsonObject());
        }
        jsonArtefact.put("authors", authorsJson);
        //events
        JSONArray eventsJson = new JSONArray();
        for (ArtefactsEvent event: getEvents()) {
            eventsJson.put(event.composeJsonObject());
        }
        jsonArtefact.put("events", eventsJson);
        //synonyms
        JSONArray synonymsJson = new JSONArray();
        for (ArtefactsSynonym synonym: getSynonyms()) {
            synonymsJson.put(synonym.composeJsonObject());
        }
        jsonArtefact.put("synonyms", synonymsJson);
        //categories
        JSONArray categoriesJson = new JSONArray();
        for (ArtefactsCategory artefactsCategory: getCategories()) {
            categoriesJson.put(artefactsCategory.composeJsonObject());
        }
        jsonArtefact.put("categories", categoriesJson);
        return jsonArtefact;
    }

    /**
     * in wiki we have {lat.:...,long.:...} and here we receive ArtefactsLocation
     *
     * @param jsonString
     * @return
     */
    public ArtefactsLocation getArtefactsLocationFromJson(String jsonString) {
        try {
            JSONObject obj = new JSONObject(jsonString.replace("\n", "").replace("\r", ""));
            Double longitude = obj.getDouble("lon");
            Double latitude = obj.getDouble("lat");
            if (longitude != null && latitude != null) {
                return new ArtefactsLocation(longitude, latitude, this);
            } else {
                return null;
            }
        } catch (JSONException e) {
            LOG.error("Impossible to parse json-coordinates {} for artefact {}", jsonString, this);
            return null;
        }
    }

    /**
     * check and correct duplicated records in events
     * @param events
     * @return
     */
    public Set<ArtefactsEvent> checkAndCorrectDuplicatedEvent(Set<ArtefactsEvent> events){
        Set<ArtefactsEvent> checkingEvents = new HashSet<>();
        for (ArtefactsEvent event: events) {
            ArtefactsEvent newEvent = new ArtefactsEvent(event.getEvent(), event.getEvent_begin(), event.getEvent_end(), event.getArtefact());
            newEvent.setUpdated(0);
            checkingEvents.add(newEvent);
        }
        return checkingEvents;
    }

    /**
     * check and correct duplicated authors
     * @param authors
     * @return
     */
    public Set<ArtefactsAuthor> checkAndCorrectDuplicatedAuthors(Set<ArtefactsAuthor> authors) {
        Set<ArtefactsAuthor> checkingAuthors = new HashSet<>();
        for (ArtefactsAuthor author: authors) {
            ArtefactsAuthor newAuthor = new ArtefactsAuthor(author.getAuthor_name(), author.getArtefact());
            newAuthor.setUpdated(0);
            checkingAuthors.add(newAuthor);
        }
        return checkingAuthors;
    }

    @Override
    public String toString() {
        return "Artefact{" +
                "id_artefacts=" + id_artefacts +
                ", artefacts_name='" + artefacts_name + '\'' +
                ", web_reference_wiki='" + web_reference_wiki + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Artefact artefact = (Artefact) o;

        return getWeb_reference_wiki().trim().equals(artefact.getWeb_reference_wiki().trim());
    }

    @Override
    public int hashCode() {
        return getWeb_reference_wiki().length();
    }

}
