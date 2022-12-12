package com.hsfl.springbreak.frontend.components.routes

import com.hsfl.springbreak.frontend.client.presentation.state.AuthState
import com.hsfl.springbreak.frontend.di.di
import com.hsfl.springbreak.frontend.utils.collectAsState
import org.kodein.di.instance
import react.FC
import react.PropsWithChildren
import react.router.useNavigate
import react.useEffect

val ProtectedRoute = FC<PropsWithChildren> { props ->
    val authState: AuthState by di.instance()
    val isAuthorized = authState.authorized.collectAsState()
    val navigator = useNavigate()
    useEffect(Unit) {
        if (isAuthorized) {
            +props.children
        } else {
            navigator("/")
        }
    }
}