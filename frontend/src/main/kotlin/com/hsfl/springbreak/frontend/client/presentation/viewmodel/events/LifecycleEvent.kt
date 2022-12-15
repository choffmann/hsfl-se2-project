package com.hsfl.springbreak.frontend.client.presentation.viewmodel.events


sealed class LifecycleEvent : CategoryListEvent, AuthDialogEvent, CreateRecipeEvent, CreateRecipeDataEvent,
    CreateRecipeDescriptionEvent, StepperEvent, CreateRecipeImageEvent, IngredientEditDialogEvent,
    IngredientsDialogEvent, IngredientsTableEvent, RootEvent, RecipeDetailEvent, HomeViewEvent, ProfileEvent,
    RecipeCardEvent, RecipeDetailEditEvent {
    object OnMount : LifecycleEvent()
    object OnUnMount : LifecycleEvent()
}