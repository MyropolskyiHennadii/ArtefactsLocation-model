package myropolskyi.locations.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

/**
 * Categories, classified artefacts (style of building for instance
 */
@Entity
@Table(name = "categories")
@Data
@NoArgsConstructor
public class Category implements AsModelRepresentable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private int id_category;
    @Column
    private String category_name;
    @Column
    private String web_reference_wiki;
    @Column
    private String page_language;//language of web-page
    @Column
    @JsonIgnore
    private String type_category;// style, temporal or other type
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
    @JoinColumn(name = "thema")
    @JsonIgnore
    //@JsonBackReference(value = "thema_categories")//important to prevent infinite loop of references
    private Subject subject;//foreign key in database

    //orphanRemoval = true to refresh all synonyms
    @OneToMany(targetEntity = CategoriesSynonym.class, mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    //@OneToMany(targetEntity= CategoriesSynonym.class, mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference(value = "categories_synonyms")//!!! important to prevent infinite loop with json references
    private Set<CategoriesSynonym> synonyms = new HashSet<>();// foreign key in database. One Artefact = many synonyms

    //orphanRemoval = true to refresh all synonyms
    @OneToMany(targetEntity = ArtefactsCategory.class, mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    //@JsonManagedReference(value = "id_categories")//!!! important to prevent infinite loop with json references
    @JsonIgnore
    private Set<ArtefactsCategory> artefactsCategories = new HashSet<>();// foreign key in database. One Artefact = many categories

    public Category(String category_name, String web_reference_wiki, String lang, String typeCategory, Subject subject) {
        this.category_name = category_name;
        this.web_reference_wiki = web_reference_wiki;
        this.page_language = lang;
        this.type_category = typeCategory;
        this.subject = subject;
    }

    /*this constructor we need to create artefact from native (not hibernate) query*/
    public Category(int id_category,
                    String subject,
                    String category_name,
                    String typeCategory,
                    String web_reference_wiki,
                    String lang) {
        this.id_category = id_category;
        this.subject = new Subject(subject);
        this.category_name = category_name;
        this.type_category = typeCategory;
        this.web_reference_wiki = web_reference_wiki;
        this.page_language = lang;
    }

    /*for compatibility with previous versions:*/
    @JsonGetter(value = "id")
    public int getId() {
        return id_category;
    }
    /*for compatibility with previous versions:*/
    public Subject getThema() {
        return subject;
    }
    /*for compatibility with previous versions:*/
    public void setThema(Subject subject) {
        this.subject = subject;
    }

    //make json compact; without references to object. just name of the thema
    @JsonGetter(value = "thema")
    private String getThemaName() {
        return subject.getThema_name();
    }

    @Override
    public String toString() {
        return "Category{" +
                "id_category=" + id_category +
                ", category_name='" + category_name + '\'' +
                ", page_language='" + page_language + '\'' +
                ", web_reference_wiki='" + web_reference_wiki +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category that = (Category) o;

        /*one of them is already written to the database (id!=0), another one can be not*/
        if(getId()*that.getId() == 0 && (getId() + that.getId()) > 0){
            return false;
        }
        /*both are not written in database*/
        if(getId() + that.getId() == 0){
            return (getWeb_reference_wiki().trim().equals(that.getWeb_reference_wiki().trim()));
        }

        /*both are written in database*/
        return getId() == that.getId();

    }

    @Override
    public int hashCode() {
        return getId();
    }

}
