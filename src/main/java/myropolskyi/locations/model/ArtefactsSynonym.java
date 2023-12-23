package myropolskyi.locations.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

//Artefact's synonym in different languages
@Entity
@Table(name = "artefacts_synonyms")
public class ArtefactsSynonym implements AsModelRepresentable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private int id_artefacts_synonyms;
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

    public ArtefactsSynonym() {
    }

    public ArtefactsSynonym(String lang, String lang_name, String web_reference_wiki, Artefact artefact) {
        this.lang = lang;
        this.lang_name = lang_name;
        this.web_reference_wiki = web_reference_wiki;
        this.artefact = artefact;
    }

    public int getId() {
        return id_artefacts_synonyms;
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

    public Artefact getArtefact() {
        return artefact;
    }

    public void setArtefact(Artefact artefact) {
        this.artefact = artefact;
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

    public void setId_artefacts_synonyms(int id_artefacts_synonyms) {
        this.id_artefacts_synonyms = id_artefacts_synonyms;
    }

    @Override
    public String toString() {
        return "ArtefactsSynonym{" +
                "id_artefacts_synonyms=" + id_artefacts_synonyms +
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
