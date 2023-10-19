package myropolskyi.locations.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

//Category's synonym in different languages
@Entity
@Table(name = "categories_synonyms")
public class CategoriesSynonym implements AsModelRepresentable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private int id_category_synonym;
    @Column
    private String lang;//code of language
    @Column
    private String lang_name;//name in language lang
    @Column
    private String web_reference_wiki;
    @Column
    @JsonIgnore
    private int updated;//1 = was updated, 0 = wasn't
    @Column
    @JsonIgnore
    private int deleted;//1 = was marked as deleted, 0 = wasn't
    @Column
    @JsonIgnore
    private String modified;//date-time of last modification
    @Column
    @JsonIgnore
    private String created;//date-time of creation
    @Column
    @JsonIgnore
    private String reviewed;//date-time of last review


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_category")
    @JsonBackReference(value = "categories_synonyms")//important to prevent infinite loop of references
    private Category category;//foreign key in database

    @Transient
    @JsonIgnore
    private int id_category;//for native query

    public CategoriesSynonym() {
    }

    public CategoriesSynonym(String lang, String lang_name, String web_reference_wiki, Category category) {
        this.lang = lang;
        this.lang_name = lang_name;
        this.web_reference_wiki = web_reference_wiki;
        this.updated = 1;//always for new exemplar (for database exchange)
        this.category = category;
    }

    /*this constructor we need to create artefact from native (not hibernate) query*/
    public CategoriesSynonym(int id_category_synonym,
                             int id_category,
                             String lang,
                             String lang_name,
                             String web_reference_wiki) {
        this.id_category_synonym = id_category_synonym;
        this.id_category = id_category;
        this.lang = lang;
        this.lang_name = lang_name;
        this.web_reference_wiki = web_reference_wiki;
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

    public int getId() {
        return id_category_synonym;
    }

    @Override
    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
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
            return getCategory().getId();
        }
    }

}
