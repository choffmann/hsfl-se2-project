package com.hsfl.springbreak.frontend

import browser.document
import com.hsfl.springbreak.frontend.components.Header
import mui.material.CssBaseline
import mui.material.Typography
import react.FC
import react.Props
import react.create
import react.dom.client.createRoot
import react.useState

fun main() {
    createRoot(document.createElement("div").also { document.body.appendChild(it) })
        .render(App.create())
}

private val App = FC<Props> {
    val authorized by useState(true)

    CssBaseline()
    Header {
        isAuthorized = authorized
        onLogoClicked = {
            println("click")
        }
        Typography {+"Hello World"}
    }

}