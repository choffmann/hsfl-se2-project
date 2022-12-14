package com.hsfl.springbreak.frontend.components.recipe

import com.hsfl.springbreak.frontend.utils.color
import csstype.Color
import csstype.FontWeight
import csstype.px
import mui.icons.material.*
import mui.material.*
import mui.material.styles.TypographyVariant
import mui.system.responsive
import mui.system.sx
import react.*
import react.dom.html.ReactHTML.img

const val recipeCardWidth = 344

external interface RecipeCardItemProps : Props {
    var id: Int
    var title: String
    var createdDate: String
    var creator: String
    var creatorImg: String?
    var imageSrc: String
    var shortDescription: String
    var cost: String
    var duration: String
    var difficulty: String
    var score: Double
    var isMyRecipe: Boolean
    var isFavorite: Boolean
    var onClick: (Int) -> Unit
    var onFavoriteClick: (Int) -> Unit
}

val RecipeCardItem = FC<RecipeCardItemProps> { props ->

    // Returned the first letters from a name (James Sullivan => JS)
    val userNameInLetter: (String) -> String = {
        val x = it.split(" ")
        "${x[0][0]}${x[1][0]}"
    }

    Card {
        sx { width = recipeCardWidth.px }
        CardHeader {
            avatar = Tooltip.create {
                title = Typography.create { +props.creator }
                Avatar {
                    props.creatorImg?.let {
                        src = it
                    } ?: +userNameInLetter(props.creator)
                }
            }
            title = Typography.create {
                sx { fontWeight = FontWeight.bold }
                +props.title
            }
            subheader = Typography.create {
                +props.createdDate
            }
        }
        CardActionArea {
            CardMedia {
                component = img
                image = props.imageSrc
            }
            CardContent {
                onClick = {
                    props.onClick(props.id)
                }
                Typography {
                    variant = TypographyVariant.body2
                    color = "text.secondary"
                    +props.shortDescription
                }
            }
        }
        CardContent {
            sx {
                paddingTop = 0.px
                paddingBottom = 0.px
            }
            Stack {
                direction = responsive(StackDirection.row)
                spacing = responsive(1)
                Chip {
                    icon = Icon.create { Euro() }
                    label = Typography.create { +props.cost }
                }
                Chip {
                    icon = Icon.create { AccessTime() }
                    label = Typography.create { +props.duration }
                }
                Chip {
                    icon = Icon.create { ShowChart() }
                    label = Typography.create { +props.difficulty }
                }
            }
        }
        CardActions {
            disableSpacing = true
            IconButton {
                onClick = { props.onFavoriteClick(props.id) }
                disabled = props.isMyRecipe
                Tooltip {
                    title = Typography.create {
                        if (props.isFavorite) +"Aus Favoriten entfernen"
                        else +"Zu Favoriten hinzufügen"
                    }
                    Favorite {
                        sx {
                            if (props.isFavorite) color = Color("red")
                        }
                    }
                }
            }
            Stack {
                direction = responsive(StackDirection.row)
                spacing = responsive(1)
                Rating {
                    value = props.score
                    precision = 0.5
                    size = Size.small
                    readOnly = true
                }
            }
        }
    }
}