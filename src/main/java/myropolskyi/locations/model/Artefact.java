package myropolskyi.locations.model;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import myropolskyi.location.exceptions.JsonReadingException;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

//main class of artefacts
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
    private int updated;//1 = was updated, 0 = wasn't
    @Column
    @JsonIgnore
    private int deleted;//1 = was marked as deleted, 0 = wasn't
    @Column
    @JsonIgnore
    private String last_modified;//date-time of last modification

    /*field to get integer array with Category's codes fron Json*/
    @Transient
    private Set<Integer> mainCategoriesId = new HashSet<>();

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
    /*TODO: this Transient stays here in order to make quick query. Because, at first, we don't need to get all categories of artefact, just id of main category.
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

    public Artefact() {
    }

    public Artefact(String artefacts_name, String web_reference_wiki, String page_language) {
        this.artefacts_name = artefacts_name;
        this.web_reference_wiki = web_reference_wiki;
        this.page_language = page_language;
        this.updated = 1;//always for new exemplar (for database exchange)
    }

    /*this constructor we need to create artefact from native (not hibernate) query*/
    public Artefact(int id, String artefacts_name, String web_reference_wiki, String page_language) {
        this.id_artefacts = id;
        this.artefacts_name = artefacts_name;
        this.web_reference_wiki = web_reference_wiki;
        this.page_language = page_language;
        this.updated = 1;//always for new exemplar (for database exchange)
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

    @JsonGetter("id_artefacts")
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

    public String getLast_modified() {
        return last_modified;
    }

    public void setLast_modified(String last_modified) {
        this.last_modified = last_modified;
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
            if (longitude != null && latitude != null) {
                return new ArtefactsLocation(longitude, latitude, this);
            } else {
                throw new JsonReadingException("Impossible to read lon and lat by getting long and lat of artefact, one of them or both are null.");
            }
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
            newEvent.setUpdated(0);
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
