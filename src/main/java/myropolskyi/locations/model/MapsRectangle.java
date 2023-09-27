package myropolskyi.locations.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;


/**
 * class saves info about borders of geographic regions
 */
@Entity
@Table(name = "regions")
public class MapsRectangle implements AsModelRepresentable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private int idregion;

    /*to which "real region" belongs this rectangle*/
    @Column
    private String region_name;
    @Column
    private Double left_bottom_longitude;
    @Column
    private Double left_bottom_latitude;
    @Column
    private Double right_top_longitude;
    @Column
    private Double right_top_latitude;

    public MapsRectangle() {
    }

    public MapsRectangle(String region_name, Double left_bottom_longitude, Double left_bottom_latitude, Double right_top_longitude, Double right_top_latitude) {
        this.region_name = region_name;
        this.left_bottom_longitude = left_bottom_longitude;
        this.left_bottom_latitude = left_bottom_latitude;
        this.right_top_longitude = right_top_longitude;
        this.right_top_latitude = right_top_latitude;
    }

    public int getId() {
        return idregion;
    }

    public void setIdregion(int idregion) {
        this.idregion = idregion;
    }

    public String getRegion_name() {
        return region_name;
    }

    public void setRegion_name(String region_name) {
        this.region_name = region_name;
    }

    public Double getLeft_bottom_longitude() {
        return left_bottom_longitude;
    }

    public void setLeft_bottom_longitude(Double left_bottom_longitude) {
        this.left_bottom_longitude = left_bottom_longitude;
    }

    public Double getLeft_bottom_latitude() {
        return left_bottom_latitude;
    }

    public void setLeft_bottom_latitude(Double left_bottom_latitude) {
        this.left_bottom_latitude = left_bottom_latitude;
    }

    public Double getRight_top_longitude() {
        return right_top_longitude;
    }

    public void setRight_top_longitude(Double right_top_longitude) {
        this.right_top_longitude = right_top_longitude;
    }

    public Double getRight_top_latitude() {
        return right_top_latitude;
    }

    public void setRight_top_latitude(Double right_top_latitude) {
        this.right_top_latitude = right_top_latitude;
    }

    @Override
    public String toString() {
        return "MapsRectangle{" +
                "idregion=" + idregion +
                ", region_name='" + region_name + '\'' +
                ", left_bottom_longitude=" + left_bottom_longitude +
                ", left_bottom_latitude=" + left_bottom_latitude +
                ", right_top_longitude=" + right_top_longitude +
                ", right_top_latitude=" + right_top_latitude +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MapsRectangle mapsRectangle = (MapsRectangle) o;
        return idregion == mapsRectangle.idregion;
    }

    @Override
    public int hashCode() {
        return region_name.length();
    }
}
