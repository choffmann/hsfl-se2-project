# Repository

Das Repository ist die Schnittstelle zwischen dem `ViewModel` und dem `Client`.Das Repository ruft die Methoden vom Client auf und verarbeitet dabei die Daten, welche vom Client zurück kommen und gibt diese an das `ViewModel` weiter. Das Repository gibt dabei ein `Flow` weiter.

## Flow

Flow sind kurz gesagt `Observer` von Kotlin. In einen Flow können Daten **rein geworfen** werden (`emit`), welche von einem **collector** abgerufen werden.

## Repository Helper

Die Funktion `repositoryHelper` erweitert den `FlowCollector` und schmeißt entsprechend Objekte rein, auf welche die Ui reagiert (Laden, Erfolgreiche anfrage der Daten oder Fehler). Diese Objekte, welche rein geschmissen werden, sind vom selbst definierten Typ `DataResponse`. `DataResponse` ist eine `sealed class` welche 4 Status hat und eine Funktion, welche im `ViewModel`aufgerufen wird.

```kotlin
suspend fun <T> FlowCollector<DataResponse<T>>.repositoryHelper(callback: suspend () -> APIResponse<T>) {
    try {
        emit(DataResponse.Loading()) <1>
        callback().let { response -> <2>
            response.data?.let {
                emit(DataResponse.Success(it))
            } ?: emit(DataResponse.Error(response.error!!)) <3>
        }
    } catch (e: Error) { <4>
        e.printStackTrace()
        println(e.cause)
        emit(DataResponse.Error(e.message ?: "An unexpected error occurred"))
    }
}
```

### Ablauf

1. Zu erst wird der Status `Loading` in den `flow` geschmissen. Der Client startet die Anfrage. Die Ui zeigt den Lade-Status an. 
2. Wenn der Client erfolgreich die Daten vom Backend geholt hat, schmeißt er diese in den Flow und die Ui kann die entsprechenden Daten anzeigen.
3. Schickt das Backend eine Fehlermeldung (z.B. id nicht gefunden), wird ein Fehler in den Flow geschmissen. Die Ui zeigt darauf hin eine Fehlermeldung
4. Sollte es zu einen anderen unerwarteten Fehler kommen (z.B. keine Internetverbindung oder Co.) wird auch dieser Fehler in den Flow an die Ui geschickt

## Siehe auch

[Client](Client%209b767bcf3af64c1eb2810db7fe26c019.md)

[ViewModel](ViewModel%20b9bb390a6f7f4bf69b838e92e485a5c2.md)

[](https://kotlinlang.org/docs/flow.html#flow-cancellation-basics)

[](https://kotlinlang.org/docs/sealed-classes.html#sealed-classes-and-when-expression)