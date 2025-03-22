package myropolskyi.locations.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

/**
 * Strictly defined artefacts author (with web-page reference)
 */
@Entity
@Table(name = "web_authors")
@Data
@NoArgsConstructor
public class WebAuthor implements AsModelRepresentable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    @Column(name = "id_web_authors", nullable = false)
    private int id;
    @Column
    private String author_name;
    @Column
    private String web_reference_wiki;//wiki-page, unique
    @Column
    private String page_language;//language of wiki-page
    @Column
    @JsonIgnore
    private String modified;//date-time of last modification
    @Column
    @JsonIgnore
    private String created;//date-time of creation
    @Column
    @JsonIgnore
    private String reviewed;//date-time of last review
    //orphanRemoval = true to refresh all synonyms
    @OneToMany(targetEntity = WebAuthorsSynonym.class, mappedBy = "webAuthor", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference//!!! important to prevent infinite loop with json references
    private Set<WebAuthorsSynonym> webAuthorsSynonyms = new HashSet<>();// foreign key in database. One Artefact = many Authors

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WebAuthor webAuthor = (WebAuthor) o;

        /*one of them is already written (id!=0), another one can be not*/
        if(getId()* webAuthor.getId() == 0 && (getId() + webAuthor.getId()) > 0){
            return false;
        }
        /*both are not written in database*/
        if(getId() + webAuthor.getId() == 0){
            return getWeb_reference_wiki().trim().equals(webAuthor.getWeb_reference_wiki().trim());
        }

        /*both are written in database*/
        return getId() == webAuthor.getId();
    }

    @Override
    public int hashCode() {
        return web_reference_wiki.length();
    }

    @Override
    public String toString() {
        return "WebAuthor{" +
                "id_authors=" + id +
                ", author_name='" + author_name + '\'' +
                ", web_reference_wiki='" + web_reference_wiki + '\'' +
                ", page_language='" + page_language + '\'' +
                ", modified='" + modified + '\'' +
                ", created='" + created + '\'' +
                ", reviewed='" + reviewed + '\'' +
                '}';
    }
}
