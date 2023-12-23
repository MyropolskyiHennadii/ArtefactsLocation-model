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
All classes are mapped to database tables. The database is filled by data from free source.
All tables are connected by foreign keys with important exceptions: 
* ArtefactsLocation and Artefact are not connected by foreign key.
* ArtefactCategory is announced as Transient in order to minimize time for getting artefacts.
That was done in order to minimize time for searching artefacts by location. Be sure by removing and creating artefacts, that you are removing or creating artefacts locations too. 
ArtefactsLocation-table is partitioned by longitude.

## Version History
**Version 1.2.3** 23.12.2023
* Setter id for authors, events, images and so on: for optimizing queries

**Version 1.2.2** 26.11.2023
* New fields in ArtefactsLocation: for data from reverse geocoding https://api.bigdatacloud.net/data/reverse-geocode-client

**Version 1.2.1** 19.10.2023
* Fields modified, created, reviewed were added to model and to database. Fields updated, deleted were removed.
* new Artefacts field: artefactsInfo, map for simplifying use artefacts fields in android-app.

**Version 1.2.0** 27.08.2023
* Java8 -> java11, hibernate 5 -> 6, other libraries.
* Class Thema was renamed to Subject.

**Version 1.1.4** **(05.05.2023)**
* Method getResumeFromWiki(String wikiPage, String artefactName) was added for exact page, not artefact
