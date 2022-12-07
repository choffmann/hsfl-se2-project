package com.hsfl.springbreak.frontend.components.routes.create

import com.hsfl.springbreak.frontend.client.presentation.viewmodel.recipe.create.CreateRecipeDataEvent
import com.hsfl.springbreak.frontend.client.presentation.viewmodel.recipe.create.CreateRecipeDataVM
import com.hsfl.springbreak.frontend.di.di
import com.hsfl.springbreak.frontend.utils.collectAsState
import csstype.px
import dom.html.HTMLInputElement
import dom.html.HTMLTextAreaElement
import mui.icons.material.CloseOutlined
import mui.icons.material.Euro
import mui.material.*
import mui.system.responsive
import mui.system.sx
import org.kodein.di.instance
import react.*

val RecipeCreateData = FC<Props> {
    val viewModel: CreateRecipeDataVM by di.instance()
    val nameTextState = viewModel.ingredientName.collectAsState()
    val shortDescTextState = viewModel.ingredientShortDesc.collectAsState()
    val durationTextState = viewModel.ingredientDuration.collectAsState()
    val priceTextState = viewModel.ingredientPrice.collectAsState()
    val difficultyTextState = viewModel.ingredientDifficulty.collectAsState()
    val categoryTextState = viewModel.ingredientCategory.collectAsState()

    var name by useState(nameTextState.value)
    var shortDesc by useState(shortDescTextState.value)
    var duration by useState(durationTextState.value)
    var price by useState(priceTextState.value)
    var difficulty by useState(difficultyTextState.value)
    var category by useState(categoryTextState.value)

    Stack {
        sx {
            padding = 16.px
        }
        direction = responsive(StackDirection.column)
        spacing = responsive(2)
        FormControl {
            InputLabel { +"Name" }
            required = true
            OutlinedInput {
                label = Typography.create { +"Name" }
                error = nameTextState.error
                value = name
                onChange = {
                    val target = it.target as HTMLInputElement
                    name = target.value
                    viewModel.onEvent(CreateRecipeDataEvent.IngredientName(target.value))
                }
            }
            FormHelperText { +nameTextState.errorMsg }
        }
        FormControl {
            InputLabel { +"Kurzbeschreibung" }
            OutlinedInput {
                multiline = true
                label = Typography.create { +"Kurzbeschreibung" }
                required = shortDescTextState.required
                error = shortDescTextState.error
                endAdornment = InputAdornment.create {
                    +"${shortDesc.length}/100"
                }
                value = shortDesc
                onChange = {
                    // TODO: Can't delete on 100
                    if (shortDesc.length < 100) {
                        val target = it.target as HTMLTextAreaElement
                        shortDesc = target.value
                        viewModel.onEvent(CreateRecipeDataEvent.IngredientShortDesc(target.value))
                    }
                }
            }
            FormHelperText { +shortDescTextState.errorMsg }
        }
        FormControl {
            InputLabel { +"Preis" }
            required = priceTextState.required
            error = priceTextState.error
            OutlinedInput {
                type = "number"
                label = Typography.create { +"Preis" }
                value = price
                endAdornment = InputAdornment.create {
                    Euro()
                }
                onChange = {
                    val target = it.target as HTMLInputElement
                    price = target.value.toDouble()
                    viewModel.onEvent(CreateRecipeDataEvent.IngredientPrice(target.value.toDouble()))
                }
            }
            FormHelperText { +priceTextState.errorMsg }
        }
        FormControl {
            InputLabel { +"Dauer" }
            required = durationTextState.required
            error = durationTextState.error
            OutlinedInput {
                type = "number"
                label = Typography.create { +"Dauer" }
                endAdornment = InputAdornment.create { +"min" }
                value = duration
                onChange = {
                    val target = it.target as HTMLInputElement
                    duration = target.value.toInt()
                    viewModel.onEvent(CreateRecipeDataEvent.IngredientDuration(target.value.toInt()))
                }
            }
            FormHelperText { +durationTextState.errorMsg }
        }
        FormControl {
            InputLabel { +"Schwierigkeit" }
            required = difficultyTextState.required
            error = difficultyTextState.error
            Select {
                label = Typography.create { +"Schwierigkeit" }
                value = difficulty
                onChange = { event, _ ->
                    difficulty = event.target.value
                    viewModel.onEvent(CreateRecipeDataEvent.IngredientDifficulty(event.target.value))
                }
                MenuItem {
                    value = "leicht"
                    +"Leicht"
                }
                MenuItem {
                    value = "mittel"
                    +"Mittel"
                }
                MenuItem {
                    value = "schwer"
                    +"Schwer"
                }
            }
            FormHelperText { +difficultyTextState.errorMsg }
        }
        FormControl {
            InputLabel { +"Kategorie" }
            required = categoryTextState.required
            error = categoryTextState.error
            Select {
                label = Typography.create { +"Kategorie" }
                value = category
                onChange = { event, _ ->
                    category = event.target.value
                    viewModel.onEvent(CreateRecipeDataEvent.IngredientCategory(event.target.value))
                }
                if (category.isNotEmpty()) {
                    endAdornment = InputAdornment.create {
                        IconButton {
                            sx { marginRight = 8.px }
                            edge = IconButtonEdge.start
                            size = Size.small
                            onClick = {
                                category = ""
                                viewModel.onEvent(CreateRecipeDataEvent.IngredientCategory(""))
                            }
                            CloseOutlined()
                        }
                    }
                }
                MenuItem {
                    value = "kuchen"
                    +"Kuchen"
                }
                MenuItem {
                    value = "nudeln"
                    +"Nudeln"
                }
                MenuItem {
                    value = "reis"
                    +"Reis"
                }
                MenuItem {
                    value = "fleisch"
                    +"Fleisch"
                }
                MenuItem {
                    value = "vegetarisch"
                    +"Vegetarisch"
                }
            }
            FormHelperText { +categoryTextState.errorMsg }
        }
    }
}