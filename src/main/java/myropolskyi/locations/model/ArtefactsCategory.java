package myropolskyi.locations.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Artefact's category (one building can be related to many styles of architecture for instance)
 */
@Entity
@Table(name = "artefacts_categories")
@Data
@NoArgsConstructor
public class ArtefactsCategory implements AsModelRepresentable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    @Column(name = "id_artefacts_categories", nullable = false)
    private int id;
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

    public ArtefactsCategory(Artefact artefact, Category category) {
        this.artefact = artefact;
        this.category = category;
    }

    /*this constructor we need to create artefact from native (not hibernate) query*/
    public ArtefactsCategory(int id_artefacts_categories, int id_artefacts, int id_category_artefact) {
        this.id = id_artefacts_categories;
        this.id_artefacts = id_artefacts;
        this.id_category_artefact = id_category_artefact;
    }

    /*to download integer Category's id*/
    @JsonGetter("category")
    public Integer getIdMainCategory(){
        return category.getId();
    }

    @Override
    public String toString() {
        return "ArtefactsCategory{" +
                "id_artefacts_categories=" + id +
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
