package com.hsfl.springbreak.frontend.components.routes.create

import com.hsfl.springbreak.frontend.client.presentation.viewmodel.recipe.create.IngredientsTableVM
import com.hsfl.springbreak.frontend.client.presentation.viewmodel.recipe.create.RecipeIngredient
import com.hsfl.springbreak.frontend.di.di
import com.hsfl.springbreak.frontend.utils.collectAsState
import com.hsfl.springbreak.frontend.utils.color
import csstype.Color
import csstype.Display
import csstype.JustifyContent
import csstype.px
import mui.icons.material.Delete
import mui.material.*
import mui.material.styles.TypographyVariant
import mui.system.sx
import org.kodein.di.instance
import react.FC
import react.Props
import react.create
import react.dom.html.TdAlign


val IngredientsTable = FC<Props> {
    val viewModel: IngredientsTableVM by di.instance()
    val selectedIngredients = viewModel.selectedIngredients.collectAsState()
    val ingredientsList = viewModel.ingredientsList.collectAsState()
    // TODO: this is called to update ui when list is updated...
    viewModel.randomState.collectAsState()

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
                    +"${ingredientsList.size} ausgewählt"
                }
                Tooltip {
                    title = Typography.create { +"Löschen" }
                    IconButton {
                        Delete()
                    }
                }
            } else {
                Typography {
                    variant = TypographyVariant.h6
                    +"Zutatenliste"
                }
            }
        }
        Table {
            TableHead {
                TableRow {
                    TableCell {
                        padding = TableCellPadding.checkbox
                        Checkbox {
                            color = CheckboxColor.primary
                            disabled = ingredientsList.isEmpty()
                        }
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
            }
        }
    }
}

external interface IngredientsTableContentProps : Props {
    var ingredients: List<RecipeIngredient>
}

val IngredientsTableContent = FC<IngredientsTableContentProps> { props ->
    TableBody {
        if (props.ingredients.isEmpty()) TableCell {
            colSpan = 4
            Typography {
                color = "text.secondary"
                +"Keine Zutaten, bitte füge eine oder mehrere Zutaten hinzu"
            }
        } else props.ingredients.map {
            TableRow {
                key = it.name
                TableCell {
                    padding = TableCellPadding.checkbox
                    Checkbox {
                        color = CheckboxColor.primary
                    }
                }
                TableCell {
                    padding = TableCellPadding.none
                    +it.name
                }
                TableCell {
                    align = TdAlign.right
                    padding = TableCellPadding.normal
                    +it.amount.toString()
                }
                TableCell {
                    align = TdAlign.right
                    padding = TableCellPadding.normal
                    +it.unit.ifEmpty { "-" }
                }
            }
        }
    }
}