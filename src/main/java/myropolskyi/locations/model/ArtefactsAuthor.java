package myropolskyi.locations.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

//Author of Artefact
@Entity
@Table(name = "artefacts_authors")
public class ArtefactsAuthor implements AsModelRepresentable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private int id_artefacts_authors;
    @Column
    private String author_name;

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
    @JsonBackReference//important to prevent infinite loop of references
    private Artefact artefact;//foreign key in database

    public ArtefactsAuthor() {
    }

    public ArtefactsAuthor(String author_name, Artefact artefact) {
        this.author_name = author_name;
        this.artefact = artefact;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public Artefact getArtefact() {
        return artefact;
    }

    public void setArtefact(Artefact artefact) {
        this.artefact = artefact;
    }

    public int getId() {
        return id_artefacts_authors;
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
        return "ArtefactsAuthor{" +
                "id_artefacts_authors=" + id_artefacts_authors +
                ", author_name='" + author_name + '\'' +
                ", artefact=" + artefact +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArtefactsAuthor that = (ArtefactsAuthor) o;

        /*one of them is already written (id!=0), another one can be not*/
        if(getId()*that.getId() == 0  && (getId() + that.getId()) > 0){
            return false;
        }
        /*both are not written in database*/
        if(getId() + that.getId() == 0){
            return getAuthor_name().trim().equals(that.getAuthor_name().trim()) && getArtefact().equals(that.getArtefact());
        }
        /*both are written in database*/
        return getId() == that.getId();

    }

    @Override
    public int hashCode() {
        return getId();
    }
}
