# ArtefactsLocations-model

Artefact is a concept of a real article (object), which can be described by its geo-coordinates (certainly), link to its valuable description (certainly), category (minimum 1, certainly), subject (certainly), author(s) (not necessarily), history/events (not necessarily), image (not necessarily), synonyms in different languages (not necessarily).

This project contains main classes and methods to work with such artefacts and their properties.

Artefact <-------

                |ArtefactsAuthor (Set)
                |ArtefactsEvent (Set)
                |ArteafctsImage (One)
                |ArtefactsLocation (One)
                |ArtefactsSynonym (Set)
                |ArtefactsCategory (Set) <--------
                                            |Category<-----
                                                            |CategoriesSynonym (Set)
                                                            |Thema (One)

## Version History

**Version 1.2.0** 27.08.2023
* Java8 -> java11, hibernate 5 -> 6, other libraries.
* Class Thema was renamed to Subject.

**Version 1.1.4** **(05.05.2023)**
* Method getResumeFromWiki(String wikiPage, String artefactName) was added for exact page, not artefact
