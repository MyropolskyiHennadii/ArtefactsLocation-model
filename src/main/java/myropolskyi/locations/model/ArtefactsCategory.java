package myropolskyi.locations.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

//Artefact's category
@Entity
@Table(name = "artefacts_categories")
public class ArtefactsCategory implements AsModelRepresentable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private int id_artefacts_categories;
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
    @JoinColumn(name = "id_artefacts")
    @JsonBackReference(value = "artefacts_categories")//important to prevent infinite loop of references
    private Artefact artefact;//foreign key in database

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_category_artefact")
    //@JsonBackReference(value = "id_categories")//important to prevent infinite loop of references
    //try:
    //@JsonBackReference(value = "getIdMainCategory")//important to prevent infinite loop of references
    @JsonIgnore
    private Category category;//foreign key in database

    @Transient
    @JsonIgnore
    private int id_artefacts;//for native query

    @Transient
    @JsonIgnore
    private int id_category_artefact;//for native query

    public int getId_category_artefact() {//for native query
        return id_category_artefact;
    }

    public ArtefactsCategory() {
    }

    public ArtefactsCategory(Artefact artefact, Category category) {
        this.artefact = artefact;
        this.category = category;
    }

    /*this constructor we need to create artefact from native (not hibernate) query*/
    public ArtefactsCategory(int id_artefacts_categories, int id_artefacts, int id_category_artefact) {
        this.id_artefacts_categories = id_artefacts_categories;
        this.id_artefacts = id_artefacts;
        this.id_category_artefact = id_category_artefact;
    }

    public Artefact getArtefact() {
        return artefact;
    }

    public void setArtefact(Artefact artefact) {
        this.artefact = artefact;
    }

    public int getId() {
        return id_artefacts_categories;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    /*to download integer Category's id*/
    @JsonGetter("category")
    public Integer getIdMainCategory(){
        return category.getId();
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
        return "ArtefactsCategory{" +
                "id_artefacts_categories=" + id_artefacts_categories +
                ", artefact=" + artefact +
                ", category =" + category +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArtefactsCategory that = (ArtefactsCategory) o;

        /*one of them is already written (id!=0) to the database, another one can be not*/
        if(getId()*that.getId() == 0 && (getId() + that.getId()) > 0){
            return false;
        }
        /*both are not written in database*/
        if(getId() + that.getId() == 0){
            return getId_category_artefact()==that.getId_category_artefact()  && getArtefact().equals(that.getArtefact());
        }

        /*both are written in database*/
        return getId() == that.getId();

    }

    @Override
    public int hashCode() {
        return getId();
    }

}
