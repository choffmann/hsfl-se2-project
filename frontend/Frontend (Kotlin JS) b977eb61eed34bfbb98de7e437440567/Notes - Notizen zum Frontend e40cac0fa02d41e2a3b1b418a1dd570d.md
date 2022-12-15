# Notes - Notizen zum Frontend

- ❗**React Router**
    
    Damit der Router in unserem Projekt funktioniert, müssen zuvor folgende Anpassungen in der Webpack Konfiguration getätigt werden. Kotlin liest bei jedem Build die Daten aus der Datei `webpack.config.d` heraus und fügt sie der entstehenden js-Datei (webpack config) hinzu. Damit der Router nun funktioniert. muss der `publicPath` und der `devServer` angepasst werden:
    
    ```kotlin
    config.output.publicPath = '/'
    config.devServer.historyApiFallback = true
    ```
    
    <aside>
    💡 Die Angabe `config.devServer.historyApiFallback` kann nur bei der Entwicklung verwendet werden. Wenn das Frontend `gebuildet` wird und die Angabe noch vorhanden ist, kommt eine Fehlermeldung
    
    </aside>
    
- ⚠️ **Parsen vom `String` zu `Int` oder `Long`**
    
    Das Problem ist, das diese Fehlermeldung erscheint, wenn ich versuche einen `String` in eine Nummer `Int` zu konvertieren.
    
    **Aufruf**:
    
    ```jsx
    event.value.toInt() // event.value is a String
    ```
    
    **Fehlermeldung**
    
    ```jsx
    Uncaught TypeError: $receiver.charCodeAt is not a function
        at toIntOrNull_0 (kotlin.js:57825:33)
        at toIntOrNull (StringNumberConversions.kt?f66f:52:1)
        at toInt (kotlin.js:46296:22)
        at CreateRecipeDataVM.onEvent_hahhs9$ (CreateRecipeDataVM.kt?81e3:79:94)
        at eval (RecipeCreateData.kt?156d:132:31)
        at handleChange (InputBase.js?5acd:361:1)
        at eval (SelectInput.js?6cea:280:1)
        at HTMLUnknownElement.callCallback (react-dom.development.js?f6e0:4164:1)
        at Object.invokeGuardedCallbackDev (react-dom.development.js?f6e0:4213:1)
        at invokeGuardedCallback (react-dom.development.js?f6e0:4277:1)
    ```
    
    **Workaround**
    
    Wird der String erst nochmal mit der Funktion `.toString()` aufgerufen, kann der Wert verarbeitet werden
    
    ```jsx
    event.value..toString().toInt()
    ```