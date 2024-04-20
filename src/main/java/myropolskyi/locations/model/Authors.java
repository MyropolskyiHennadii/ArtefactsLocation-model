package myropolskyi.locations.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
@Table(name = "authors")
public class Authors implements AsModelRepresentable{

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

    public Authors() {
    }

    public void setId_authors(int id_authors) {
        this.id_authors = id_authors;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public void setWeb_reference_wiki(String web_reference_wiki) {
        this.web_reference_wiki = web_reference_wiki;
    }

    public void setPage_language(String page_language) {
        this.page_language = page_language;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public void setReviewed(String reviewed) {
        this.reviewed = reviewed;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public String getWeb_reference_wiki() {
        return web_reference_wiki;
    }

    public String getPage_language() {
        return page_language;
    }

    @Override
    public int getId() {
        return id_authors;
    }

    @Override
    public String getModified() {
        return modified;
    }

    @Override
    public String getCreated() {
        return created;
    }

    @Override
    public String getReviewed() {
        return reviewed;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Authors authors = (Authors) o;

        /*one of them is already written (id!=0), another one can be not*/
        if(getId()*authors.getId() == 0 && (getId() + authors.getId()) > 0){
            return false;
        }
        /*both are not written in database*/
        if(getId() + authors.getId() == 0){
            return getWeb_reference_wiki().trim().equals(authors.getWeb_reference_wiki().trim());
        }

        /*both are written in database*/
        return getId() == authors.getId();
    }

    @Override
    public int hashCode() {
        return web_reference_wiki.length();
    }
}
