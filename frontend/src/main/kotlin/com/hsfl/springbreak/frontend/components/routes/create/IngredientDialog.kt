package com.hsfl.springbreak.frontend.components.routes.create

import com.hsfl.springbreak.frontend.client.data.model.Ingredient
import com.hsfl.springbreak.frontend.client.presentation.viewmodel.recipe.create.IngredientAutocompleteState
import com.hsfl.springbreak.frontend.client.presentation.viewmodel.recipe.create.IngredientsDialogEvent
import com.hsfl.springbreak.frontend.client.presentation.viewmodel.recipe.create.IngredientsDialogVM
import com.hsfl.springbreak.frontend.client.presentation.viewmodel.recipe.create.RecipeIngredient
import com.hsfl.springbreak.frontend.di.di
import com.hsfl.springbreak.frontend.utils.collectAsState
import csstype.px
import dom.html.HTMLInputElement
import kotlinx.js.ReadonlyArray
import mui.base.FilterOptionsState
import mui.icons.material.Add
import mui.icons.material.ExpandMore
import mui.material.*
import mui.system.responsive
import mui.system.sx
import org.kodein.di.instance
import react.*

val IngredientDialog = FC<Props> {
    val viewModel: IngredientsDialogVM by di.instance()
    val openDialog = viewModel.openDialog.collectAsState()
    val ingredients = viewModel.ingredientsList.collectAsState()
    val ingredientName = viewModel.ingredientName.collectAsState()
    val ingredientAmount = viewModel.ingredientAmount.collectAsState()
    val ingredientUnit = viewModel.ingredientUnit.collectAsState()
    val autoComplete = viewModel.autoCompleteState.collectAsState()

    var nameState by useState(ingredientName)
    var amountState by useState(ingredientAmount)
    var unitState by useState(ingredientUnit)

    val resetInternalStates: () -> Unit = {
        nameState = ""
        amountState = 0
        unitState = ""
    }

    Dialog {
        open = openDialog
        fullWidth = true
        maxWidth = "lg"
        DialogTitle { +"Zutat hinzufügen" }
        DialogContent {
            IngredientAccordion {
                ingredientList = ingredients
            }
            IngredientsFormular {
                isEditing = false
                name = nameState
                unit = unitState
                amount = amountState
                onNameChanged = {
                    nameState = it
                    viewModel.onEvent(IngredientsDialogEvent.IngredientNameChanged(it))
                }
                onAmountChanged = {
                    amountState = it
                    viewModel.onEvent(IngredientsDialogEvent.IngredientAmountChanged(it))
                }
                onUnitChanged = {
                    unitState = it
                    viewModel.onEvent(IngredientsDialogEvent.IngredientUnitChanged(it))
                }
                onNewIngredient = {
                    resetInternalStates()
                    viewModel.onEvent(IngredientsDialogEvent.OnAddMoreIngredient)
                }
                autoCompleteState = autoComplete
                onAutocompleteClick = {
                    viewModel.onEvent(IngredientsDialogEvent.OnIngredientAutoComplete)
                }
            }
        }
        DialogActions {
            Button {
                onClick = {
                    resetInternalStates()
                    viewModel.onEvent(IngredientsDialogEvent.OnAbort)
                }
                +"Abbrechen"
            }
            Button {
                variant = ButtonVariant.contained
                onClick = {
                    resetInternalStates()
                    viewModel.onEvent(IngredientsDialogEvent.OnFinished)
                }
                +"Fertig"
            }
        }
    }
}

external interface IngredientsFormularProps : DialogContentProps {
    var onNewIngredient: () -> Unit
    var name: String
    var onNameChanged: (String) -> Unit
    var amount: Int
    var onAmountChanged: (Int) -> Unit
    var unit: String
    var onUnitChanged: (String) -> Unit
    var isEditing: Boolean
    var autoCompleteState: IngredientAutocompleteState
    var onAutocompleteClick: () -> Unit
}

val IngredientsFormular = FC<IngredientsFormularProps> { props ->
    Stack {
        sx {
            paddingTop = 8.px
        }
        direction = responsive(StackDirection.column)
        spacing = responsive(2)
        if (props.isEditing) {
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
        } else {
            AutoCompleteIngredient {
                ingredients = props.autoCompleteState.allIngredients
                isLoading = props.autoCompleteState.loading
                onInputChanged = {
                    props.onNameChanged(it)
                }
                onClick = {
                    props.onAutocompleteClick()
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
        if (!props.isEditing) {
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


external interface AutoCompleteIngredientProps : Props {
    var ingredients: Array<Ingredient.Label>
    var isLoading: Boolean
    var onInputChanged: (String) -> Unit
    var onClick: () -> Unit
}

val AutoCompleteIngredient = FC<AutoCompleteIngredientProps> { props ->
    @Suppress("UPPER_BOUND_VIOLATED") Autocomplete<AutocompleteProps<Ingredient.Label>> {
        disablePortal = true
        freeSolo = true
        selectOnFocus = true
        clearOnBlur = true
        handleHomeEndKeys = true
        options = props.ingredients
        loading = props.isLoading
        loadingText = ReactNode("Lade Daten...")
        renderInput = { params ->
            TextField.create {
                +params
                label = ReactNode("Zutat")
                onClick = {
                    println("AutoCompleteIngredient::click")
                    props.onClick()
                }
            }
        }
        onInputChange = { _, value, _ ->
            props.onInputChanged(value)
        }

        // Own filter option to show "{ZUTAT} hinzufügen" in list
        filterOptions =
            { labels: ReadonlyArray<Ingredient.Label>, filterOptionsState: FilterOptionsState<Ingredient.Label> ->
                val filtered =
                    labels.filter { it.label.startsWith(filterOptionsState.inputValue, 0, true) }.toMutableList()
                val isExisting = filtered.find { it.equals(filterOptionsState.inputValue) }?.let { true } ?: false
                if (filterOptionsState.inputValue != "" && !isExisting) {
                    filtered.add(Ingredient.Label(id = -1, label = filterOptionsState.inputValue))
                }
                filtered.toTypedArray()
            }
    }
}
