package myropolskyi.locations.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * class for authors representation in mobile app
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorRepresentation {

    private String lang;/*language*/
    private String name;/*name in language*/
    private String webReference;/*link to wiki*/

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
