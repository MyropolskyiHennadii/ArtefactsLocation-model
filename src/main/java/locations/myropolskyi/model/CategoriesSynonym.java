package locations.myropolskyi.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.*;

//Category's synonym in different languages
@Entity
@Table(name = "categories_synonyms")
public class CategoriesSynonym {

    private static final Logger logger = LogManager.getLogger(ArtefactsSynonym.class);

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
    @JsonBackReference//important to prevent infinite loop of references
    private locations.myropolskyi.model.Category category;//foreign key in database

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
