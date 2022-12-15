package com.hsfl.springbreak.frontend.components.recipe.detail

import com.hsfl.springbreak.frontend.client.data.model.Recipe
import csstype.*
import emotion.react.css
import mui.icons.material.*
import mui.material.*
import mui.material.styles.TypographyVariant
import mui.system.sx
import react.*
import react.dom.html.ReactHTML

external interface RecipeDetailViewProps : Props {
    var recipe: Recipe
    var isMyRecipe: Boolean
    var isFavorite: Boolean
    var onFavoriteClick: () -> Unit
    var onLikeClick: () -> Unit
    var onDislikeClick: () -> Unit
    var onEditClick: () -> Unit
    var onDeleteClick: () -> Unit
    var onScoreChange: (Double) -> Unit
}

val RecipeDetailView = FC<RecipeDetailViewProps> { props ->
    Box {
        /**
         * Show recipe image
         */
        props.recipe.image?.let { image ->
            if (image.isNotEmpty()) {
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
                score = props.recipe.score
                onDeleteClick = props.onDeleteClick
                onFavoriteClick = props.onFavoriteClick
                onEditClick = props.onEditClick
                onScoreChange = props.onScoreChange
            }
        }

        /**
         * Show recipe metadata chips
         */

        /**
         * Show recipe metadata chips
         */

        /**
         * Show recipe metadata chips
         */

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
                component = ReactHTML.span
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

val RecipeTitle = FC<RecipeTitleProps> { props ->
    Box {
        Typography {
            component = ReactHTML.span
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


external interface RecipeActionProps : Props {
    var myRecipe: Boolean
    var isFavorite: Boolean
    var score: Double
    var onFavoriteClick: () -> Unit
    var onEditClick: () -> Unit
    var onDeleteClick: () -> Unit
    var onScoreChange: (Double) -> Unit
}

val RecipeAction = FC<RecipeActionProps> { props ->
    Box {
        sx {
            display = Display.flex
            alignItems = AlignItems.center
            justifyContent = JustifyContent.flexStart
        }
        Rating {
            value = props.score
            precision = 0.5
            onChange = { _, newValue -> props.onScoreChange(newValue?.toDouble() ?: 0.0) }
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

external interface RecipeMetaDataProps : Props {
    var profileImage: String?
    var userFistName: String?
    var userLastName: String?
    var chipIcon: ReactElement<*>?
    var metaDataText: String
    var editMode: Boolean
    var onChipClick: () -> Unit
}

val RecipeMetaData = FC<RecipeMetaDataProps> { props ->
    Box {
        sx {
            paddingRight = 8.px
        }
        props.chipIcon?.let {
            Chip {
                sx {
                    alignItems = AlignItems.center
                    MuiChip.root {
                        display = Display.flex
                        justifyContent = JustifyContent.center
                        alignItems = AlignItems.center
                    }
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