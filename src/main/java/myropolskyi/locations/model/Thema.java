package myropolskyi.locations.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.json.JSONException;
import org.json.JSONObject;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

//Themas of record
@Entity
@Table(name = "themas")
public class Thema implements LocationsJsonRepresentable {

    @Id
    private String thema_name;
    @Column
    @JsonIgnore
    private int updated;//1 = was updated, 0 = wasn't
    @Column
    @JsonIgnore
    private int deleted;//1 = was marked as deleted, 0 = wasn't

    @OneToMany(targetEntity = Category.class, mappedBy = "thema", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference(value = "thema_categories")//!!! important to prevent infinite loop with json references
    private Set<Category> categories = new HashSet<>();// foreign key in database. One thema = many categories

    public Thema() {
    }

    public Thema(String thema_name) {
        this.thema_name = thema_name;
        this.updated = 1;//always for new exemplar (for database exchange)
    }

    public String getThema_name() {
        return thema_name;
    }

    public void setThema_name(String thema_name) {
        this.thema_name = thema_name;
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

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    /**
     * composes json-representation for Thema-exemplar
     */
    @Override
    public JSONObject composeJsonObject() {
        JSONObject jsonThema = new JSONObject();
        jsonThema.put("thema_name", thema_name);
        return jsonThema;
    }

    /**
     * decomposes json-representation TO Thema-exemplar
     */
    @Override
    public Thema decomposeJsonObject(JSONObject json) throws NumberFormatException, JSONException {
        this.thema_name = json.getString("thema_name");
        return this;
    }


    @Override
    public String toString() {
        return "Thema{" +
                ", thema_name='" + thema_name + '\'' +
                ", categories=" + categories +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Thema thema = (Thema) o;
        return getThema_name().equals(thema.getThema_name());
    }

    @Override
    public int hashCode() {
        return getThema_name().length();
    }
}
