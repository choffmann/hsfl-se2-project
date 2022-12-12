package com.hsfl.springbreak.frontend.client.presentation.viewmodel.events

sealed interface StepperEvent {
    object OnNextStep : StepperEvent
    object OnBackStep : StepperEvent
}