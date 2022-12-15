package com.hsfl.springbreak.frontend.components.recipe

import com.hsfl.springbreak.frontend.client.presentation.viewmodel.events.LifecycleEvent
import com.hsfl.springbreak.frontend.client.presentation.viewmodel.events.RecipeCardEvent
import com.hsfl.springbreak.frontend.client.presentation.viewmodel.recipe.RecipeListViewModel
import com.hsfl.springbreak.frontend.client.presentation.viewmodel.recipe.RecipeListType
import com.hsfl.springbreak.frontend.components.notfound.EmptyCategory
import com.hsfl.springbreak.frontend.components.notfound.EmptyFavorite
import com.hsfl.springbreak.frontend.components.notfound.EmptyOwnRecipe
import com.hsfl.springbreak.frontend.di.di
import com.hsfl.springbreak.frontend.utils.collectAsState
import csstype.Display
import csstype.JustifyContent
import csstype.number
import csstype.px
import mui.lab.Masonry
import mui.material.Box
import mui.system.responsive
import mui.system.sx
import org.kodein.di.instance
import react.FC
import react.Props
import react.useEffect

external interface RecipeListProps : Props {
    var listType: RecipeListType
}

val RecipeList = FC<RecipeListProps> { props ->
    val viewModel: RecipeListViewModel by di.instance()
    val recipeList = viewModel.recipeList.collectAsState()
    viewModel.randomState.collectAsState()

    useEffect(Unit) {
        viewModel.onEvent(
            RecipeCardEvent.OnLaunch(
                type = props.listType
            )
        )
        cleanup { viewModel.onEvent(LifecycleEvent.OnUnMount) }
    }

    if (recipeList.isEmpty()) {
        when (props.listType) {
            is RecipeListType.CategoryList -> EmptyCategory()
            RecipeListType.FavoriteList -> EmptyFavorite()
            RecipeListType.MyRecipeList -> EmptyOwnRecipe()
            RecipeListType.HomeList.AllTab -> {}
            RecipeListType.HomeList.CheapTab -> {}
            RecipeListType.HomeList.FastTab -> {}
            RecipeListType.HomeList.PopularTab -> {}
        }
    } else {
        Box {
            sx {
                flexGrow = number(1.0)
                margin = 8.px
                display = Display.flex
                justifyContent = JustifyContent.center
            }

            Masonry {
                sx {
                    minWidth = 1000.px
                    maxWidth = 1400.px
                }
                columns = responsive(3)
                spacing = responsive(2)
                recipeList.map {
                    RecipeCard {
                        id = it.recipe.id
                        title = it.recipe.title
                        createdDate = "Bla"
                        creator = "${it.recipe.creator.firstName} ${it.recipe.creator.lastName}"
                        imageSrc = it.recipe.image ?: ""
                        shortDescription = it.recipe.shortDescription
                        cost = it.recipe.price.toInt().toString()
                        duration = it.recipe.duration.toInt().toString()
                        difficulty = it.recipe.difficulty.name
                        creatorImg = it.recipe.creator.image
                        onFavoriteClick = {
                            viewModel.onEvent(RecipeCardEvent.OnFavorite(it))
                        }
                        isMyRecipe = it.isMyRecipe
                        isFavorite = it.isFavorite
                    }
                }
            }
        }
    }
}