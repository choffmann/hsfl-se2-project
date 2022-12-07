package com.hsfl.springbreak.frontend.components.routes.create

import csstype.px
import dom.html.HTMLInputElement
import mui.icons.material.Add
import mui.icons.material.ExpandMore
import mui.material.*
import mui.system.responsive
import mui.system.sx
import react.*

external interface IngredientDialogProps : Props {
    var open: Boolean
    var onClose: () -> Unit
    var onFinished: (List<RecipeIngredient>) -> Unit
}

val IngredientDialog = FC<IngredientDialogProps> { props ->
    var ingredients: List<RecipeIngredient> by useState(emptyList())
    var ingredientName by useState("")
    var ingredientAmount by useState(0)
    var ingredientUnit by useState("")

    val addIngredient: () -> Unit = {
        val ingredient = RecipeIngredient(
            name = ingredientName,
            amount = ingredientAmount,
            unit = ingredientUnit
        )
        // To force react to render child
        val ingredientsList: MutableList<RecipeIngredient> = ingredients.toMutableList()
        ingredientsList.add(ingredient)
        ingredients = ingredientsList.toList()
    }

    val clearTextFieldStates: () -> Unit = {
        ingredientName = ""
        ingredientAmount = 0
        ingredientUnit = ""
    }

    val clearIngredientsList: () -> Unit = {
        ingredients = emptyList()
    }

    Dialog {
        open = props.open
        fullWidth = true
        maxWidth = "lg"
        DialogTitle { +"Zutat hinzufügen" }
        DialogContent {
            IngredientAccordion {
                ingredientList = ingredients
            }
            IngredientsFormular {
                name = ingredientName
                unit = ingredientUnit
                amount = ingredientAmount
                onNameChanged = { ingredientName = it }
                onAmountChanged = { ingredientAmount = it }
                onUnitChanged = { ingredientUnit = it }
                onNewIngredient = {
                    clearTextFieldStates()
                    addIngredient()
                }
            }
        }
        DialogActions {
            Button {
                onClick = {
                    clearIngredientsList()
                    clearTextFieldStates()
                    props.onClose()
                }
                +"Abbrechen"
            }
            Button {
                variant = ButtonVariant.contained
                onClick = {
                    addIngredient()
                    props.onFinished(ingredients)
                    clearIngredientsList()
                    clearTextFieldStates()
                }
                +"Fertig"
            }
        }
    }
}

private external interface IngredientsFormularProps : DialogContentProps {
    var onNewIngredient: () -> Unit
    var name: String
    var onNameChanged: (String) -> Unit
    var amount: Int
    var onAmountChanged: (Int) -> Unit
    var unit: String
    var onUnitChanged: (String) -> Unit
}

private val IngredientsFormular = FC<IngredientsFormularProps> { props ->


    Stack {
        sx {
            paddingTop = 8.px
        }
        direction = responsive(StackDirection.column)
        spacing = responsive(2)
        FormControl {
            InputLabel { +"Zutat" }
            required = true
            OutlinedInput {
                fullWidth = true
                value = props.name
                label = Typography.create { +"Zutat" }
                onChange = {
                    val target = it.target as HTMLInputElement
                    props.onNameChanged(target.value)
                }
            }
        }
        FormControl {
            InputLabel { +"Menge" }
            required = true
            OutlinedInput {
                fullWidth = true
                value = props.amount
                type = "number"
                label = Typography.create { +"Menge" }
                onChange = {
                    val target = it.target as HTMLInputElement
                    props.onAmountChanged(target.value.toInt())
                }
            }
        }
        FormControl {
            InputLabel { +"Einheit" }
            OutlinedInput {
                fullWidth = true
                value = props.unit
                label = Typography.create { +"Einheit" }
                onChange = {
                    val target = it.target as HTMLInputElement
                    props.onUnitChanged(target.value)
                }
            }
        }
        Button {
            variant = ButtonVariant.outlined
            startIcon = Icon.create { Add() }
            onClick = {
                props.onNewIngredient()
            }
            +"Eine weitere Zutat hinzufügen"
        }
    }
}

private external interface DialogContentNotEmptyProps : DialogContentProps {
    var ingredientList: List<RecipeIngredient>
}

private val IngredientAccordion = FC<DialogContentNotEmptyProps> { props ->
    Box {
        props.ingredientList.forEach { ingredient ->
            Accordion {
                AccordionSummary {
                    expandIcon = Icon.create { ExpandMore() }
                    Typography { +ingredient.name }
                }
                AccordionDetails {
                    Stack {
                        sx {
                            paddingTop = 8.px
                        }
                        direction = responsive(StackDirection.column)
                        spacing = responsive(2)
                        FormControl {
                            InputLabel { +"Zutat" }
                            required = true
                            OutlinedInput {
                                fullWidth = true
                                value = ingredient.name
                                label = Typography.create { +"Zutat" }
                            }
                        }
                        FormControl {
                            InputLabel { +"Menge" }
                            required = true
                            OutlinedInput {
                                fullWidth = true
                                type = "number"
                                value = ingredient.amount
                                label = Typography.create { +"Menge" }
                            }
                        }
                        FormControl {
                            InputLabel { +"Einheit" }
                            OutlinedInput {
                                fullWidth = true
                                value = ingredient.unit
                                label = Typography.create { +"Einheit" }
                            }
                        }
                    }
                }
            }
        }
    }
}