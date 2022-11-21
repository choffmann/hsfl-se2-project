package com.hsfl.springbreak.frontend

import browser.document
import com.hsfl.springbreak.frontend.components.Header
import com.hsfl.springbreak.frontend.components.NavDrawer
import mui.material.CssBaseline
import mui.material.LinearProgress
import mui.material.Typography
import react.FC
import react.Props
import react.create
import react.dom.client.createRoot

fun main() {
    createRoot(document.createElement("div").also { document.body.appendChild(it) })
        .render(App.create())
}

private val App = FC<Props> {
    CssBaseline()
    Header {
        onLogoClicked = {
            println("click")
        }
        Typography {+"Hello World"}
    }

}