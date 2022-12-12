package com.hsfl.springbreak.frontend.client.presentation.viewmodel.events

import web.file.File

sealed interface CreateRecipeImageEvent {
    data class SelectedFile(val file: File): CreateRecipeImageEvent
}