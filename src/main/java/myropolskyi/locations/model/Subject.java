package myropolskyi.locations.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Subject of artefacts (Architecture for instance)
 */
@Entity
@Table(name = "themas")
public class Subject implements AsModelRepresentable {

    @Id
    @JsonProperty("id")
    private String thema_name;
    @Column
    @JsonIgnore
    private String modified;//date-time of last modification
    @Column
    @JsonIgnore
    private String created;//date-time of creation
    @Column
    @JsonIgnore
    private String reviewed;//date-time of last review

    @OneToMany(targetEntity = Category.class, mappedBy = "subject", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    /*@JsonManagedReference(value = "thema_categories")//!!! important to prevent infinite loop with json references*/
    @JsonIgnore
    private Set<Category> categories = new HashSet<>();// foreign key in database. One thema = many categories

    public Subject() {
    }

    public Subject(String thema_name) {
        this.thema_name = thema_name;
    }

    public String getThema_name() {
        return thema_name;
    }

    public void setThema_name(String subject_name) {
        this.thema_name = subject_name;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
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
        return "Thema{" +
                ", thema_name='" + thema_name + '\'' +
                ", categories=" + categories +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subject subject = (Subject) o;
        return getThema_name().equals(subject.getThema_name());
    }

    @Override
    public int hashCode() {
        return getThema_name().length();
    }

    /*only to fulfill claim of interface. It does not matter here, because Subject has no int ID*/
    @Override
    @JsonGetter("thema_name")/* formal*/
    public int getId() {
        return 0;
    }
}
