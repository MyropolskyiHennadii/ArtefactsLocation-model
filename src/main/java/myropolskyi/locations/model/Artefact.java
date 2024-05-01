package myropolskyi.locations.model;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import myropolskyi.location.exceptions.JsonReadingException;

import java.util.*;

/**
 * Artefact itself as an geographical and artificial object
 */
@Entity
@Table(name = "artefacts")
public class Artefact implements AsModelRepresentable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private int id_artefacts;
    @Column
    private String artefacts_name;
    @Column
    private String web_reference_wiki;//wiki-page, unique
    @Column
    private String page_language;//language of wiki-page
    @Column
    @JsonIgnore
    private String modified;//date-time of last modification
    @Column
    @JsonIgnore
    private String created;//date-time of creation
    @Column
    @JsonIgnore
    private String reviewed;//date-time of last review


    /*field to get integer array with Category's codes fron Json*/
    @Transient
    private Set<Integer> mainCategoriesId = new HashSet<>();
    /*map for simplifying use artefacts fields in android-app:*/
    @Transient
    @JsonIgnore
    Map<String, String> artefactsInfo = new HashMap<>();

    //orphanRemoval = true to refresh all synonyms
    @OneToMany(targetEntity = ArtefactsAuthor.class, mappedBy = "artefact", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference//!!! important to prevent infinite loop with json references
    private Set<ArtefactsAuthor> authors = new HashSet<>();// foreign key in database. One Artefact = many Authors
    //orphanRemoval = true to refresh all synonyms
    @OneToMany(targetEntity = ArtefactsEvent.class, mappedBy = "artefact", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference//!!! important to prevent infinite loop with json references
    private Set<ArtefactsEvent> events = new HashSet<>();// foreign key in database. One Artefact = many events

    //orphanRemoval = true to refresh all synonyms
    @OneToMany(targetEntity = ArtefactsSynonym.class, mappedBy = "artefact", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference(value = "artefacts_synonyms")//!!! important to prevent infinite loop with json references
    private Set<ArtefactsSynonym> synonyms = new HashSet<>();// foreign key in database. One Artefact = many synonyms

    //orphanRemoval = true to refresh all synonyms
    /*this Transient stays here in order to make quick query. Because, at first, we don't need to get all categories of artefact, just id of main category.
    And second:  just simple hibernate query is VERY slow in this case. May be we need to optimize database*/
    /*TODO: need to change writing to database because of this transient*/
/*    @OneToMany(targetEntity = ArtefactsCategory.class, mappedBy = "artefact", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonIgnore*/
    @Transient
    private Set<ArtefactsCategory> categories = new HashSet<>();// foreign key in database. One Artefact = many categories

    //orphanRemoval = true to refresh all synonyms
    @OneToOne(targetEntity = ArtefactsLocation.class, mappedBy = "artefact", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference(value = "artefacts_location")//!!! important to prevent infinite loop with json references
    private ArtefactsLocation artefactsLocation;//foreign key in database

    //orphanRemoval = true to refresh all synonyms
    @OneToOne(targetEntity = ArtefactsImage.class, mappedBy = "artefact", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference(value = "artefacts_image")//!!! important to prevent infinite loop with json references
    private ArtefactsImage artefactsImage;//foreign key in database

    @Transient
    @JsonIgnore
    /*new field in DB for strictly defined authors:*/
    private Set<Integer> webAuthorsIDs = new HashSet<>();

    public Artefact() {
    }

    public Artefact(String artefacts_name, String web_reference_wiki, String page_language) {
        this.artefacts_name = artefacts_name;
        this.web_reference_wiki = web_reference_wiki;
        this.page_language = page_language;
    }

    /*this constructor we need to create artefact from native (not hibernate) query*/
    public Artefact(int id, String artefacts_name, String web_reference_wiki, String page_language) {
        this.id_artefacts = id;
        this.artefacts_name = artefacts_name;
        this.web_reference_wiki = web_reference_wiki;
        this.page_language = page_language;
    }

    public Set<Integer> getMainCategoriesId() {
        return mainCategoriesId;
    }

    @JsonSetter("categories")
    public void setMainCategoriesId(Integer[] inputArray) {
        Arrays.asList(inputArray).stream().forEach(a -> mainCategoriesId.add(a));
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

    public int getId() {
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

    public String getModified() {
        return modified;
    }

    public void setModified(String last_modified) {
        this.modified = last_modified;
    }

    public Map<String, String> getArtefactsInfo() {
        return artefactsInfo;
    }

    public void setArtefactsInfo(Map<String, String> artefactsInfo) {
        this.artefactsInfo = artefactsInfo;
    }

    /**
     * in wiki we have {lat.:...,long.:...} and here we receive ArtefactsLocation
     *
     * @param jsonString
     * @return
     */
    public ArtefactsLocation getArtefactsLocationFromJson(String jsonString) throws JsonReadingException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode obj = mapper.readTree(jsonString);
            Double longitude = obj.get("lon").asDouble();
            Double latitude = obj.get("lat").asDouble();
            return new ArtefactsLocation(longitude, latitude, this);
        } catch (JsonProcessingException e) {
            throw new JsonReadingException("JsonProcessingException by getting long and lat of artefact: " + e.getMessage());
        }
    }

    /**
     * check and correct duplicated records in events
     *
     * @param events
     * @return
     */
    public Set<ArtefactsEvent> checkAndCorrectDuplicatedEvent(Set<ArtefactsEvent> events) {
        Set<ArtefactsEvent> checkingEvents = new HashSet<>();
        for (ArtefactsEvent event : events) {
            ArtefactsEvent newEvent = new ArtefactsEvent(event.getEvent(), event.getEvent_begin(), event.getEvent_end(), event.getArtefact());
            checkingEvents.add(newEvent);
        }
        return checkingEvents;
    }

    //make json compact; without references to object. just integers = Category.id
    @JsonGetter(value = "categories")
    private Set<Integer> getIdArtefactsCategory() {
        Set<Integer> idArtefactsCategory = new HashSet<>();
        for (ArtefactsCategory a : getCategories()) {
            idArtefactsCategory.add(a.getCategory().getId());
        }
        return idArtefactsCategory;
    }

    /**
     * check and correct duplicated authors
     *
     * @param authors
     * @return
     */
    public Set<ArtefactsAuthor> checkAndCorrectDuplicatedAuthors(Set<ArtefactsAuthor> authors) {
        Set<ArtefactsAuthor> checkingAuthors = new HashSet<>();
        for (ArtefactsAuthor author : authors) {
            ArtefactsAuthor newAuthor = new ArtefactsAuthor(author.getAuthor_name(), author.getArtefact());
            checkingAuthors.add(newAuthor);
        }
        return checkingAuthors;
    }

    @Override
    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    @Override
    public String getReviewed() {
        return reviewed;
    }

    public void setReviewed(String reviewed) {
        this.reviewed = reviewed;
    }

    public void setMainCategoriesId(Set<Integer> mainCategoriesId) {
        this.mainCategoriesId = mainCategoriesId;
    }

    public Set<Integer> getWebAuthorsIDs() {
        return webAuthorsIDs;
    }

    public void setWebAuthorsIDs(Set<Integer> webAuthorsIDs) {
        this.webAuthorsIDs = webAuthorsIDs;
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

        /*one of them is already written (id!=0), another one can be not*/
        if (getId() * artefact.getId() == 0 && (getId() + artefact.getId()) > 0) {
            return false;
        }
        /*both are not written in database*/
        if (getId() + artefact.getId() == 0) {
            return getWeb_reference_wiki().trim().equals(artefact.getWeb_reference_wiki().trim());
        }

        /*both are written in database*/
        return getId() == artefact.getId();
    }

    @Override
    public int hashCode() {
        return getWeb_reference_wiki().length();
    }

}
