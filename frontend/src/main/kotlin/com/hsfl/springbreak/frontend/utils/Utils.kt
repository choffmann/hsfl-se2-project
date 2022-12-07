package com.hsfl.springbreak.frontend.utils

import csstype.Length
import csstype.px
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import mui.material.IconButtonProps
import mui.material.StepButtonProps
import mui.material.TypographyProps
import react.useEffect
import react.useState

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

inline var StepButtonProps.color: String?
    get() = TODO("Prop is write-only!")
    set(value) {
        asDynamic().color = value
    }

inline var StepButtonProps.onClick: () -> Unit
    get() = TODO("Prop is write-only!")
    set(value) {
        value()
    }

fun Int.toMuiPx(): Length = (this * 8).px

// Subscribe to StateFlow as a state hook
fun <T> StateFlow<T>.collectAsState(): T {
    val (state, setStat) = useState(this.value)
    this.let { flow ->
        useEffect(Unit) {
            MainScope().launch {
                flow.collectLatest { setStat(it) }
            }
        }
    }
    return state
}
