package myropolskyi.locations.model;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Artefact's image (link to image)
 */
@Entity
@Table(name = "artefacts_images")
@Data
@NoArgsConstructor
public class ArtefactsImage implements AsModelRepresentable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    @Column(name = "id_image", nullable = false)
    private int id;
    @Column
    private String path_to_image;
    @Column
    @JsonIgnore
    private String image_copyright;

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

    @Transient
    @JsonIgnore
    private boolean includeWikiOutside = false;

    @Override
    public void setIncludeWikiOutsideFieldsInJson(boolean include) {
        this.includeWikiOutside = include;
    }

    @JsonGetter("image_copyright")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getImageCopyrightForJson() {
        return includeWikiOutside ? image_copyright : null;
    }

    @JsonIgnore
    public String getImage_copyright() {
        return image_copyright;
    }

    @JsonSetter("image_copyright")
    public void setImage_copyright(String image_copyright) {
        this.image_copyright = image_copyright;
    }

    /*this constructor we need to create artefact_image from native (not hibernate) query*/
    public ArtefactsImage(String path_to_image, Artefact artefact) {
        this.path_to_image = path_to_image;
        this.artefact = artefact;
    }

    public ArtefactsImage(int id, String path_to_image) {
        this.id = id;
        this.path_to_image = path_to_image;
    }

    @Override
    public String toString() {
        return "ArtefactsImage{" +
                "id_image=" + id +
                ", path_to_image='" + path_to_image + '\'' +
                ", artefact=" + artefact +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArtefactsImage that = (ArtefactsImage) o;

        /*one of them is already written to the database (id!=0), another one can be not*/
        if(getId()*that.getId() == 0 && (getId() + that.getId()) > 0){
            return false;
        }
        /*both are not written in database*/
        if(getId() + that.getId() == 0){
            return getPath_to_image().equals(that.getPath_to_image())
                    && getArtefact().equals(that.getArtefact());
        }

        /*both are written in database*/
        return getId() == that.getId();
    }

    @Override
    public int hashCode() {
        return path_to_image.length();
    }

}
