package com.hsfl.springbreak.frontend.utils

import csstype.Length
import csstype.px
import mui.material.TypographyProps

inline var TypographyProps.color: String
    get() = TODO("Prop is write-only!")
    set(value) {
        asDynamic().color = value
    }

fun Int.inMuiPx(): Length = this.px