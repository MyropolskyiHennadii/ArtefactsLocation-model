package myropolskyi.locations.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "artefacts_images")
public class ArtefactsImage implements AsModelRepresentable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_image;
    @Column
    private String path_to_image;

    @Column
    @JsonIgnore
    private int updated;//1 = was updated, 0 = wasn't
    @Column
    @JsonIgnore
    private int deleted;//1 = was marked as deleted, 0 = wasn't

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_artefacts")
    @JsonBackReference(value = "artefacts_image")//important to prevent infinite loop of references
    private Artefact artefact;//foreign key in database

    /*this constructor we need to create artefact_image from native (not hibernate) query*/
    public ArtefactsImage(String path_to_image, int updated, int deleted, Artefact artefact) {
        this.path_to_image = path_to_image;
        this.updated = updated;
        this.deleted = deleted;
        this.artefact = artefact;
    }

    public ArtefactsImage(int id_image, String path_to_image) {
        this.id_image = id_image;
        this.path_to_image = path_to_image;
        this.deleted = 0;
        this.updated = 0;
    }

    public ArtefactsImage() {
    }

    public int getId() {
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
        return path_to_image.equals(that.path_to_image) && (artefact.getId() == that.getArtefact().getId());
    }

    @Override
    public int hashCode() {
        return path_to_image.length();
    }

}
