package com.hsfl.springbreak.frontend.components.recipe

import com.hsfl.springbreak.frontend.client.presentation.viewmodel.ProfileRecipeListTableVM
import com.hsfl.springbreak.frontend.client.presentation.viewmodel.events.LifecycleEvent
import com.hsfl.springbreak.frontend.di.di
import com.hsfl.springbreak.frontend.utils.collectAsState
import com.hsfl.springbreak.frontend.utils.color
import mui.material.*
import org.kodein.di.instance
import react.FC
import react.Props
import react.useEffect

val ShowMyRecipes = FC<Props> {
    val viewModel: ProfileRecipeListTableVM by di.instance()
    val recipeList = viewModel.recipeList.collectAsState()

    useEffect(Unit) {
        viewModel.onEvent(LifecycleEvent.OnMount)
        cleanup { viewModel.onEvent(LifecycleEvent.OnUnMount) }
    }

    TableContainer {
        Table {
            TableHead {
                TableCell { +"Rezeptname" }
                TableCell { +"Kategorie" }
                TableCell { +"Schwierigkeit" }
                TableCell { +"Aufrufe" }
            }

            TableBody {
                if (recipeList.isEmpty()) TableCell {
                    colSpan = 4
                    Typography {
                        color = "text.secondary"
                        +"Es sind noch keine Rezepte vorhanden"
                    }
                } else recipeList.map {
                    TableCell { +it.title }
                    TableCell { +it.category.name }
                    TableCell { +it.difficulty.name }
                    TableCell { +it.views.toString() }
                }
            }
        }
    }
}