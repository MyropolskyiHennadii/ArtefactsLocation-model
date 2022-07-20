package myropolskyi.locations.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.json.JSONException;
import org.json.JSONObject;

import javax.persistence.*;

//Artefact's category
@Entity
@Table(name = "artefacts_categories")
public class ArtefactsCategory implements LocationsJsonRepresentable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_artefacts_categories;
    @Column
    private int updated;//1 = was updated, 0 = wasn't
    @Column
    private int deleted;//1 = was marked as deleted, 0 = wasn't

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_artefacts")
    @JsonBackReference//important to prevent infinite loop of references
    private Artefact artefact;//foreign key in database

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_category_artefact")
    @JsonBackReference//important to prevent infinite loop of references
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

    public int getId_artefacts_categories() {
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

    @Override
    /**
     * composes json-representation for ArtefactsCategory-exemplar
     */
    public JSONObject composeJsonObject() {
        JSONObject jsonArtefactsCategory = new JSONObject();
        jsonArtefactsCategory.put("id_artefacts_categories", id_artefacts_categories);
        jsonArtefactsCategory.put("category", category.composeJsonObject());
        return jsonArtefactsCategory;
    }

    @Override
    /**
     * decomposes json-representation TO ArtefactsCategory-exemplar
     */
    public ArtefactsCategory decomposeJsonObject(JSONObject json) throws NumberFormatException, JSONException {
        this.id_artefacts_categories = json.getInt("id_artefacts_categories");
        this.category = new Category().decomposeJsonObject(json.getJSONObject("category"));
        return this;
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
        return getId_artefacts_categories();
    }

}
