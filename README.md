# ArtefactsLocation-model

A Java 21 Hibernate ORM model library for managing architectural and cultural heritage artefacts with geolocation data.

## Overview

This project provides a comprehensive data model for representing real-world architectural objects (artefacts) with their geographical locations, historical information, categorization, and multilingual support. It's designed to work with location-based applications that showcase cultural and architectural heritage.

## Features

- **Geolocation Support**: Store and query artefacts by geographical coordinates (longitude/latitude)
- **Multilingual**: Support for artefact and category names in multiple languages via synonyms
- **Rich Metadata**: Track authors, historical events, images, and Wikipedia references
- **Flexible Categorization**: Multiple categories per artefact with subject-based organization
- **JSON Serialization**: Full Jackson support for REST API integration
- **Optimized Queries**: Partitioned tables and transient fields for performance
- **Hibernate ORM**: JPA/Hibernate entities ready for database persistence

## Data Model

### Core Entities

```
Artefact (Main Entity)
├── ArtefactsLocation (1:1) - Geographical coordinates
├── ArtefactsImage (1:1) - Image reference and copyright
├── ArtefactsAuthor (1:N) - Authors/architects (optionally references WebAuthor via id_web_authors)
├── ArtefactsEvent (1:N) - Historical events (construction, destruction, etc.)
├── ArtefactsSynonym (1:N) - Names in different languages
└── ArtefactsCategory (1:N) - Style/temporal classifications
    └── Category
        ├── CategoriesSynonym (1:N) - Category names in different languages
        └── Subject (N:1) - Thematic grouping (e.g., Architecture)

WebAuthor (Strictly defined authors with Wikipedia pages)
├── WebAuthorsSynonym (1:N) - Author names in different languages
└── ArtefactsAuthor (1:N) - Referenced by artefacts when author is well-defined
```

### Key Classes

- **Artefact**: Main entity representing an architectural object with name, Wikipedia reference, and language
- **ArtefactsLocation**: GPS coordinates (partitioned by longitude for performance)
- **ArtefactsAuthor**: Flexible author attribution with optional reference to WebAuthor via `id_web_authors` field
- **WebAuthor**: Well-defined authors with Wikipedia pages and multilingual synonyms
- **Category**: Classification system (architectural styles, temporal periods, etc.)
- **Subject**: High-level thematic grouping (e.g., Architecture, Sculpture)

## Database

The database schema is defined by this model. SQL dumps with 200,000+ architectural objects are available:

**[ArtefactsLocation-data](https://github.com/MyropolskyiHennadii/ArtefactsLocation-data)** - Download the complete database


## Technical Details

### Requirements

- Java 21
- Hibernate 7.1.1+
- Jackson 2.20+
- Lombok 1.18.42+

### Database Design

- All entities are mapped to database tables via JPA annotations
- **Important**: `ArtefactsLocation` and `Artefact` are NOT connected by foreign key for performance
- `ArtefactsCategory` is marked as `@Transient` in Artefact to optimize location-based queries
- `artefacts_locations` table is partitioned by `int_longitude` (floor of longitude)

### JSON Serialization

The model includes sophisticated JSON handling:
- Conditional field inclusion based on `includeWikiOutside` flag
- Custom `@JsonGetter`/`@JsonSetter` for backward compatibility
- Circular reference prevention via `@JsonManagedReference`/`@JsonBackReference`
- Compact JSON output (e.g., categories as integer arrays)

### Utility Methods

**ModelStaticMethods** provides:
- JSON file parsing to artefact lists
- Wikipedia API integration for extracting article summaries
- Reverse geocoding support


Add to your `pom.xml`:

```xml
<dependency>
    <groupId>myropolskyi.locations</groupId>
    <artifactId>ArtefactsLocation-model</artifactId>
    <version>2.2.2</version>
</dependency>
```

## Version History

See [CHANGELOG.md](CHANGELOG.md) for detailed version history.

## License

This project is open source. Data is sourced from free/open sources (primarily Wikipedia).

## Related Projects

This model is used by the **LookAroundArchitecture** mobile application for Android.

## Contact

For questions or suggestions, contact: miropolskij@gmail.com
