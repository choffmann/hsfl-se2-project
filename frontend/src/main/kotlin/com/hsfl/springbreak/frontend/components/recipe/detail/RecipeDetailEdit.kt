package com.hsfl.springbreak.frontend.components.recipe.detail

import com.hsfl.springbreak.frontend.client.data.model.Ingredient
import com.hsfl.springbreak.frontend.client.data.model.Recipe
import com.hsfl.springbreak.frontend.utils.color
import com.hsfl.springbreak.frontend.utils.component
import csstype.*
import emotion.react.css
import kotlinx.js.get
import mui.icons.material.*
import mui.material.*
import mui.material.styles.TypographyVariant
import mui.system.responsive
import mui.system.sx
import org.w3c.dom.url.URL
import react.*
import react.dom.html.InputType
import react.dom.html.ReactHTML
import web.file.File

external interface RecipeDetailEditProps : Props {
    var recipe: Recipe
    var onRecipeImageChanged: (File) -> Unit
    var onCancel: () -> Unit
    var onSave: () -> Unit
}

val RecipeDetailEdit = FC<RecipeDetailEditProps> { props ->
    var profileImageState by useState(props.recipe.image)
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
            RecipeTitleEdit {
                title = props.recipe.title
                subtitle = props.recipe.shortDescription
            }

        }

        Box {
            sx {
                marginTop = 8.px
                paddingLeft = 8.px
                paddingRight = 8.px
                display = Display.flex
                flexDirection = FlexDirection.row
                alignItems = AlignItems.center
            }

            Tooltip {
                title = ReactNode("Schwierigkeit bearbeiten")
                RecipeMetaData {
                    metaDataText = props.recipe.difficulty.name
                    editMode = true
                    chipIcon = Icon.create { ShowChart() }
                }
            }


            RecipeMetaData {
                metaDataText = props.recipe.category.name
                editMode = true
                chipIcon = Icon.create { Category() }
            }

            RecipeMetaData {
                metaDataText = props.recipe.price.toInt().toString()
                editMode = true
                chipIcon = Icon.create { Euro() }
            }

            RecipeMetaData {
                metaDataText = "${props.recipe.duration.toInt()} min"
                editMode = true
                chipIcon = Icon.create { AccessTime() }
            }
        }
        RecipeIngredientTableEdit {
            recipe = props.recipe
            selectedIngredients = listOf()
        }
    }

    TextField {
        sx { marginTop = 8.px }
        label = ReactNode("Beschreibung")
        fullWidth = true
        multiline = true
        value = props.recipe.longDescription
    }
    Box {
        sx { marginTop = 8.px }
        RecipeActionEdit {
            onSave = props.onSave
            onCancel = props.onCancel
        }
    }
}

external interface RecipeTitleEditProps : Props {
    var title: String
    var subtitle: String
}

val RecipeTitleEdit = FC<RecipeTitleEditProps> { props ->
    Stack {
        direction = responsive(StackDirection.column)
        spacing = responsive(2)
        TextField {
            fullWidth = true
            label = ReactNode("Rezeptname")
            value = props.title
        }
        TextField {
            fullWidth = true
            label = ReactNode("Kurzbeschreibung")
            value = props.subtitle
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

external interface RecipeIngredientTableEditProps : Props {
    var recipe: Recipe
    var selectedIngredients: List<Ingredient>
}

val RecipeIngredientTableEdit = FC<RecipeIngredientTableEditProps> { props ->
    TableContainer {
        if (props.selectedIngredients.isNotEmpty()) {
            Toolbar {
                sx {
                    display = Display.flex
                    justifyContent = JustifyContent.spaceBetween
                    marginTop = 16.px
                    if (props.selectedIngredients.isNotEmpty())
                        backgroundColor = Color("lightgrey")
                }
                Typography {
                    variant = TypographyVariant.subtitle1
                    color = "inherit"
                    +"${props.selectedIngredients.size} ausgewählt"
                }
                Box {
                    if (props.selectedIngredients.size == 1) Tooltip {
                        title = Typography.create { +"Bearbeiten" }
                        IconButton {
                            onClick = {

                            }
                            Edit()
                        }
                    }
                    Tooltip {
                        title = Typography.create { +"Löschen" }
                        IconButton {
                            onClick = {

                            }
                            Delete()
                        }
                    }
                }
            }
        }
        Table {
            TableHead {
                TableCell {
                    padding = TableCellPadding.checkbox
                    +""
                }
                TableCell { +"Menge" }
                TableCell { +"Zutat" }
            }
            TableBody {
                props.recipe.ingredients.map {
                    TableRow {
                        TableCell {
                            padding = TableCellPadding.checkbox
                            Checkbox {

                            }
                        }
                        TableCell { +"${it.amount} ${it.unit}" }
                        TableCell { +it.name }
                    }
                }
            }
        }
    }
}