package myropolskyi.locations.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.PartitionKey;

import static java.lang.Math.floor;

//Location of Artefact
@Entity
@Table(name = "artefacts_locations")
public class ArtefactsLocation implements AsModelRepresentable {

    private static int counter;//for comparing objects created with id_artefacts_locations = 0

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_artefacts_locations;
    @Column
    private double longitude;
    @Column
    private double latitude;
    @Column
    @JsonIgnore
    private int updated;//1 = was updated, 0 = wasn't
    @Column
    @JsonIgnore
    private int deleted;//1 = was marked as deleted, 0 = wasn't

    @Column
    @PartitionKey
    @JsonIgnore
    private int int_longitude;//floor(longitude) for partitioning the table

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_artefacts")
    @JsonBackReference(value = "artefacts_location")//important to prevent infinite loop of references
    private Artefact artefact;//foreign key in database

    //for comparing objects created with id_artefacts_locations = 0
    @Transient
    @JsonIgnore
    private int id_temporary;

    public ArtefactsLocation() {
    }

    public ArtefactsLocation(double longitude, double latitude, Artefact artefact) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.int_longitude = (int)floor(longitude);
        this.updated = 1;//always for new exemplar (for database exchange)
        this.artefact = artefact;
        //for comparing objects created with id_artefacts_locations = 0
        this.id_temporary = counter;
        counter++;
    }

    /*this constructor we need to create artefact_location from native (not hibernate) query*/
    public ArtefactsLocation(int id_artefacts_locations, double longitude, double latitude) {
        this.id_artefacts_locations = id_artefacts_locations;
        this.longitude = longitude;
        this.latitude = latitude;
        this.int_longitude = (int)floor(longitude);
        this.updated = 0;//always for new exemplar (for database exchange)
    }

    public int getId_temporary() {
        return id_temporary;
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

    public int getInt_longitude() {
        return int_longitude;
    }

    public void setInt_longitude(int int_longitude) {
        this.int_longitude = int_longitude;
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

        return getArtefact() == that.getArtefact() && getLatitude() == that.getLatitude() && getLongitude() == that.getLongitude();
    }

    @Override
    public int hashCode() {
        return id_artefacts_locations;
    }

}
