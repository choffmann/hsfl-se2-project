package com.hsfl.springbreak.frontend.client.presentation.state

import com.hsfl.springbreak.frontend.client.data.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class UserState {
    private val _userState = MutableStateFlow(
        User(-1, "", "", "", "", "")
    )
    val userState: StateFlow<User> = _userState

    fun onEvent(event: UserStateEvent) {
        when (event) {
            is UserStateEvent.UpdateUser -> _userState.value = userState.value.copy(
                firstName = event.value.firstName ?: userState.value.firstName,
                lastName = event.value.lastName ?: userState.value.lastName,
                email = event.value.email ?: userState.value.email,
                password = event.value.password ?: userState.value.password
            )

            is UserStateEvent.SetUser -> _userState.value = event.user

        }
    }
}

sealed class UserStateEvent {
    data class UpdateUser(val value: User.State) : UserStateEvent()
    data class SetUser(val user: User) : UserStateEvent()
}