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
    private int updated;//1 = was updated, 0 = wasn't
    @Column
    @JsonIgnore
    private int deleted;//1 = was marked as deleted, 0 = wasn't

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

    public ArtefactsCategory() {
    }

    public ArtefactsCategory(Artefact artefact, Category category) {
        this.artefact = artefact;
        this.category = category;
        this.updated = 1;//always for new exemplar (for database exchange)
    }

    public Artefact getArtefact() {
        return artefact;
    }

    public void setArtefact(Artefact artefact) {
        this.artefact = artefact;
    }

    @JsonGetter("id_artefacts_categories")
    public int getId() {
        return id_artefacts_categories;
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

    /*to download integer Category's id*/
    @JsonGetter("category")
    public Integer getIdMainCategory(){
        return category.getId();
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

        return (getArtefact() == that.getArtefact() && getCategory() == that.getCategory());
    }

    @Override
    public int hashCode() {
        return getId();
    }

}
