package com.hsfl.springbreak.frontend

import browser.document
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
    Typography { +"Hello World" }
}