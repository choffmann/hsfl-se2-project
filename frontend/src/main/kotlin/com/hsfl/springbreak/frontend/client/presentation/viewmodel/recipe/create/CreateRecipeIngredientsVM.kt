package com.hsfl.springbreak.frontend.client.presentation.viewmodel.recipe.create

import com.hsfl.springbreak.frontend.client.presentation.viewmodel.events.CreateRecipeIngredientsEvent
import com.hsfl.springbreak.frontend.client.presentation.viewmodel.events.IngredientsDialogEvent

class CreateRecipeIngredientsVM(
    private val ingredientsDialogVM: IngredientsDialogVM,
) {

    fun onEvent(event: CreateRecipeIngredientsEvent) {
        when (event) {
            is CreateRecipeIngredientsEvent.OnAddIngredient -> ingredientsDialogVM.onEvent(IngredientsDialogEvent.OnOpen)
        }
    }
}

data class RecipeIngredient(var name: String, var amount: Int, var unit: String)
