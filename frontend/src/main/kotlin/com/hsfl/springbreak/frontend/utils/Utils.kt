package com.hsfl.springbreak.frontend.utils

import csstype.Length
import csstype.px
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import mui.material.*
import react.useEffect
import react.useState
import kotlin.js.Date

// Add color property to TypographyProps
inline var TypographyProps.color: String
    get() = TODO("Prop is write-only!")
    set(value) {
        asDynamic().color = value
    }

// Add component property to IconButton
inline var IconButtonProps.component: react.ElementType<*>?
    get() = TODO("Prop is write-only!")
    set(value) {
        asDynamic().component = value
    }

inline var ButtonProps.component: react.ElementType<*>?
    get() = TODO("Prop is write-only!")
    set(value) {
        asDynamic().component = value
    }

inline var FabProps.component: react.ElementType<*>?
    get() = TODO("Prop is write-only!")
    set(value) {
        asDynamic().component = value
    }

inline var StepButtonProps.color: String?
    get() = TODO("Prop is write-only!")
    set(value) {
        asDynamic().color = value
    }

fun Int.toMuiPx(): Length = (this * 8).px

// Subscribe to StateFlow as a state hook
fun <T> StateFlow<T>.collectAsState(): T {
    val (state, setStat) = useState(this.value)
    this.let { flow ->
        useEffect(Unit) {
            val job = MainScope().launch {
                flow.collectLatest { setStat(it) }
            }
            cleanup { job.cancel() }
        }
    }
    return state
}

fun Date.convertToDate(): String {
    val month = when (this.getMonth()) {
        0 -> "Januar"
        1 -> "Februar"
        2 -> "März"
        3 -> "April"
        4 -> "Mai"
        5 -> "Juni"
        6 -> "Juli"
        7 -> "August"
        8 -> "September"
        9 -> "Oktober"
        10 -> "November"
        11 -> "Dezember"
        else -> {
            ""
        }
    }
    return "$month ${this.getDate()}, ${this.getFullYear()}"
}