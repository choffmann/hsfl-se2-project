package com.hsfl.springbreak.frontend.client.presentation.viewmodel.events

import web.file.File

sealed interface AuthDialogEvent {
    object OpenLoginDialog : AuthDialogEvent
    object OpenRegisterDialog : AuthDialogEvent
    data class OnCloseLoginDialog(val event: dynamic, val reason: String) : AuthDialogEvent
    data class OnCloseRegisterDialog(val event: dynamic, val reason: String) : AuthDialogEvent
    data class OnLogin(val email: String, val password: String) : AuthDialogEvent
    data class OnRegister(
        val firstName: String,
        val lastName: String,
        val email: String,
        val password: String,
        val confirmedPassword: String,
        val profileImage: File?
    ) : AuthDialogEvent
}