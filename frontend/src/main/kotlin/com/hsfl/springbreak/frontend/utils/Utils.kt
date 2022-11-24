package com.hsfl.springbreak.frontend.utils

import csstype.Length
import csstype.px
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import mui.material.TypographyProps
import react.useEffect
import react.useState

inline var TypographyProps.color: String
    get() = TODO("Prop is write-only!")
    set(value) {
        asDynamic().color = value
    }

fun Int.inMuiPx(): Length = this.px

fun <T> StateFlow<T>.collectAsState(): T {
    val (state, setStat) = useState(this.value)
    this.let { flow ->
        useEffect(Unit) {
            MainScope().launch {
                flow.collect { setStat(it) }
            }
        }
    }
    return state
}
