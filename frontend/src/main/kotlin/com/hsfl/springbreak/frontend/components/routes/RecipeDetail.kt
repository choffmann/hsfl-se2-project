package com.hsfl.springbreak.frontend.components.routes

import com.hsfl.springbreak.frontend.client.data.model.Ingredient
import com.hsfl.springbreak.frontend.client.data.model.Recipe
import com.hsfl.springbreak.frontend.client.presentation.viewmodel.events.LifecycleEvent
import com.hsfl.springbreak.frontend.client.presentation.viewmodel.events.RecipeDetailEvent
import com.hsfl.springbreak.frontend.client.presentation.viewmodel.recipe.detail.RecipeDetailViewModel
import com.hsfl.springbreak.frontend.di.di
import com.hsfl.springbreak.frontend.utils.collectAsState
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
import org.kodein.di.instance
import org.w3c.dom.url.URL
import react.*
import react.dom.html.InputType
import react.dom.html.ReactHTML
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.label
import react.dom.html.ReactHTML.span
import web.file.File

external interface RecipeDetailProps : Props {
    var recipeId: Int
}

val RecipeDetail = FC<RecipeDetailProps> { props ->
    val viewModel: RecipeDetailViewModel by di.instance()
    val recipeState = viewModel.recipe.collectAsState()
    val myRecipeState = viewModel.isMyRecipe.collectAsState()
    val editModeState = viewModel.editMode.collectAsState()
    val isFavoriteState = viewModel.isFavorite.collectAsState()

    useEffect(Unit) {
        viewModel.onEvent(RecipeDetailEvent.RecipeId(props.recipeId))
        cleanup { viewModel.onEvent(LifecycleEvent.OnUnMount) }
    }

    if (editModeState) {
        RecipeDetailEdit {
            recipe = recipeState
            onSave = {}
            onCancel = {
                viewModel.onEvent(RecipeDetailEvent.CancelEdit)
            }
        }
    } else {
        RecipeDetailView {
            recipe = recipeState
            isMyRecipe = myRecipeState
            isFavorite = isFavoriteState
            onFavoriteClick = {
                viewModel.onEvent(RecipeDetailEvent.OnFavorite)
            }
            onLikeClick = {}
            onDislikeClick = {}
            onEditClick = {
                viewModel.onEvent(RecipeDetailEvent.OnEdit)
            }
            onDeleteClick = {}
        }
    }


}

private external interface RecipeDetailEditProps : Props {
    var recipe: Recipe
    var onRecipeImageChanged: (File) -> Unit
    var onCancel: () -> Unit
    var onSave: () -> Unit
}

private val RecipeDetailEdit = FC<RecipeDetailEditProps> { props ->
    var profileImageState by useState(props.recipe.image)
    Box {
        profileImageState?.let { image ->
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

private external interface RecipeTitleEditProps : Props {
    var title: String
    var subtitle: String
}

private val RecipeTitleEdit = FC<RecipeTitleEditProps> { props ->
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
    var onCancel: () -> Unit
    var onSave: () -> Unit
}

private val RecipeActionEdit = FC<RecipeActionEditProps> { props ->
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

private external interface RecipeIngredientTableEditProps : Props {
    var recipe: Recipe
    var selectedIngredients: List<Ingredient>
}

private val RecipeIngredientTableEdit = FC<RecipeIngredientTableEditProps> { props ->
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

private external interface RecipeDetailViewProps : Props {
    var recipe: Recipe
    var isMyRecipe: Boolean
    var isFavorite: Boolean
    var onFavoriteClick: () -> Unit
    var onLikeClick: () -> Unit
    var onDislikeClick: () -> Unit
    var onEditClick: () -> Unit
    var onDeleteClick: () -> Unit
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
                isFavorite = props.isFavorite
                onDeleteClick = props.onDeleteClick
                onLikeClick = props.onLikeClick
                onDislikeClick = props.onDislikeClick
                onFavoriteClick = props.onFavoriteClick
                onEditClick = props.onEditClick
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
                metaDataText = props.recipe.category.name
                chipIcon = Icon.create { Category() }
            }

            RecipeMetaData {
                metaDataText = props.recipe.price.toInt().toString()
                chipIcon = Icon.create { Euro() }
            }

            RecipeMetaData {
                metaDataText = "${props.recipe.duration.toInt()} min"
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
    var isFavorite: Boolean
    var onFavoriteClick: () -> Unit
    var onLikeClick: () -> Unit
    var onDislikeClick: () -> Unit
    var onEditClick: () -> Unit
    var onDeleteClick: () -> Unit
}

private val RecipeAction = FC<RecipeActionProps> { props ->
    Box {
        sx {
            display = Display.flex
            alignItems = AlignItems.center
            justifyContent = JustifyContent.flexStart
        }
        Rating {
            value = 2.5
            precision = 0.5
        }
        if (props.isFavorite) {
            Tooltip {
                title = ReactNode("Favorite entfernen")
                IconButton {
                    onClick = { props.onFavoriteClick() }
                    Favorite {
                        sx {
                            color = Color("red")
                        }
                    }
                }
            }
        } else {
            Tooltip {
                title = ReactNode("Als Favorite speichern")
                IconButton {
                    onClick = { props.onFavoriteClick() }
                    Favorite {
                        color = SvgIconColor.inherit
                    }
                }
            }
        }

        if (props.myRecipe) {
            Tooltip {
                title = ReactNode("Dieses Rezept bearbeiten")
                IconButton {
                    Edit {
                        onClick = { props.onEditClick() }
                        color = SvgIconColor.inherit
                    }
                }
            }
            Tooltip {
                title = ReactNode("Dieses Rezept löschen")
                IconButton {
                    onClick = { props.onDeleteClick() }
                    DeleteForever {
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
    var editMode: Boolean
    var onChipClick: () -> Unit
}

private val RecipeMetaData = FC<RecipeMetaDataProps> { props ->
    Box {
        sx {
            paddingRight = 8.px
        }
        props.chipIcon?.let {
            Chip {
                sx {
                    alignItems = AlignItems.center
                }
                icon = it
                label = ReactNode(props.metaDataText)
                if (props.editMode) {
                    onClick = { props.onChipClick() }
                }
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

