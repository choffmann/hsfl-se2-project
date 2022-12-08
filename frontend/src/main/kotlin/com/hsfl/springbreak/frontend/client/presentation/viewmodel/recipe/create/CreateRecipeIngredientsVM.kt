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

data class RecipeIngredient(val name: String, val amount: Int, val unit: String)