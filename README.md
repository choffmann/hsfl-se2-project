# Software Engineering 2 Project

## Datenbank

### Docker
Postgres Images holen
```
docker pull postgres:latest
```

Docker container starten
```
docker run --name se2-springbreak -p 5432:5432 -e POSTGRES_PASSWORD=super_mega_secret_password -e POSTGRES_USER=spring -e POSTGRES_DB=springbreak -d postgres
```

Wenn eingerichtet, kann der Container mit folgendem Befehl wieder gestartet werden
```
docker start se2-springbreak
```

**Tips**
Container entfernen
```
docker rm <conatiner_name>
```

IP Adresse vom Container
```
docker inspect -f '{{range.NetworkSettings.Networks}}{{.IPAddress}}{{end}}' container_name_or_id
```

**Einbindung Intellij**
![](docs/image/datasource_postgres_intellij.png)

### Nativ
Wird die Datenbank nicht über Docker bereitgestellt, müssen folgende Parameter in der Datenbank konfiguriert werden, um die Spring-Konfigurationsdatei nicht ändern zu müssen

```
POSTGRES_HOST=localhost
POSTGRES_PORT=5432
POSTGRES_DB=springbreak
POSTGRES_USER=spring
POSTGRES_PASSWORD=super_mega_secret_password
```

```postgresql
create database springbreak;
create user spring with password 'super_mega_secret_password';
grant all on schema public to spring;
alter user spring set search_path to public;
```

### Datenbank Dump
Ein Dump mit Demo Datensätzen steht unter dem Ordner `dump/db.dump` bereit

## Backend starten
Wenn die Datenbank gestartet ist, kann die App mit folgenden Shell Script gebaut und ausgeführt werden
```bash
./gradlew frontend:build --stacktrace
rm -rf backend/src/main/resources/static/
cp -r frontend/build/distributions backend/src/main/resources/static
./gradlew backend:build --stacktrace
java -jar backend/build/libs/backend-0.0.1-SNAPSHOT.jar
```
