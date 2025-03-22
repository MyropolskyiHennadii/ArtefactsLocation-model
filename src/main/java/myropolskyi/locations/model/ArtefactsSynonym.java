package myropolskyi.locations.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Artefact's synonym (name of the artefact in another language and so on)
 */
@Entity
@Table(name = "artefacts_synonyms")
@Data
@NoArgsConstructor
public class ArtefactsSynonym implements AsModelRepresentable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    @Column(name = "id_artefacts_synonyms", nullable = false)
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
    @JoinColumn(name = "id_artefacts")
    @JsonBackReference(value = "artefacts_synonyms")//important to prevent infinite loop of references
    private Artefact artefact;//foreign key in database

    public ArtefactsSynonym(String lang, String lang_name, String web_reference_wiki, Artefact artefact) {
        this.lang = lang;
        this.lang_name = lang_name;
        this.web_reference_wiki = web_reference_wiki;
        this.artefact = artefact;
    }

    @Override
    public String toString() {
        return "ArtefactsSynonym{" +
                "id_artefacts_synonyms=" + id +
                ", lang='" + lang + '\'' +
                ", lang_name='" + lang_name + '\'' +
                ", web_reference_wiki='" + web_reference_wiki + '\'' +
                ", artefact=" + artefact +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArtefactsSynonym that = (ArtefactsSynonym) o;

        /*one of them is already written to the database (id!=0), another one can be not*/
        if(getId()*that.getId() == 0 && (getId() + that.getId()) > 0){
            return false;
        }
        /*both are not written in database*/
        if(getId() + that.getId() == 0){
            return (getWeb_reference_wiki().trim().equals(that.getWeb_reference_wiki().trim())
                    && getArtefact().equals(that.getArtefact()));
        }

        /*both are written in database*/
        return getId() == that.getId();
    }

    @Override
    public int hashCode() {
        return getId();
    }
}
