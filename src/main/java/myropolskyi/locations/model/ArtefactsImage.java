package myropolskyi.locations.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
@Table(name = "artefacts_images")
public class ArtefactsImage implements AsModelRepresentable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private int id_image;
    @Column
    private String path_to_image;

    @Column
    @JsonIgnore
    private String modified;//date-time of last modification
    @Column
    @JsonIgnore
    private String created;//date-time of creation
    @Column
    @JsonIgnore
    private String reviewed;//date-time of last review


    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_artefacts")
    @JsonBackReference(value = "artefacts_image")//important to prevent infinite loop of references
    private Artefact artefact;//foreign key in database

    /*this constructor we need to create artefact_image from native (not hibernate) query*/
    public ArtefactsImage(String path_to_image, Artefact artefact) {
        this.path_to_image = path_to_image;
        this.artefact = artefact;
    }

    public ArtefactsImage(int id_image, String path_to_image) {
        this.id_image = id_image;
        this.path_to_image = path_to_image;
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
