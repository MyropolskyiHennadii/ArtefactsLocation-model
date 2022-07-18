package myropolskyi.locations.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.json.JSONObject;

import javax.persistence.*;

//Artefact's synonym in different languages
@Entity
@Table(name = "artefacts_synonyms")
public class ArtefactsSynonym implements LocationsJsonRepresentable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_artefacts_synonyms;
    @Column
    private String lang;//code of language
    @Column
    private String lang_name;//name in language lang
    @Column
    private String web_reference_wiki;
    @Column
    private int updated;//1 = was updated, 0 = wasn't
    @Column
    private int deleted;//1 = was marked as deleted, 0 = wasn't


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_artefacts")
    @JsonBackReference//important to prevent infinite loop of references
    private Artefact artefact;//foreign key in database

    public ArtefactsSynonym() {
    }

    public ArtefactsSynonym(String lang, String lang_name, String web_reference_wiki, Artefact artefact) {
        this.lang = lang;
        this.lang_name = lang_name;
        this.web_reference_wiki = web_reference_wiki;
        this.updated = 1;//always for new exemplar (for database exchange)
        this.artefact = artefact;
    }

    public int getId_artefacts_synonyms() {
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

    @Override
    /**
     * composes json-representation for ArtefactSynonym-exemplar
     */
    public JSONObject composeJsonObject(){
        JSONObject jsonSynonym = new JSONObject();
        jsonSynonym.put("id_artefacts_synonyms", id_artefacts_synonyms);
        jsonSynonym.put("lang", lang);
        jsonSynonym.put("lang_name", lang_name);
        jsonSynonym.put("web_reference_wiki", web_reference_wiki);
        return jsonSynonym;
    }
    
     @Override
     /**
     * decomposes json-representation TO ArtefactSynonym-exemplar
     */
    public ArtefactSynonym decomposeJsonObject(JSONObject json) throws NumberFormatException, JSONException {
        this.id_artefacts_synonyms = Integer.parseInt(json.getString("id_artefacts_synonyms"));
        this.lang = json.getString("lang");
        this.lang_name = json.getString("lang_name");
        this.web_reference_wiki = json.getString("web_reference_wiki");
        return this;
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

        return getArtefact() == that.getArtefact() && getWeb_reference_wiki().equals(that.getWeb_reference_wiki());
    }

    @Override
    public int hashCode() {
        return getId_artefacts_synonyms();
    }
}
