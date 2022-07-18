package myropolskyi.locations.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.json.JSONException;
import org.json.JSONObject;

import javax.persistence.*;

@Entity
@Table(name = "artefacts_images")
public class ArtefactsImage implements LocationsJsonRepresentable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_image;
    @Column
    private String path_to_image;

    @Column
    private int updated;//1 = was updated, 0 = wasn't
    @Column
    private int deleted;//1 = was marked as deleted, 0 = wasn't

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_artefacts")
    @JsonBackReference//important to prevent infinite loop of references
    private Artefact artefact;//foreign key in database

    public ArtefactsImage(String path_to_image, int updated, int deleted, Artefact artefact) {
        this.path_to_image = path_to_image;
        this.updated = updated;
        this.deleted = deleted;
        this.artefact = artefact;
    }

    public ArtefactsImage() {
    }

    public int getId_image() {
        return id_image;
    }

    public String getPath_to_image() {
        return path_to_image;
    }

    public void setPath_to_image(String path_to_image) {
        this.path_to_image = path_to_image;
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
     * composes json-representation for ArtefactsImage-exemplar
     */
    public JSONObject composeJsonObject() {
        JSONObject jsonImage = new JSONObject();
        jsonImage.put("id_image", id_image);
        jsonImage.put("path_to_image", path_to_image);
        return jsonImage;
    }

    @Override
    /**
     * decomposes json-representation TO ArtefactsImage-exemplar
     */
    public ArtefactsImage decomposeJsonObject(JSONObject json) throws NumberFormatException, JSONException {
        this.id_image = Integer.parseInt(json.getString("id_image"));
        this.path_to_image = json.getString("path_to_image");
        return this;
    }

    @Override
    public String toString() {
        return "ArtefactsImage{" +
                "id_image=" + id_image +
                ", path_to_image='" + path_to_image + '\'' +
                ", artefact=" + artefact +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArtefactsImage that = (ArtefactsImage) o;
        return path_to_image.equals(that.path_to_image) && (artefact.getId_artefacts() == that.getArtefact().getId_artefacts());
    }

    @Override
    public int hashCode() {
        return path_to_image.length();
    }

}
