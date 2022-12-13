package com.hsfl.springbreak.frontend.components.routes

import com.hsfl.springbreak.frontend.client.data.model.Recipe
import com.hsfl.springbreak.frontend.client.presentation.viewmodel.recipe.detail.RecipeDetailViewModel
import com.hsfl.springbreak.frontend.di.di
import com.hsfl.springbreak.frontend.utils.collectAsState
import com.hsfl.springbreak.frontend.utils.component
import csstype.*
import emotion.react.css
import kotlinx.js.get
import mui.icons.material.*
import mui.material.*
import mui.material.styles.TypographyVariant
import mui.system.responsive
import mui.system.sx
import org.kodein.di.instance
import org.w3c.dom.url.URL
import react.*
import react.dom.html.InputType
import react.dom.html.ReactHTML
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.label
import react.dom.html.ReactHTML.span
import web.file.File

val RecipeDetail = FC<Props> {
    val viewModel: RecipeDetailViewModel by di.instance()
    val recipeState = viewModel.recipe.collectAsState()
    val myRecipeState = viewModel.isMyRecipe.collectAsState()

    RecipeDetailView {
        recipe = recipeState
        isMyRecipe = myRecipeState
    }
    /*RecipeDetailEdit {
        recipe = recipeState
    }*/
}

private external interface RecipeDetailEditProps : Props {
    var recipe: Recipe
    var onProfileImageChanged: (File) -> Unit
}

private val RecipeDetailEdit = FC<RecipeDetailEditProps> { props ->
    var profileImage by useState<String?>(props.recipe.image)
    Box {
        profileImage?.let { image ->
            if (image.isNotEmpty()) {
                Box {
                    div {
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
                                onProfileImageChanged = { profileImage = URL.createObjectURL(it) }
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
                    onProfileImageChanged = { profileImage = URL.createObjectURL(it) }
                }
            }
        } ?: Box {
            sx {
                display = Display.flex
                justifyContent = JustifyContent.center
                paddingTop = 16.px
            }
            UploadRecipeImageButton {
                onProfileImageChanged = { profileImage = URL.createObjectURL(it) }
            }
        }

        Box {
            sx {
                marginTop = 8.px
                paddingLeft = 8.px
                paddingRight = 8.px
                display = Display.flex
                justifyContent = JustifyContent.spaceBetween
            }
            RecipeTitleEdit {
                title = props.recipe.title
                subtitle = props.recipe.shortDescription
            }
            RecipeActionEdit {
            }
        }
    }
}

private external interface RecipeTitleEditProps : Props {
    var title: String
    var subtitle: String
}

private val RecipeTitleEdit = FC<RecipeTitleEditProps> { props ->
    Stack {
        direction = responsive(StackDirection.column)
        spacing = responsive(2)
        TextField {
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

private external interface UploadRecipeImageButtonProps : Props {
    var onProfileImageChanged: (File) -> Unit
}

private val UploadRecipeImageButton = FC<UploadRecipeImageButtonProps> { props ->
    Fab {
        component = label
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

private external interface RecipeActionEditProps : Props {
    var myRecipe: Boolean
}

private val RecipeActionEdit = FC<RecipeActionEditProps> { props ->
    Box {
        Button {
            sx { paddingLeft = 8.px }
            +"Abbrechen"
        }
        Button {
            variant = ButtonVariant.contained
            startIcon = Icon.create { Save() }
            +"Speichern"
        }
    }
}

private external interface RecipeDetailViewProps : Props {
    var recipe: Recipe
    var isMyRecipe: Boolean
}

private val RecipeDetailView = FC<RecipeDetailViewProps> { props ->
    Box {
        /**
         * Show recipe image
         */
        props.recipe.image?.let { image ->
            if (image.isNotEmpty()) {
                div {
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
                    }
                }
            }
        }

        /**
         * Show recipe title, subtitle and user action area
         */
        Box {
            sx {
                marginTop = 8.px
                paddingLeft = 8.px
                paddingRight = 8.px
                display = Display.flex
                justifyContent = JustifyContent.spaceBetween
            }
            RecipeTitle {
                title = props.recipe.title
                subtitle = props.recipe.shortDescription
            }
            RecipeAction {
                myRecipe = props.isMyRecipe
            }
        }

        /**
         * Show recipe metadata chips
         */
        Box {
            sx {
                marginTop = 8.px
                paddingLeft = 8.px
                paddingRight = 8.px
                display = Display.flex
                flexDirection = FlexDirection.row
                alignItems = AlignItems.center
            }

            RecipeMetaData {
                metaDataText = "${props.recipe.creator.firstName} ${props.recipe.creator.lastName}"
                userFistName = props.recipe.creator.firstName
                userLastName = props.recipe.creator.lastName
                profileImage = props.recipe.creator.image
            }

            RecipeMetaData {
                metaDataText = props.recipe.difficulty.name
                chipIcon = Icon.create { ShowChart() }
            }


            RecipeMetaData {
                metaDataText = props.recipe.difficulty.name
                chipIcon = Icon.create { Category() }
            }

            RecipeMetaData {
                metaDataText = props.recipe.price.toInt().toString()
                chipIcon = Icon.create { Euro() }
            }

            RecipeMetaData {
                metaDataText = props.recipe.duration.toInt().toString()
                chipIcon = Icon.create { AccessTime() }
            }

            RecipeMetaData {
                metaDataText = props.recipe.views.toString()
                chipIcon = Icon.create { Visibility() }
            }
        }

        /**
         * Show recipe ingredients in Table
         */
        TableContainer {
            Table {
                TableHead {
                    TableCell { +"Menge" }
                    TableCell { +"Zutat" }
                }
                TableBody {
                    props.recipe.ingredients.map {
                        TableRow {
                            TableCell { +"${it.amount} ${it.unit}" }
                            TableCell { +it.name }
                        }
                    }
                }
            }
        }

        /**
         * Show recipe description
         */
        Box {
            sx {
                padding = 8.px
            }
            Typography {
                // To show multiline
                component = span
                css {
                    whiteSpace = WhiteSpace.preLine
                    textAlign = TextAlign.justify
                }
                variant = TypographyVariant.body1
                +props.recipe.longDescription
            }
        }
    }
}

external interface RecipeTitleProps : Props {
    var title: String
    var subtitle: String
}

private val RecipeTitle = FC<RecipeTitleProps> { props ->
    Box {
        Typography {
            component = span
            noWrap = false
            sx { fontWeight = FontWeight.bold }
            variant = TypographyVariant.h4
            +props.title
        }
        Typography {
            variant = TypographyVariant.body1
            +props.subtitle
        }
    }
}


private external interface RecipeActionProps : Props {
    var myRecipe: Boolean
}

private val RecipeAction = FC<RecipeActionProps> { props ->
    Box {
        Tooltip {
            title = ReactNode("Das Rezept gefällt mir nicht")
            IconButton {
                ThumbDown {
                    color = SvgIconColor.inherit
                }
            }
        }
        Tooltip {
            title = ReactNode("Das Rezept gefällt mir")
            IconButton {
                ThumbUp {
                    color = SvgIconColor.inherit
                }
            }
        }
        Tooltip {
            title = ReactNode("Als Favorite speichern")
            IconButton {
                Favorite {
                    color = SvgIconColor.inherit
                }
            }
        }

        if (props.myRecipe) {
            Tooltip {
                title = ReactNode("Dieses Rezept bearbeiten")
                IconButton {
                    Edit {
                        color = SvgIconColor.inherit
                    }
                }
            }
            Tooltip {
                title = ReactNode("Dieses Rezept löschen")
                IconButton {
                    Delete {
                        color = SvgIconColor.error
                    }
                }
            }
        }
    }
}

private external interface RecipeMetaDataProps : Props {
    var profileImage: String?
    var userFistName: String?
    var userLastName: String?
    var chipIcon: ReactElement<*>?
    var metaDataText: String
}

private val RecipeMetaData = FC<RecipeMetaDataProps> { props ->
    Box {
        sx {
            paddingRight = 8.px
        }
        props.chipIcon?.let {
            Chip {
                icon = it
                label = ReactNode(props.metaDataText)
            }
        } ?: Chip {
            avatar = Avatar.create {
                props.profileImage?.let {
                    src = it
                } ?: "${props.userFistName?.get(0)}${props.userLastName?.get(0)}"
            }
            label = ReactNode(props.metaDataText)
        }
    }
}

