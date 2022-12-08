package com.hsfl.springbreak.frontend.client.presentation.viewmodel.recipe.create

class CreateRecipeIngredientsVM(
    private val ingredientsDialogVM: IngredientsDialogVM,
) {

    fun onEvent(event: CreateRecipeIngredientsEvent) {
        when (event) {
            is CreateRecipeIngredientsEvent.OnAddIngredient -> ingredientsDialogVM.onEvent(IngredientsDialogEvent.OnOpen)
        }
    }
}

sealed class CreateRecipeIngredientsEvent {
    object OnAddIngredient : CreateRecipeIngredientsEvent()
}

data class RecipeIngredient(var name: String, var amount: Int, var unit: String)