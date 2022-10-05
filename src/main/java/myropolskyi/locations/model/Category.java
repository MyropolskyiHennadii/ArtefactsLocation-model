package myropolskyi.locations.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

//Category of thema
@Entity
@Table(name = "categories")
public class Category implements LocationsJsonRepresentable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_category;
    @Column
    private String category_name;
    @Column
    private String web_reference_wiki;
    @Column
    private String page_language;//language of web-page
    @Column
    private String type_category;// style, temporal or other type
    @Column
    private int updated;//1 = was updated, 0 = wasn't
    @Column
    private int deleted;//1 = was marked as deleted, 0 = wasn't

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "thema")
    @JsonBackReference(value = "thema_categories")//important to prevent infinite loop of references
    private Thema thema;//foreign key in database

    //orphanRemoval = true to refresh all synonyms
    @OneToMany(targetEntity = CategoriesSynonym.class, mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    //@OneToMany(targetEntity= CategoriesSynonym.class, mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference(value = "categories_synonyms")//!!! important to prevent infinite loop with json references
    private Set<CategoriesSynonym> synonyms = new HashSet<>();// foreign key in database. One Artefact = many Authors

    //orphanRemoval = true to refresh all synonyms
    @OneToMany(targetEntity = ArtefactsCategory.class, mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference(value = "id_categories")//!!! important to prevent infinite loop with json references
    private Set<ArtefactsCategory> artefactsCategories = new HashSet<>();// foreign key in database. One Artefact = many Authors

    public Category() {
    }

    public Category(String category_name, String web_reference_wiki, String lang, String typeCategory, Thema thema) {
        this.category_name = category_name;
        this.web_reference_wiki = web_reference_wiki;
        this.page_language = lang;
        this.updated = 1;//always for new exemplar (for database exchange)
        this.type_category = typeCategory;
        this.thema = thema;
    }

    public int getId_category() {
        return id_category;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
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

    public Thema getThema() {
        return thema;
    }

    public void setThema(Thema thema) {
        this.thema = thema;
    }

    public String getPage_language() {
        return page_language;
    }

    public void setPage_language(String page_language) {
        this.page_language = page_language;
    }

    public Set<CategoriesSynonym> getSynonyms() {
        return synonyms;
    }

    public void setSynonyms(Set<CategoriesSynonym> synonyms) {
        this.synonyms = synonyms;
    }

    public String getType_category() {
        return type_category;
    }


    /**
     * composes json-representation for Category-exemplar
     */
    @Override
    public JSONObject composeJsonObject() {
        JSONObject jsonCategory = new JSONObject();
        jsonCategory.put("id_category", id_category);
        jsonCategory.put("category_name", category_name);
        jsonCategory.put("web_reference_wiki", web_reference_wiki);
        jsonCategory.put("page_language", page_language);
        jsonCategory.put("type_category", type_category);
        jsonCategory.put("thema", thema.getThema_name());
        //synonyms
        JSONArray synonymsJson = new JSONArray();
        for (CategoriesSynonym synonym : getSynonyms()) {
            synonymsJson.put(synonym.composeJsonObject());
        }
        jsonCategory.put("synonyms", synonymsJson);
        return jsonCategory;
    }

    /**
     * decomposes json-representation TO Category-exemplar
     */
    @Override
    public Category decomposeJsonObject(JSONObject json) throws NumberFormatException, JSONException {
        this.id_category = json.getInt("id_category");
        this.category_name = json.getString("category_name");
        this.web_reference_wiki = json.getString("web_reference_wiki");
        this.page_language = json.getString("page_language");
        this.type_category = json.getString("type_category");
        this.thema = new Thema(json.getString("thema"));
        //synonyms
        JSONArray synonymsJson = json.getJSONArray("synonyms");
        synonyms.clear();
        for (int i = 0; i < synonymsJson.length(); i++) {
            synonyms.add(new CategoriesSynonym().decomposeJsonObject(synonymsJson.getJSONObject(i)));
        }
        return this;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id_category=" + id_category +
                ", category_name='" + category_name + '\'' +
                ", page_language='" + page_language + '\'' +
                ", web_reference_wiki='" + web_reference_wiki + '\'' +
                ", thema=" + thema.getThema_name() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return getWeb_reference_wiki().equals(category.getWeb_reference_wiki());
    }

    @Override
    public int hashCode() {
        return getId_category();
    }

}
