# XML parser

This XML parser will download ZIP file from https://www.smartform.cz/download/kopidlno.xml.zip,
unpack it into "unzipped" folder, than load relevant data and save them in PostgresDB running locally in Docker

## Requiremnets

[Java 21](https://www.oracle.com/java/technologies/downloads/)

[Docker](https://www.docker.com/) running locally for PostgresDB container

## Running XML parser

Call: http://localhost:8085/test to load data.
You can run it directly from [OpenAPI](https://www.openapis.org/) docs: http://localhost:8085/swagger-ui/index.html
