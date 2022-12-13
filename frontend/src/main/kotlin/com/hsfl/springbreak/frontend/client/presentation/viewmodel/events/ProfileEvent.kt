package com.hsfl.springbreak.frontend.client.presentation.viewmodel.events

import web.file.File

sealed interface ProfileEvent {
    object OnEdit : ProfileEvent
    object OnSave : ProfileEvent
    object OnAbort : ProfileEvent
    data class FirstNameChanged(val value: String) : ProfileEvent
    data class LastNameChanged(val value: String) : ProfileEvent
    data class EmailChanged(val value: String) : ProfileEvent
    data class PasswordChanged(val value: String) : ProfileEvent
    data class ConfirmedPasswordChanged(val value: String) : ProfileEvent
    data class ProfileImageChanged(val file: File) : ProfileEvent
}