# Frontend (Kotlin/JS)

## Links

---

[MUI: The React component library you always wanted](https://mui.com/)

## Pages:

---

[Model](Frontend%20(Kotlin%20JS)%20b977eb61eed34bfbb98de7e437440567/Model%20486d761dba204766bff520c119eec91a.md)

[Client](Frontend%20(Kotlin%20JS)%20b977eb61eed34bfbb98de7e437440567/Client%209b767bcf3af64c1eb2810db7fe26c019.md)

[Notes - Notizen zum Frontend](Frontend%20(Kotlin%20JS)%20b977eb61eed34bfbb98de7e437440567/Notes%20-%20Notizen%20zum%20Frontend%20e40cac0fa02d41e2a3b1b418a1dd570d.md)

[Repository](Frontend%20(Kotlin%20JS)%20b977eb61eed34bfbb98de7e437440567/Repository%204fe68c294fa14e3bbced72750259e130.md)

[ViewModel](Frontend%20(Kotlin%20JS)%20b977eb61eed34bfbb98de7e437440567/ViewModel%20b9bb390a6f7f4bf69b838e92e485a5c2.md)

## Ablauf

```mermaid
sequenceDiagram
	User->>Ui: Login
	Ui->>ViewModel: onEvent(LoginEvent.ClickLogin)
	ViewModel->>Repository: login(user: User.Login)
	Repository->>Repository: Flow: emit(DataReponse.Loading)
	Note over Repository: Flow wird mit state Loading gefüllt
	Repository->>ViewModel: DataReponse.Loading
	ViewModel->>Ui: Zeige Ladebalken
	Repository->>Client: login(user: User.Login)
	Client->>Backend: /api/login
	alt is Success
		Backend->>Client: User.Repsonse
		Client->>Repository: User.Response
		Repository->>Repository: Flow: emit(DataResponse.Success(data))
		Note over Repository: Flow wird mit state Success und Daten gefüllt
		Repository->>ViewModel : Flow mit daten
		ViewModel->>Ui: Zeige Daten
	else is Error
		Backend->>Client: Error
		Client->>Repository: Error
		Repository->>Repository: Flow: emit(DataResponse.Error(msg))
		Note over Repository: Flow wird mit state Error und Fehlermeldung gefüllt
		Repository->>ViewModel : Flow mit Fehlermeldung
		ViewModel->>Ui: Zeige Fehlermeldung
	end
```

### Siehe

[](https://ui.dev/react-router-cannot-get-url-refresh#webpack--development)

[](https://kotlinlang.org/docs/js-project-setup.html#webpack-configuration-file)