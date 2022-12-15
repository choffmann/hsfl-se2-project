# Client

Die Client Implementierung erfolgt über das Kotlin Framework `Ktor`. `Ktor` (bzw. `Ktor-Client`) ist ein Framework, um HTTP Anfragen zu stellen. 

## Projektstruktur

Der Client befindet sich unter `com.hsfl.springbreak.frontend.client.data.Client.kt`. In der Kotlin Datei ist die Klasse `Client` und das Interface `ApiClient` zu finden. Das Interface definiert die Methoden, welche der Client an die REST Schnittstelle vom Backend sendet. Die einzelnen Methoden geben die Response von der Anfrage weiter. Der Client wird immer nur vom Repository aufgerufen.

## Beispiel

Das Interface definiert die Methode, welche an das Backend geschickt wird

```kotlin
interface ApiClient {
  suspend fun login(user: User.Login): User.Response
	// ...
}
```

Die Klasse Client implementiert das Interface und erweitert die Methode wie folgt:

```kotlin
override suspend fun login(user: User.Login): User.Response {
  return client.post(urlString = "http://localhost:8080/login") {
      contentType(ContentType.Application.Json)
      setBody(user)
  }.body()
}
```

### GET Request

```kotlin
client.get("http://localhost:8080/login").body()
```

### POST Request

```kotlin
client.post(urlString = "http://localhost:8080/login") {
	contentType(ContentType.Application.Json)
	setBody(user)
}.body()
```

### Datei hochladen

Leider gibt es aktuell noch Probleme mit `Ktor`, speziell das Objekt `web.file.File` zu serialisieren, weshalb Binare Dateien über `window.fetch` das Backend übergeben werden müssen. Das Objekt `web.file.File` ist z.B. ein Bild, welches vom Browser über das Dateisystem vom Betriebssystem ausgewählt wird (In unserem Fall ein Profilbild oder ein Bild vom Rezept).

```kotlin
val formData = FormData()
profileImage?.let { file ->
  formData.append("image", file.slice(), file.name)
}
val response = window.fetch(input = "http://localhost:8080/upload-profile-image", init = RequestInit(
  method = "POST",
  body = formData
))
  .await()
  .text()
  .await()
```

## Siehe auch

[Model](Model%20486d761dba204766bff520c119eec91a.md)

[Repository](Repository%204fe68c294fa14e3bbced72750259e130.md)

[Welcome | Ktor](https://ktor.io/docs/welcome.html)