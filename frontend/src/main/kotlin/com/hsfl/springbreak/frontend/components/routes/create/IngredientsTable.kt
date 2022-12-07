package com.hsfl.springbreak.frontend.components.routes.create

import com.hsfl.springbreak.frontend.utils.color
import mui.material.*
import mui.material.styles.TypographyVariant
import react.FC
import react.Props
import react.dom.html.TdAlign

external interface IngredientsTableProps : Props {
    var ingredientsList: List<RecipeIngredient>
}

val IngredientsTable = FC<IngredientsTableProps> { props ->
    val tableHeads = listOf("Zutat", "Menge", "Einheit")
    TableContainer {
        Toolbar {
            Typography {
                variant = TypographyVariant.h6
                +"Zutatenliste"
            }
        }
        Table {
            TableHead {
                TableRow {
                    TableCell {
                        padding = TableCellPadding.checkbox
                        Checkbox {
                            color = CheckboxColor.primary
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
            TableBody {
                if (props.ingredientsList.isEmpty()) TableCell {
                    colSpan = 4
                    Typography {
                        color = "text.secondary"
                        +"Keine Zutaten, bitte lege eine oder mehrere Zutaten an"
                    }
                } else props.ingredientsList.forEach {

                    TableRow {
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
    }
}