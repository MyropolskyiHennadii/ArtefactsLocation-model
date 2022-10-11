package myropolskyi.locations.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

//Author of Artefact
@Entity
@Table(name = "artefacts_authors")
public class ArtefactsAuthor implements LocationsJsonRepresentable {

    private static int counter;//for comparing objects created with id_artefacts_authors = 0

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_artefacts_authors;
    @Column
    private String author_name;
    @Column
    @JsonIgnore
    private int updated;//1 = was updated, 0 = wasn't
    @Column
    @JsonIgnore
    private int deleted;//1 = was marked as deleted, 0 = wasn't

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_artefacts")
    @JsonBackReference//important to prevent infinite loop of references
    private Artefact artefact;//foreign key in database

    //for comparing objects created with id_events_artefacts = 0
    @Transient
    private int id_temporary;

    public ArtefactsAuthor() {
    }

    public ArtefactsAuthor(String author_name, Artefact artefact) {
        this.author_name = author_name;
        this.artefact = artefact;
        this.updated = 1;//always for new exemplar (for database exchange)

        //for comparing objects created with id_artefacts_authors = 0
        this.id_temporary = counter;
        counter++;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
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

    public Artefact getArtefact() {
        return artefact;
    }

    public void setArtefact(Artefact artefact) {
        this.artefact = artefact;
    }

    public int getId_artefacts_authors() {
        return id_artefacts_authors;
    }

    public int getId_temporary() {
        return id_temporary;
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

        return getAuthor_name().equals(that.getAuthor_name()) && getArtefact() == that.getArtefact();
    }

    @Override
    public int hashCode() {
        return getId_artefacts_authors();
    }
}
