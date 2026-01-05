package myropolskyi.locations.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Synonym of strictly defined author (WebAuthor) (name of the author in another language and so on)
 */
@Entity
@Table(name = "web_authors_synonyms")
@Data
@NoArgsConstructor
public class WebAuthorsSynonym implements AsModelRepresentable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    @Column(name = "id_web_authors_synonyms", nullable = false)
    private int id;
    @Column(name = "lang_name")
    private String author_name;
    @Column
    private String web_reference_wiki;//wiki-page, unique
    @Column(name = "lang")
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_web_authors")
    @JsonBackReference//important to prevent infinite loop of references
    private WebAuthor webAuthor;//foreign key in database

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WebAuthorsSynonym that = (WebAuthorsSynonym) o;

        /*one of them is already written (id!=0), another one can be not*/
        if(getId()*that.getId() == 0  && (getId() + that.getId()) > 0){
            return false;
        }
        /*both are not written in database*/
        if(getId() + that.getId() == 0){
            return getWeb_reference_wiki().trim().equals(that.getWeb_reference_wiki().trim()) && getWebAuthor().equals(that.getWebAuthor());
        }
        /*both are written in database*/
        return getId() == that.getId();
    }

    @Override
    public int hashCode() {

        return (web_reference_wiki == null) ? 0 : web_reference_wiki.hashCode();
    }

    @Override
    public String toString() {
        return "WebAuthorsSynonym{" +
                "id_authors_synonyms=" + id +
                ", author_name='" + author_name + '\'' +
                ", web_reference_wiki='" + web_reference_wiki + '\'' +
                ", page_language='" + page_language + '\'' +
                ", modified='" + modified + '\'' +
                ", created='" + created + '\'' +
                ", reviewed='" + reviewed + '\'' +
                '}';
    }
}
