package myropolskyi.locations.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.json.JSONException;
import org.json.JSONObject;

import javax.persistence.*;

//Location of Artefact
@Entity
@Table(name = "artefacts_locations")
public class ArtefactsLocation implements LocationsJsonRepresentable {

    private static int counter;//for comparing objects created with id_artefacts_locations = 0

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_artefacts_locations;
    @Column
    private double longitude;
    @Column
    private double latitude;
    @Column
    private int updated;//1 = was updated, 0 = wasn't
    @Column
    private int deleted;//1 = was marked as deleted, 0 = wasn't

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_artefacts")
    @JsonBackReference//important to prevent infinite loop of references
    private Artefact artefact;//foreign key in database

    //for comparing objects created with id_artefacts_locations = 0
    @Transient
    private int id_temporary;

    public ArtefactsLocation() {
    }

    public ArtefactsLocation(double longitude, double latitude, Artefact artefact) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.updated = 1;//always for new exemplar (for database exchange)
        this.artefact = artefact;
        //for comparing objects created with id_artefacts_locations = 0
        this.id_temporary = counter;
        counter++;
    }

    public int getId_temporary() {
        return id_temporary;
    }

    public int getId_artefacts_locations() {
        return id_artefacts_locations;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
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

    @Override
    /**
     * composes json-representation for ArtefactsLocation-exemplar
     */
    public JSONObject composeJsonObject() {
        JSONObject jsonLocation = new JSONObject();
        jsonLocation.put("id_artefacts_locations", id_artefacts_locations);
        jsonLocation.put("longitude", longitude);
        jsonLocation.put("latitude", latitude);
        return jsonLocation;
    }

    @Override
    /**
     * decomposes json-representation TO ArtefactsLocation-exemplar
     */
    public ArtefactsLocation decomposeJsonObject(JSONObject json) throws NumberFormatException, JSONException {
        this.id_artefacts_locations = json.getInt("id_artefacts_locations");
        this.longitude = json.getDouble("longitude");
        this.latitude = json.getDouble("latitude");
        return this;
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
