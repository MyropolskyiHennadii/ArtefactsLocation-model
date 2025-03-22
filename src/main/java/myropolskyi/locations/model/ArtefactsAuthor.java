package myropolskyi.locations.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Author of artefact (as they are written in artefact's webpage, also as not well defined strings)
 */
@Entity
@Table(name = "artefacts_authors")
@Data
@NoArgsConstructor
public class ArtefactsAuthor implements AsModelRepresentable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    @Column(name = "id_artefacts_authors", nullable = false)
    private int id;
    @Column
    private String author_name;
    @Column
    @JsonProperty("id_web_authors")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    /*with old (first) versions of android apps this column did not exist in database.
    * It appears at LookAroundArchitecture only for 2.1.2 version.
    * This "String" means reference to WebAuthor-id*/
    private String id_web_authors = "";
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

    public ArtefactsAuthor(String author_name, Artefact artefact) {
        this.author_name = author_name;
        this.artefact = artefact;
        this.id_web_authors = "";
    }

    public ArtefactsAuthor(String author_name, Artefact artefact, String id_web_authors) {
        this.author_name = author_name;
        this.artefact = artefact;
        this.id_web_authors = id_web_authors;
    }

    @Override
    public String toString() {
        return "ArtefactsAuthor{" +
                "id_artefacts_authors=" + id +
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
        if (getId() * that.getId() == 0 && (getId() + that.getId()) > 0) {
            return false;
        }
        /*both are not written in database*/
        if (getId() + that.getId() == 0) {
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
