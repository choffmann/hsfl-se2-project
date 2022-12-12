package com.hsfl.springbreak.frontend.client.presentation.viewmodel

import com.hsfl.springbreak.frontend.client.data.model.User
import com.hsfl.springbreak.frontend.client.presentation.state.AuthEvent
import com.hsfl.springbreak.frontend.client.presentation.state.AuthState
import com.hsfl.springbreak.frontend.client.presentation.state.UserState
import com.hsfl.springbreak.frontend.client.presentation.state.UserStateEvent
import com.hsfl.springbreak.frontend.client.presentation.viewmodel.events.LifecycleEvent
import com.hsfl.springbreak.frontend.client.presentation.viewmodel.events.RootEvent
import com.hsfl.springbreak.frontend.di.di
import org.kodein.di.instance
import web.storage.localStorage

class RootViewModel {

    fun onEvent(event: RootEvent) {
        when (event) {
            LifecycleEvent.OnMount -> checkIsLoggedIn()
            LifecycleEvent.OnUnMount -> TODO()
        }
    }

    private fun checkIsLoggedIn() {
        val authState: AuthState by di.instance()
        if (localStorage.getItem("isLoggedIn").toBoolean()) {
            authState.onEvent(AuthEvent.IsAuthorized)
            readUserState()
        } else {
            authState.onEvent(AuthEvent.IsUnauthorized)
        }
    }

    private fun readUserState() {
        val userState: UserState by di.instance()
        userState.onEvent(UserStateEvent.UpdateUser(
            User.State(
                firstName = localStorage.getItem("userFirstName"),
                lastName = localStorage.getItem("userLastName"),
                email = localStorage.getItem("userEmail"),
                image = localStorage.getItem("userImage"),
                password = null
            )
        ))
    }
}