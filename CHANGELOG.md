# Changelog

All notable changes to this project will be documented in this file.

## [2.2.2] - 2026-02-08
* New class Announcement for JsonArtefactWrapper: it is used my sending message to smartphone-client

## [2.2.1] - 2026-01-31
* java17 -> java21

## [2.2.0] - 2026-01-18
* Libraries were refreshed
* New fields in image (copyright) and artefact (is_outside_wiki)

## [2.1.2-jakarta] - 2025-09-24
* Libraries were refreshed
* User-Agent was changed to LookAroundArchitecture/2.5.3 (https://play.google.com/store/apps/details?id=myropolskyi.android.locations&pcampaignid=web_share), contact: Hennadii.Myropolskyi@outlook.com

## [2.1.1-jakarta] - 2025-09-14
* Set for Wiki-Resume conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36");

## [2.1.0-jakarta] - 2025-03-22
* java11 -> java17
* 2025-05-01: hashcode for web_reference_wiki in web-author was corrected

## [2.0.1] - 2024-09-21
* New class AuthorRepresentation
* Libraries were refreshed

## [2.0.0] - 2024-04-20
* New Classes: Authors, AuthorsSynonyms
* org.slf4j

## [1.2.4] - 2024-04-07
* logj42 -> 2.23.1, fasterxml -> 2.17.0, hibernate -> 6.4.4.Final, mavencompiler.version -> 3.13.0

## [1.2.3] - 2023-12-23
* Setter id for authors, events, images and so on: for optimizing queries

## [1.2.2] - 2023-11-26
* New fields in ArtefactsLocation: for data from reverse geocoding https://api.bigdatacloud.net/data/reverse-geocode-client

## [1.2.1] - 2023-10-19
* Fields modified, created, reviewed were added to model and to database. Fields updated, deleted were removed
* New Artefacts field: artefactsInfo, map for simplifying use artefacts fields in android-app

## [1.2.0] - 2023-08-27
* Java8 -> java11, hibernate 5 -> 6, other libraries
* Class Thema was renamed to Subject

## [1.1.4] - 2023-05-05
* Method getResumeFromWiki(String wikiPage, String artefactName) was added for exact page, not artefact
