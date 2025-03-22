package myropolskyi.locations.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Category's synonym (name of the category in another language and so on)
 */
@Entity
@Table(name = "categories_synonyms")
@Data
@NoArgsConstructor
public class CategoriesSynonym implements AsModelRepresentable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    @Column(name = "id_category_synonym", nullable = false)
    private int id;
    @Column
    private String lang;//code of language
    @Column
    private String lang_name;//name in language lang
    @Column
    private String web_reference_wiki;
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

    public CategoriesSynonym(String lang, String lang_name, String web_reference_wiki, Category category) {
        this.lang = lang;
        this.lang_name = lang_name;
        this.web_reference_wiki = web_reference_wiki;
        this.category = category;
    }

    /*this constructor we need to create artefact from native (not hibernate) query*/
    public CategoriesSynonym(int id,
                             int id_category,
                             String lang,
                             String lang_name,
                             String web_reference_wiki) {
        this.id = id;
        this.id_category = id_category;
        this.lang = lang;
        this.lang_name = lang_name;
        this.web_reference_wiki = web_reference_wiki;
    }

    @Override
    public String toString() {
        return "CategoriesSynonym{" +
                "id_category_synonym=" + id +
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

        /*one of them is already written to the database (id!=0), another one can be not*/
        if(getId()*that.getId() == 0 && (getId() + that.getId()) > 0){
            return false;
        }
        /*both are not written in database*/
        if(getId() + that.getId() == 0){
            return (getWeb_reference_wiki().trim().equals(that.getWeb_reference_wiki().trim())
                    && getCategory().equals(that.getCategory()));
        }

        /*both are written in database*/
        return getId() == that.getId();
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
