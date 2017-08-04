# URL Shortener

This is a simple URL shortening service written in Java with Spring Boot.

## Prerequisites
- Java 8 or later
- Gradle 4.0.1 (to build)
- Set the environment variable `H2_DB_LOCATION` to specify the location of the
H2 database file. If the file doesn't exist, it will be created.

### Windows
`set H2_DB_LOCATION=~/urlshortener.db`

### GNU/Linux
`H2_DB_LOCATION=~/urlshortener.db`

## Compile
[![Build Status](https://travis-ci.org/pybeaudouin/urlshortener.svg?branch=master)](https://travis-ci.org/pybeaudouin/urlshortener)

`gradle build`

This will create an executable jar that runs a web server.
The jar will be created in the build/lib directory.

## Run


`java -jar build/libs/urlshortener-x.y.z.jar`

## Usage
### Shorten a URL
Send a POST request to `/1` with the URL to shorten in the `url` field:

`curl --data "url=https://github.com" http://localhost:8080/1/`

### Expand a short URL
Just hit the URL with your browser.


## Code Coverage
`gradle test jacocoFullReport`

Then open `/build/reports/jacoco/jacocoFullReport/html/index.html`