package myropolskyi.locations.model;

/**
 * class for authors representation in mobile app
 */
public class AuthorRepresentation {

    private String lang;/*language*/
    private String name;/*name in language*/
    private String webReference;/*link to wiki*/

    public AuthorRepresentation(String lang, String name, String webReference) {
        this.lang = lang;
        this.name = name;
        this.webReference = webReference;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWebReference() {
        return webReference;
    }

    public void setWebReference(String webReference) {
        this.webReference = webReference;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AuthorRepresentation)) return false;

        AuthorRepresentation that = (AuthorRepresentation) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return name;
    }
}
