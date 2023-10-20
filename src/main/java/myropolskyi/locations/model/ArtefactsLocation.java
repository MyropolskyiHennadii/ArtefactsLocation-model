package myropolskyi.locations.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import org.hibernate.annotations.PartitionKey;

import static java.lang.Math.floor;

//Location of Artefact
@Entity
@Table(name = "artefacts_locations")
public class ArtefactsLocation implements AsModelRepresentable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private int id_artefacts_locations;
    @Column
    private double longitude;
    @Column
    private double latitude;
    @Column
    @JsonIgnore
    private String modified;//date-time of last modification
    @Column
    @JsonIgnore
    private String created;//date-time of creation
    @Column
    @JsonIgnore
    private String reviewed;//date-time of last review

    @Column
    @PartitionKey
    @JsonIgnore
    private int int_longitude;//floor(longitude) for partitioning the table

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_artefacts")
    @JsonBackReference(value = "artefacts_location")//important to prevent infinite loop of references
    private Artefact artefact;//foreign key in database

    public ArtefactsLocation() {
    }

    public ArtefactsLocation(double longitude, double latitude, Artefact artefact) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.int_longitude = (int)floor(longitude);
        this.artefact = artefact;
    }

    /*this constructor we need to create artefact_location from native (not hibernate) query*/
    public ArtefactsLocation(int id_artefacts_locations, double longitude, double latitude) {
        this.id_artefacts_locations = id_artefacts_locations;
        this.longitude = longitude;
        this.latitude = latitude;
        this.int_longitude = (int)floor(longitude);
    }

    public int getId() {
        return id_artefacts_locations;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
        this.int_longitude = (int)floor(longitude);
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public Artefact getArtefact() {
        return artefact;
    }

    public void setArtefact(Artefact artefact) {
        this.artefact = artefact;
    }

    public int getInt_longitude() {
        return int_longitude;
    }

    public void setInt_longitude(int int_longitude) {
        this.int_longitude = int_longitude;
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
        return "ArtefactsLocation{" +
                "id_artefacts_locations=" + id_artefacts_locations +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", artefact=" + artefact +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArtefactsLocation that = (ArtefactsLocation) o;

        /*one of them is already written to the database (id!=0), another one can be not*/
        if(getId()*that.getId() == 0 && (getId() + that.getId()) > 0){
            return false;
        }
        /*both are not written in database*/
        if(getId() + that.getId() == 0){
            return (getLatitude() == that.getLatitude()
                    && getLongitude() == that.getLongitude())
                    && getArtefact().equals(that.getArtefact());
        }

        /*both are written in database*/
        return getId() == that.getId();
    }

    @Override
    public int hashCode() {
        return id_artefacts_locations;
    }

}
