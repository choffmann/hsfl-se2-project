package com.hsfl.springbreak.frontend.client.presentation.state

import com.hsfl.springbreak.frontend.client.data.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class UserState {
    private val _userState = MutableStateFlow(
        User(
            id = 1,
            firstName = "Jeeve",
            lastName = "Philodendron",
            email = "jeeve.philodendron@platns.com",
            password = "geheim",
            image = "https://plantaddiction.de/media/image/37/be/5a/philodendron-congo-apple_400x400.jpg"
        )
    )
    val userState: StateFlow<User> = _userState

    fun onEvent(event: UserStateEvent) = when (event) {
        is UserStateEvent.UpdateUser -> _userState.value = userState.value.copy(
            firstName = event.value.firstName ?: userState.value.firstName,
            lastName = event.value.lastName ?: userState.value.lastName,
            email = event.value.email ?: userState.value.email,
            password = event.value.password ?: userState.value.password
        )
    }
}

sealed class UserStateEvent {
    data class UpdateUser(val value: User.State) : UserStateEvent()
}