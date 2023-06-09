# Studentenküche

In Rahmen der Vorlesung **Software-Engineering 2** wurde eine Webapplikation entwickelt, welche die Verwaltung von
Rezepten und Zutaten ermöglicht. Die App wurde mit dem Spring Framework entwickelt und verwendet eine Postgres
Datenbank. Als Frontend wurde React und MUI in Kotlin (Kotlin-Wrapper) verwendet.

## Datenbank

Die Datenbank Konfiguration befindet sich in der Datei `backend/src/main/resources/application.properties`. Ein Dump der
Datenbank befindet sich in der Datei `dump/db.dump`.

## Applikation starten

Wenn die Datenbank gestartet ist, kann die App mit folgenden Shell Script gebaut und ausgeführt werden. Dabei werden die Daten für das Frontend in das Backend kopiert.

```bash
./gradlew frontend:build --stacktrace
rm -rf backend/src/main/resources/static/
cp -r frontend/build/distributions backend/src/main/resources/static
./gradlew backend:build --stacktrace
java -jar backend/build/libs/backend-0.0.1-SNAPSHOT.jar
```

## Frontend 
Eine genauere Dokumentation zum Frontend befindet sich in der Datei `frontend/README.md` ([README.md](https://github.com/choffmann/hsfl-se2-project/blob/master/frontend/README.md)).
