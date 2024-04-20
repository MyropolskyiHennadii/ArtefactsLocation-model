package myropolskyi.locations.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "web_authors")
public class WebAuthor implements AsModelRepresentable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private int id_authors;
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
    private Set<WebAuthorsSynonym> webAuthors = new HashSet<>();// foreign key in database. One Artefact = many Authors

    public WebAuthor() {
    }

    public void setId_authors(int id_authors) {
        this.id_authors = id_authors;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public String getWeb_reference_wiki() {
        return web_reference_wiki;
    }

    public void setWeb_reference_wiki(String web_reference_wiki) {
        this.web_reference_wiki = web_reference_wiki;
    }

    public String getPage_language() {
        return page_language;
    }

    public void setPage_language(String page_language) {
        this.page_language = page_language;
    }

    @Override
    public int getId() {
        return id_authors;
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

    public Set<WebAuthorsSynonym> getWebAuthors() {
        return webAuthors;
    }

    public void setWebAuthors(Set<WebAuthorsSynonym> authors) {
        this.webAuthors = authors;
    }

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
                "id_authors=" + id_authors +
                ", author_name='" + author_name + '\'' +
                ", web_reference_wiki='" + web_reference_wiki + '\'' +
                ", page_language='" + page_language + '\'' +
                ", modified='" + modified + '\'' +
                ", created='" + created + '\'' +
                ", reviewed='" + reviewed + '\'' +
                '}';
    }
}
