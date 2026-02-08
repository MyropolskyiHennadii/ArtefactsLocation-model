# ArtefactsLocations-model

Artefact is a concept of a real article (object), which can be described by its geo-coordinates (certainly), link to its valuable description (certainly), category (minimum 1, certainly), subject (certainly), webAuthor(s) (not necessarily), history/events (not necessarily), image (not necessarily), synonyms in different languages (not necessarily).

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

**Version 2.2.2** 08.02.2026
* new class Announcement for JsonArtefactWrapper: it is used my sending message to smartphone-client 

**Version 2.2.1** 31.01.2026
* java17 -> java21

**Version 2.2.0** 18.01.2026
* libraries were refreshed
* new fields in image (copyright) and artefact (is_outside_wiki)

**Version 2.1.2-jakarta** 24.09.2025
* libraries were refreshed
* User-Agent was changed to
  LookAroundArchitecture/2.5.3 (https://play.google.com/store/apps/details?id=myropolskyi.android.locations&pcampaignid=web_share), contact: Hennadii.Myropolskyi@outlook.com

**Version 2.1.1-jakarta** 14.09.2025
* set for Wiki-Resume conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36");

**Version 2.1.0-jakarta** 22.03.2025
* java11 -> java17
* 01.05.2025: hashcode for web_reference_wiki in web-author was corrected

**Version 2.0.1** 21.09.2024
* new class AuthorRepresentation. Libraries were refreshed

**Version 2.0.0** 20.04.2024
* new Classes: Authors, AuthorsSynonyms
* org.slf4j


**Version 1.2.4** 07.04.2024
* logj42 -> 2.23.1, fasterxml -> 2.17.0, hibernate -> 6.4.4.Final, mavencompiler.version -> 3.13.0

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
