package com.hsfl.springbreak.frontend.components.recipe

import com.hsfl.springbreak.frontend.utils.color
import csstype.Color
import csstype.FontWeight
import csstype.px
import kotlinx.js.jso
import mui.icons.material.*
import mui.material.*
import mui.material.Menu
import mui.material.styles.TypographyVariant
import mui.system.responsive
import mui.system.sx
import react.*
import react.dom.events.MouseEvent
import react.dom.events.MouseEventHandler
import react.dom.html.ReactHTML.img

data class Point(val x: Double, val y: Double)

const val recipeCardWidth = 344

external interface RecipeCardItemProps : Props {
    var title: String
    var createdDate: String
    var creator: String
    var imageSrc: String
    var shortDescription: String
    var cost: String
    var duration: String
    var difficulty: String
}

val RecipeCardItem = FC<RecipeCardItemProps> { props ->
    var point by useState<Point>()
    var isFavorite by useState(false)
    var showSnackbar by useState(false)


    val handleContextMenu = { event: MouseEvent<*, *> ->
        event.preventDefault()
        point = if (point == null) {
            Point(
                x = event.clientX - 2, y = event.clientY - 4
            )
        } else {
            null
        }
    }

    val handleClose: MouseEventHandler<*> = { point = null }

    val handleClickFavorite: MouseEventHandler<*> = {
        if (!isFavorite) {
            showSnackbar = true
        }
        isFavorite = !isFavorite
    }

    // Returned the first letters from a name (James Sullivan => JS)
    val userNameInLetter: (String) -> String = {
        val x = it.split(" ")
        "${x[0][0]}${x[1][0]}"
    }

    Card {
        sx { maxWidth = recipeCardWidth.px }
        CardHeader {
            avatar = Tooltip.create {
                title = Typography.create { +props.creator }
                Avatar {
                    +userNameInLetter(props.creator)
                }
            }
            action = IconButton.create {
                onClick = handleContextMenu
                MoreVert()
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
                onClick = handleClickFavorite
                Tooltip {
                    title = Typography.create {
                        if (isFavorite) +"Aus Favoriten entfernen"
                        else +"Zu Favoriten hinzufügen"
                    }
                    Favorite {
                        sx {
                            if (isFavorite) color = Color("red")
                        }
                    }
                }
            }
            Stack {
                direction = responsive(StackDirection.row)
                spacing = responsive(1)
                Rating {
                    value = 2.5
                    precision = 0.5
                    size = Size.small
                    readOnly = true
                }
                Typography {
                    variant = TypographyVariant.body2
                    color = "text.secondary"
                    +"(123)"
                }
            }
        }
        Menu {
            open = point != null
            onClose = handleClose

            anchorReference = PopoverReference.anchorPosition
            anchorPosition = if (point != null) {
                jso {
                    top = point!!.y
                    left = point!!.x
                }
            } else {
                undefined
            }

            MenuItem {
                ListItemIcon { Visibility() }
                ListItemText { +"View" }
            }
            MenuItem {
                ListItemIcon { Edit() }
                ListItemText { +"Edit" }
            }
            MenuItem {
                ListItemIcon { Delete() }
                ListItemText { +"Delete" }
            }
        }
    }
}