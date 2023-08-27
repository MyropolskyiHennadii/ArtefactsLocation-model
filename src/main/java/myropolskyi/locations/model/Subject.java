package myropolskyi.locations.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

//Themas of record
@Entity
@Table(name = "themas")
public class Subject implements LocationsJsonRepresentable {

    @Id
    private String subject_name;
    @Column
    @JsonIgnore
    private int updated;//1 = was updated, 0 = wasn't
    @Column
    @JsonIgnore
    private int deleted;//1 = was marked as deleted, 0 = wasn't

    @OneToMany(targetEntity = Category.class, mappedBy = "subject", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference(value = "thema_categories")//!!! important to prevent infinite loop with json references
    private Set<Category> categories = new HashSet<>();// foreign key in database. One thema = many categories

    public Subject() {
    }

    public Subject(String subject_name) {
        this.subject_name = subject_name;
        this.updated = 1;//always for new exemplar (for database exchange)
    }

    public String getSubject_name() {
        return subject_name;
    }

    public void setSubject_name(String subject_name) {
        this.subject_name = subject_name;
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

    @Override
    public String toString() {
        return "Thema{" +
                ", thema_name='" + subject_name + '\'' +
                ", categories=" + categories +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subject subject = (Subject) o;
        return getSubject_name().equals(subject.getSubject_name());
    }

    @Override
    public int hashCode() {
        return getSubject_name().length();
    }

    /*only to fulfill claim of interface. It does not matter here, because Subject has no int ID*/
    @Override
    public int getId() {
        return 0;
    }
}
