package com.hsfl.springbreak.frontend.components.recipe.create

import com.hsfl.springbreak.frontend.client.data.model.Ingredient
import com.hsfl.springbreak.frontend.client.presentation.viewmodel.events.IngredientsTableEvent
import com.hsfl.springbreak.frontend.client.presentation.viewmodel.recipe.create.IngredientsTableRow
import com.hsfl.springbreak.frontend.client.presentation.viewmodel.recipe.create.IngredientsTableVM
import com.hsfl.springbreak.frontend.client.presentation.viewmodel.recipe.create.RecipeIngredient
import com.hsfl.springbreak.frontend.di.di
import com.hsfl.springbreak.frontend.utils.collectAsState
import com.hsfl.springbreak.frontend.utils.color
import csstype.Color
import csstype.Display
import csstype.JustifyContent
import csstype.px
import mui.icons.material.Add
import mui.icons.material.Delete
import mui.icons.material.Edit
import mui.material.*
import mui.material.styles.TypographyVariant
import mui.system.sx
import org.kodein.di.instance
import react.*
import react.dom.html.TdAlign

external interface IngredientsTableProps : Props {
    var editMode: Boolean
    var ingredientsList: List<Ingredient>
    var onNewIngredient: () -> Unit
}

val IngredientsTable = FC<IngredientsTableProps> { props ->
    val viewModel: IngredientsTableVM by di.instance()
    val selectedIngredients = viewModel.selectedIngredients.collectAsState()
    val ingredientsList = viewModel.ingredientsList.collectAsState()
    // TODO: this is called to update ui when list is updated...
    viewModel.randomState.collectAsState()

    useEffect(Unit) {
        if (props.editMode) {
            viewModel.onEvent(IngredientsTableEvent.OnNewData(props.ingredientsList.map {
                RecipeIngredient(name = it.name, unit = it.unit, amount = it.amount)
            }))
        }
    }

    TableContainer {
        Toolbar {
            sx {
                display = Display.flex
                justifyContent = JustifyContent.spaceBetween
                marginTop = 16.px
                if (selectedIngredients.isNotEmpty())
                    backgroundColor = Color("lightgrey")
            }
            if (selectedIngredients.isNotEmpty()) {
                Typography {
                    variant = TypographyVariant.subtitle1
                    color = "inherit"
                    +"${selectedIngredients.size} ausgewählt"
                }
                Box {
                    if (selectedIngredients.size == 1) Tooltip {
                        title = Typography.create { +"Bearbeiten" }
                        IconButton {
                            onClick = {
                                viewModel.onEvent(IngredientsTableEvent.OnEditRow)
                            }
                            Edit()
                        }
                    }
                    Tooltip {
                        title = Typography.create { +"Löschen" }
                        IconButton {
                            onClick = {
                                viewModel.onEvent(IngredientsTableEvent.OnDeleteRows)
                            }
                            Delete()
                        }
                    }
                }
            } else {
                Typography {
                    variant = TypographyVariant.h6
                    +"Zutatenliste"
                }
                if (props.editMode) {
                    Tooltip {
                        title = ReactNode("Zutat hinzufügen")
                        IconButton {
                            onClick = { props.onNewIngredient() }
                            Add()
                        }
                    }
                }
            }
        }
        Table {
            TableHead {
                TableRow {
                    TableCell {
                        padding = TableCellPadding.checkbox
                        /*Checkbox {
                            color = CheckboxColor.primary
                            disabled = ingredientsList.isEmpty()
                        }*/
                    }
                    TableCell {
                        padding = TableCellPadding.none
                        +"Zutat"
                    }
                    TableCell {
                        align = TdAlign.right
                        padding = TableCellPadding.normal
                        +"Menge"
                    }
                    TableCell {
                        align = TdAlign.right
                        padding = TableCellPadding.normal
                        +"Einheit"
                    }
                }
            }
            IngredientsTableContent {
                ingredients = ingredientsList
                onIngredientClick = {
                    viewModel.onEvent(IngredientsTableEvent.OnSelectRow(it))
                }
            }
        }
    }
}

external interface IngredientsTableContentProps : Props {
    var ingredients: List<IngredientsTableRow>
    var onIngredientClick: (Int) -> Unit
}

val IngredientsTableContent = FC<IngredientsTableContentProps> { props ->
    TableBody {
        if (props.ingredients.isEmpty()) TableCell {
            colSpan = 4
            Typography {
                color = "text.secondary"
                +"Keine Zutaten vorhanden, bitte füge eine oder mehrere Zutaten hinzu"
            }
        } else props.ingredients.mapIndexed { index, item ->
            TableRow {
                TableCell {
                    padding = TableCellPadding.checkbox
                    Checkbox {
                        color = CheckboxColor.primary
                        checked = item.selected
                        onClick = {
                            props.onIngredientClick(index)
                        }
                    }
                }
                TableCell {
                    padding = TableCellPadding.none
                    +item.item.name
                }
                TableCell {
                    align = TdAlign.right
                    padding = TableCellPadding.normal
                    +item.item.amount.toString()
                }
                TableCell {
                    align = TdAlign.right
                    padding = TableCellPadding.normal
                    +item.item.unit.ifEmpty { "-" }
                }
            }
        }
    }
}