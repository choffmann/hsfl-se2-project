# ViewModel

Das ViewModel kontrolliert die Ui. Das ViewModel feuert unter anderem den Befehl an das Repository (`→ Client`) ab, horcht gleichzeitig auf die Antwort und kann entsprechend darauf reagieren. Die Ui kann durch Events mit dem ViewModel kommunizieren. Bei einer Anfrage zum Backend und zum verarbieten der Antwort kommt hier die bereits erwähnte Methode `handleDataResponse`, welche in der `sealed class DataResponse<T>` definiert ist. 

## Kommunikation von der Ui zum ViewModel

Die Ui kann mittels Events, welche vom User ausgeführt werden, mit der Ui kommunizieren. Ein Beispiel beim Login. Hier haben wir folgende Interaktionsmöglichkeiten vom Benutzer:

- User öffnet/schließt den Login Dialog
- User klickt auf den Login Button
- User klickt den Abbrechen Button
- User klickt den Button, welches das Register Dialog öffnet

Somit entsteht beispielsweise folgenden Status Klasse für das `LoginViewModel`:

```kotlin
sealed class LoginEvent() {
  object OpenDialog: LoginEvent()
  object CloseDialog: LoginEvent()
  object OpenRegisterDialog: LoginEvent()
  data class OnLoginButton(val email: String, val password: String): LoginEvent()
}
```

Über die `when`Funktion von Kotlin kann überprüft werden, welcher Status an eine Funktion (hier `onEvent(event: LoginEvent`) geschickt 

```kotlin
fun onEvent(event: LoginEvent) {
  when (event) {
    is LoginEvent.OpenDialog -> // open dialog
    is LoginEvent.CloseDialog -> // close dialog
    is LoginEvent.OpenRegisterDialog -> // close login dialog and open register
    is LoginEvent.OnLoginButton -> //  login z.B. login(event.email, event.password)
  }
}
```

## Kommunikation vom ViewModel zur Ui

Das ViewModel kommuniziert zu der Ui über `StateFlows`. Ähnlich wie das Repository, schmeißt das ViewModel die Informationen in einen Flow, welche von der Ui als `collector` gesammelt und angezeigt werden.  

Dabei werden insgesamt zwei `StateFlows` für einen Status definiert. Ein `MutableStateFlow` welcher auf `private` gesetzt ist. Dieser kann nur innerhalb des ViewModels verändert werden. Daneben gibt es einen öffentlichen `StateFlow`, welcher nur Informationen raus gibt.

```kotlin
private val _loginDialogOpen = MutableStateFlow(false)
val loginDialogOpen: StateFlow<Boolean> = _loginDialogOpen

private val _registerDialogOpen = MutableStateFlow(false)
val registerDialogOpen: StateFlow<Boolean> = _registerDialogOpen
```

Die Ui kann mittels dem React Hook `useEffect` auf den Flow lauschen und anzeigen. Die Methode `fun <T> StateFlow<T>.collectAsState(): T` erweitert den StateFlow, so dass wir hier über React darauf zugreifen können. Diese Methode befindet sich unter `com.hsfl.springbreak.frontend.utils.Utils.kt`. Die Methode `collectAsState()` kann wie folgt aufgerufen werden

```kotlin
val openLoginDialog = viewModel.loginDialogOpen.collectAsState()
val openRegisterDialog = viewModel.registerDialogOpen.collectAsState()
```

Ändert sich nun im ViewModel der State im einen von diesen Flows, wird dieser an die Ui mit übergeben.

## Kommunikation mit dem Repository/Backend

Wie bereits angesprochen, schickt das ViewModel Daten bzw. ruft bei bedarf das Repository auf und lauscht auf die Antwort vom Backend. Durch die Methode `handleDataRepsonse<T>` kann dynamisch auf die Antworten vom Backend reagiert werden. Von dem Backend können folgende Responses zurückkommen

- Loading
- Success
- Error
- Unauthorized

Die Methode `handleDataRepsonse<T>` besteht somit aus vier `callbacks`, die je nach der Antwort vom Backend aufgerufen werden. Das praktische hierbei ist, dadurch das bei Kotlin die Parameter vordefiniert werden können, können wir hier flexibel unser verhalten beschreiben. Zum Beispiel wird bei dem Response Loading ein Ladebalken in der Ui angezeigt. Wollen wir dieses Verhalten nicht für eine bestimmte Anfrage haben, können wir diesen Parameter einfach überschreiben. Die Funktion ist wie folgt aufgebaut (vordefinierte Parameter Definitionen könnten abweichen):

```kotlin
suspend fun <out: T> handleDataResponse(
  onSuccess: suspend (T) -> Unit,
  onError: suspend (String) -> Unit = {
      UiEventState.onEvent(UiEvent.ShowError(it))
  },
  onLoading: suspend () -> Unit = {
      UiEventState.onEvent(UiEvent.ShowLoading)
  },
  onUnauthorized: suspend (String) -> Unit
) = when (this) {
  is Error -> onError(this.error)
  is Loading -> onLoading()
  is Success -> onSuccess(this.data)
  is Unauthorized -> onUnauthorized(this.error)
}
```

Im `ViewModel` können die Parameter wie folgt aufgerufen, bzw. überschrieben werden:

```kotlin
private fun onLogin(email: String, password: String) = scope.launch {
  userRepository.login(User.Login(email, password)).collect { response ->
	  response.handleDataResponse<User>(
      onSuccess = { // Do this on success },
      onLoading = { // Overwrite this behavior on loading }
	  )
  }
}
```

## Siehe auch

[Repository](Repository%204fe68c294fa14e3bbced72750259e130.md)