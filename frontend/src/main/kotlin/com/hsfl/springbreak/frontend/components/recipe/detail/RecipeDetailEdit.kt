package com.hsfl.springbreak.frontend.components.recipe.detail

import com.hsfl.springbreak.frontend.client.data.model.Category
import com.hsfl.springbreak.frontend.client.data.model.Difficulty
import com.hsfl.springbreak.frontend.client.data.model.Ingredient
import com.hsfl.springbreak.frontend.client.data.model.Recipe
import com.hsfl.springbreak.frontend.client.presentation.viewmodel.events.CreateRecipeIngredientsEvent
import com.hsfl.springbreak.frontend.client.presentation.viewmodel.events.LifecycleEvent
import com.hsfl.springbreak.frontend.client.presentation.viewmodel.recipe.create.CreateRecipeIngredientsVM
import com.hsfl.springbreak.frontend.client.presentation.viewmodel.recipe.create.IngredientsTableVM
import com.hsfl.springbreak.frontend.client.presentation.viewmodel.recipe.detail.RecipeDetailEditViewModel
import com.hsfl.springbreak.frontend.components.recipe.create.IngredientDialog
import com.hsfl.springbreak.frontend.components.recipe.create.IngredientEditDialog
import com.hsfl.springbreak.frontend.components.recipe.create.IngredientsTable
import com.hsfl.springbreak.frontend.di.di
import com.hsfl.springbreak.frontend.utils.collectAsState
import com.hsfl.springbreak.frontend.utils.component
import csstype.*
import dom.html.HTMLInputElement
import dom.html.HTMLTextAreaElement
import emotion.react.css
import kotlinx.js.get
import mui.icons.material.*
import mui.material.*
import mui.system.responsive
import mui.system.sx
import org.kodein.di.instance
import org.w3c.dom.url.URL
import react.*
import react.dom.html.InputType
import react.dom.html.ReactHTML
import react.dom.onChange
import web.file.File

external interface RecipeDetailEditProps : Props {
    var recipe: Recipe
    var onCancel: () -> Unit
    var onSave: (Recipe.Update, File?) -> Unit
}

val RecipeDetailEdit = FC<RecipeDetailEditProps> { props ->
    val viewModel: RecipeDetailEditViewModel by di.instance()
    val categoryListState = viewModel.categoryList.collectAsState()
    val difficultyListState = viewModel.difficultyList.collectAsState()

    var profileImageState by useState(props.recipe.image)
    var recipeTitleState by useState(props.recipe.title)
    var recipeSubtitleState by useState(props.recipe.shortDescription)
    var recipeDifficultyState by useState(props.recipe.difficulty.id.toString())
    var recipeCategoryState by useState(props.recipe.category.id.toString())
    var recipePriceState by useState(props.recipe.price)
    var recipeDurationState by useState(props.recipe.duration)
    var recipeDescriptionState by useState(props.recipe.longDescription)

    useEffect(Unit) {
        viewModel.onEvent(LifecycleEvent.OnMount)
        cleanup { viewModel.onEvent(LifecycleEvent.OnUnMount) }
    }

    Box {
        profileImageState?.let { image ->
            if (image.isNotEmpty()) {
                Box {
                    ReactHTML.div {
                        css {
                            backgroundImage = url(image)
                            height = 30.vh
                            backgroundPosition = GeometryPosition.center
                            backgroundRepeat = BackgroundRepeat.noRepeat
                            backgroundSize = BackgroundSize.cover
                            position = Position.relative
                            borderRadius = 8.px
                            boxShadow = BoxShadow(
                                offsetX = 3.px,
                                offsetY = 3.px,
                                blurRadius = 8.px,
                                color = Color("lightgrey")
                            )
                            opacity = number(0.70)
                        }
                        Box {
                            sx {
                                height = 30.vh
                                display = Display.flex
                                justifyContent = JustifyContent.center
                                flexDirection = FlexDirection.row
                                alignItems = AlignItems.center
                            }
                            UploadRecipeImageButton {
                                onProfileImageChanged = { profileImageState = URL.createObjectURL(it) }
                            }
                        }
                    }
                }
            } else Box {
                sx {
                    display = Display.flex
                    justifyContent = JustifyContent.center
                    paddingTop = 16.px
                }
                UploadRecipeImageButton {
                    onProfileImageChanged = { profileImageState = URL.createObjectURL(it) }
                }
            }
        } ?: Box {
            sx {
                display = Display.flex
                justifyContent = JustifyContent.center
                paddingTop = 16.px
            }
            UploadRecipeImageButton {
                onProfileImageChanged = { profileImageState = URL.createObjectURL(it) }
            }
        }

        Box {
            sx {
                marginTop = 16.px
                paddingLeft = 8.px
                paddingRight = 8.px
            }
            RecipeDetailEditMetadata {
                title = recipeTitleState
                subtitle = recipeSubtitleState
                difficulty = recipeDifficultyState
                difficultyList = difficultyListState
                onDifficultyChange = { recipeDifficultyState = it }
                category = category
                categoryList = categoryListState
                onCategoryChange = { recipeCategoryState = it }
                duration = recipeDurationState
                onDurationChange = { recipeDurationState = it.toDouble() }
                price = recipePriceState
                onPriceChange = { recipePriceState = it }
                onTileChange = { recipeTitleState = it }
                onSubtitleChange = { recipeSubtitleState = it }
            }
        }

        /*RecipeIngredientTableEdit {
            recipe = props.recipe
            selectedIngredients = listOf()
        }*/
        IngredientsTable {
            editMode = true
            ingredientsList = props.recipe.ingredients
            onNewIngredient = {
                val ingredientsViewModel: CreateRecipeIngredientsVM by di.instance()
                ingredientsViewModel.onEvent(CreateRecipeIngredientsEvent.OnAddIngredient)
            }
        }
        IngredientDialog()
        IngredientEditDialog()
    }

    RecipeDescriptionEdit {
        description = recipeDescriptionState
        onChange = { recipeDescriptionState = it }
    }

    Box {
        sx { marginTop = 8.px }
        RecipeActionEdit {
            onSave = {
                val ingredientsViewModel: IngredientsTableVM by di.instance()
                val recipe = Recipe.Update(
                    id = props.recipe.id,
                    title = recipeTitleState,
                    shortDescription = recipeSubtitleState,
                    price = recipePriceState,
                    duration = recipeDurationState,
                    difficultyId = recipeDifficultyState.toString().toInt(),
                    categoryId = recipeCategoryState.toString().toInt(),
                    longDescription = recipeDescriptionState,
                    ingredients = ingredientsViewModel.ingredientsList.value.map {
                        Ingredient.Create(name = it.item.name, unit = it.item.unit, amount = it.item.amount)
                    }
                )
                props.onSave(recipe, null)
            }
            onCancel = props.onCancel
        }
    }
}

external interface RecipeDetailEditMetadataProps : Props {
    var title: String
    var subtitle: String
    var difficultyList: List<Difficulty>
    var difficulty: String
    var categoryList: List<Category>
    var category: String
    var price: Double
    var duration: Double
    var onDifficultyChange: (String) -> Unit
    var onCategoryChange: (String) -> Unit
    var onPriceChange: (Double) -> Unit
    var onDurationChange: (Int) -> Unit
    var onTileChange: (String) -> Unit
    var onSubtitleChange: (String) -> Unit
}

val RecipeDetailEditMetadata = FC<RecipeDetailEditMetadataProps> { props ->
    Box {
        Stack {
            direction = responsive(StackDirection.column)
            spacing = responsive(2)
            TextField {
                fullWidth = true
                label = ReactNode("Rezeptname")
                value = props.title
                onChange = {
                    val target = it.target as HTMLInputElement
                    props.onTileChange(target.value)
                }
            }
            TextField {
                fullWidth = true
                label = ReactNode("Kurzbeschreibung")
                value = props.subtitle
                onChange = {
                    val target = it.target as HTMLInputElement
                    props.onSubtitleChange(target.value)
                }
            }
            FormControl {
                InputLabel { +"Preis" }
                OutlinedInput {
                    type = "number"
                    label = Typography.create { +"Preis" }
                    value = props.price
                    endAdornment = InputAdornment.create {
                        Euro()
                    }
                    onChange = {
                        val target = it.target as HTMLInputElement
                        props.onPriceChange(target.value.toDouble())
                    }
                }
            }

            FormControl {
                InputLabel { +"Dauer" }
                OutlinedInput {
                    type = "number"
                    label = Typography.create { +"Dauer" }
                    endAdornment = InputAdornment.create { +"min" }
                    value = props.duration
                    onChange = {
                        val target = it.target as HTMLInputElement
                        props.onDurationChange(target.value.toInt())
                    }
                }
            }

            FormControl {
                InputLabel { +"Schwierigkeit" }
                Select {
                    label = Typography.create { +"Schwierigkeit" }
                    value = props.difficulty
                    onChange = { event, _ ->
                        props.onDifficultyChange(event.target.value)
                    }
                    props.difficultyList.map {
                        MenuItem {
                            value = it.id
                            +it.name
                        }
                    }
                }
            }
            FormControl {
                InputLabel { +"Kategorie" }
                Select {
                    label = Typography.create { +"Kategorie" }
                    value = props.category
                    onChange = { event, _ ->
                        props.onCategoryChange(event.target.value)
                    }
                    props.categoryList.map {
                        MenuItem {
                            value = it.id
                            +it.name
                        }
                    }
                }
            }
        }
    }
}

external interface RecipeDescriptionEditProps : Props {
    var description: String
    var onChange: (String) -> Unit
}

val RecipeDescriptionEdit = FC<RecipeDescriptionEditProps> { props ->
    Box {
        sx { marginTop = 8.px }
        TextField {
            label = ReactNode("Beschreibung")
            fullWidth = true
            multiline = true
            value = props.description
            onChange = {
                val target = it.target as HTMLTextAreaElement
                props.onChange(target.value)
            }
        }
    }
}

external interface UploadRecipeImageButtonProps : Props {
    var onProfileImageChanged: (File) -> Unit
}

val UploadRecipeImageButton = FC<UploadRecipeImageButtonProps> { props ->
    Fab {
        component = ReactHTML.label
        color = FabColor.secondary
        variant = FabVariant.extended
        ReactHTML.input {
            hidden = true
            accept = "image/*"
            type = InputType.file
            onChange = {
                if (it.target.files?.length != 0) {
                    it.target.files?.get(0)?.let { file ->
                        props.onProfileImageChanged(file)
                    }
                }
            }
        }
        Upload()
        +"Bild anpassen"
    }
}

external interface RecipeActionEditProps : Props {
    var onCancel: () -> Unit
    var onSave: () -> Unit
}

val RecipeActionEdit = FC<RecipeActionEditProps> { props ->
    Box {
        sx {
            display = Display.flex
            flexDirection = FlexDirection.row
            justifyContent = JustifyContent.flexEnd
            alignItems = AlignItems.flexStart
            paddingLeft = 8.px
        }
        Button {
            onClick = {
                props.onCancel()
            }
            +"Abbrechen"
        }
        Button {
            variant = ButtonVariant.contained
            startIcon = Icon.create { Save() }
            onClick = { props.onSave() }
            +"Speichern"
        }
    }
}
