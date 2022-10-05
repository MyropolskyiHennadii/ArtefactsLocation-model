package myropolskyi.locations.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.json.JSONException;
import org.json.JSONObject;

import javax.persistence.*;

//Category's synonym in different languages
@Entity
@Table(name = "categories_synonyms")
public class CategoriesSynonym implements LocationsJsonRepresentable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_category_synonym;
    @Column
    private String lang;//code of language
    @Column
    private String lang_name;//name in language lang
    @Column
    private String web_reference_wiki;
    @Column
    private int updated;//1 = was updated, 0 = wasn't
    @Column
    private int deleted;//1 = was marked as deleted, 0 = wasn't

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_category")
    @JsonBackReference(value = "categories_synonyms")//important to prevent infinite loop of references
    private Category category;//foreign key in database

    public CategoriesSynonym() {
    }

    public CategoriesSynonym(String lang, String lang_name, String web_reference_wiki, Category category) {
        this.lang = lang;
        this.lang_name = lang_name;
        this.web_reference_wiki = web_reference_wiki;
        this.updated = 1;//always for new exemplar (for database exchange)
        this.category = category;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getLang_name() {
        return lang_name;
    }

    public void setLang_name(String lang_name) {
        this.lang_name = lang_name;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getId_category_synonym() {
        return id_category_synonym;
    }


    /**
     * composes json-representation for CategoriesSynonym-exemplar
     */
    @Override
    public JSONObject composeJsonObject() {
        JSONObject jsonSynonym = new JSONObject();
        jsonSynonym.put("id_category_synonym", id_category_synonym);
        jsonSynonym.put("lang", lang);
        jsonSynonym.put("lang_name", lang_name);
        jsonSynonym.put("web_reference_wiki", web_reference_wiki);
        return jsonSynonym;
    }

    /**
     * decomposes json-representation TO CategoriesSynonym-exemplar
     */
    @Override
    public CategoriesSynonym decomposeJsonObject(JSONObject json) throws NumberFormatException, JSONException {
        this.id_category_synonym = json.getInt("id_category_synonym");
        this.lang = json.getString("lang");
        this.lang_name = json.getString("lang_name");
        this.web_reference_wiki = json.getString("web_reference_wiki");
        return this;
    }

    @Override
    public String toString() {
        return "CategoriesSynonym{" +
                "id_category_synonym=" + id_category_synonym +
                ", lang='" + lang + '\'' +
                ", lang_name='" + lang_name + '\'' +
                ", web_reference_wiki='" + web_reference_wiki + '\'' +
                ", category=" + category +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoriesSynonym that = (CategoriesSynonym) o;

        return (getCategory() == that.getCategory() && getWeb_reference_wiki().equals(that.getWeb_reference_wiki()));
    }

    @Override
    public int hashCode() {
        if (category == null) {
            return 999999999;
        } else {
            return getCategory().getId_category();
        }
    }

}
