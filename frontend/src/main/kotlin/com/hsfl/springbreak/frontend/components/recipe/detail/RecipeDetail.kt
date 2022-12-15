package com.hsfl.springbreak.frontend.components.recipe.detail

import com.hsfl.springbreak.frontend.client.presentation.viewmodel.events.LifecycleEvent
import com.hsfl.springbreak.frontend.client.presentation.viewmodel.events.RecipeDetailEvent
import com.hsfl.springbreak.frontend.client.presentation.viewmodel.recipe.detail.RecipeDetailViewModel
import com.hsfl.springbreak.frontend.di.di
import com.hsfl.springbreak.frontend.utils.collectAsState
import org.kodein.di.instance
import react.*

external interface RecipeDetailProps : Props {
    var recipeId: Int
}

val RecipeDetail = FC<RecipeDetailProps> { props ->
    val viewModel: RecipeDetailViewModel by di.instance()
    val recipeState = viewModel.recipe.collectAsState()
    val myRecipeState = viewModel.isMyRecipe.collectAsState()
    val editModeState = viewModel.editMode.collectAsState()
    val isFavoriteState = viewModel.isFavorite.collectAsState()
    val openDeleteDialog = viewModel.openDeleteDialog.collectAsState()

    useEffect(Unit) {
        viewModel.onEvent(RecipeDetailEvent.RecipeId(props.recipeId))
        cleanup { viewModel.onEvent(LifecycleEvent.OnUnMount) }
    }

    if (editModeState) {
        RecipeDetailEdit {
            recipe = recipeState
            onSave = {}
            onCancel = {
                viewModel.onEvent(RecipeDetailEvent.CancelEdit)
            }
        }
    } else {
        RecipeDetailView {
            recipe = recipeState
            isMyRecipe = myRecipeState
            isFavorite = isFavoriteState
            onFavoriteClick = {
                viewModel.onEvent(RecipeDetailEvent.OnFavorite)
            }
            onScoreChange = { viewModel.onEvent(RecipeDetailEvent.OnScoreChanged(it)) }
            onLikeClick = {}
            onDislikeClick = {}
            onEditClick = {
                viewModel.onEvent(RecipeDetailEvent.OnEdit)
            }
            onDeleteClick = {
                viewModel.onEvent(RecipeDetailEvent.OnDelete)
            }
        }
    }

    DeleteRecipeDialog {
        open = openDeleteDialog
        recipeTitle = recipeState.title
        onConfirm = { viewModel.onEvent(RecipeDetailEvent.OnDeleteDialogConfirm) }
        onAbort = { viewModel.onEvent(RecipeDetailEvent.OnDeleteDialogAbort) }
    }
}
