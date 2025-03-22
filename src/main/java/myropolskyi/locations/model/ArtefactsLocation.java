package myropolskyi.locations.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.PartitionKey;

import static java.lang.Math.floor;

/**
 * Artefact's location (latitude, longitude and other attributes of location)
 */
@Entity
@Table(name = "artefacts_locations")
@Data
@NoArgsConstructor
public class ArtefactsLocation implements AsModelRepresentable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    @Column(name = "id_artefacts_locations", nullable = false)
    private int id;
    @Column
    private double longitude;
    @Column
    private double latitude;
    /*for data from reverse geocoding https://api.bigdatacloud.net/data/reverse-geocode-client*/
    @Column
    @JsonIgnore
    private String continent;
    @Column
    @JsonIgnore
    private String country;
    @Column
    @JsonIgnore
    private String subdivision;
    @Column
    @JsonIgnore
    private String city;
    @Column
    @JsonIgnore
    private String locality;
    @Column
    @JsonIgnore
    private String localityinfo;
    @Column
    @JsonIgnore
    private String postcode;
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

    public ArtefactsLocation(double longitude, double latitude, Artefact artefact) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.int_longitude = (int)floor(longitude);
        this.artefact = artefact;
    }

    /*this constructor we need to create artefact_location from native (not hibernate) query*/
    public ArtefactsLocation(int id, double longitude, double latitude) {
        this.id = id;
        this.longitude = longitude;
        this.latitude = latitude;
        this.int_longitude = (int)floor(longitude);
    }

    @Override
    public String toString() {
        return "ArtefactsLocation{" +
                "id_artefacts_locations=" + id +
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
        return id;
    }

}
