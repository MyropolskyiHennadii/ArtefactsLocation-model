package myropolskyi.locations.model;

/*interface serves only as mark to gather all model classes*/
public interface AsModelRepresentable {

    int getId();
    String getModified();
    String getCreated();
    String getReviewed();

    // Method to control inclusion of new fields in JSON for backward compatibility
    default void setIncludeWikiOutsideFieldsInJson(boolean include) {
        // Default implementation does nothing - override in classes that have new fields
    }
}
