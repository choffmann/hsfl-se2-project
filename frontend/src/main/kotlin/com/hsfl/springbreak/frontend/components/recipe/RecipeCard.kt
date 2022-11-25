package com.hsfl.springbreak.frontend.components.recipe

import com.hsfl.springbreak.frontend.utils.color
import com.hsfl.springbreak.frontend.utils.toMuiPx
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
import react.FC
import react.Props
import react.create
import react.dom.events.MouseEvent
import react.dom.events.MouseEventHandler
import react.dom.events.SyntheticEvent
import react.dom.html.ReactHTML.img
import react.useState

data class Point(val x: Double, val y: Double)

val RecipeCard = FC<Props> {
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

    // val onCloseSnackbar: (SyntheticEvent<*, *>, SnackbarCloseReason)
    val onCloseSnackbar = { _: SyntheticEvent<*, *>, _: SnackbarCloseReason ->
        showSnackbar = false
    }

    Card {
        sx { maxWidth = 43.toMuiPx() }
        CardHeader {
            avatar = Tooltip.create {
                title = Typography.create { +"Cedrik Hoffmann" }
                Avatar {
                    +"CH"
                }
            }
            action = IconButton.create {
                onClick = handleContextMenu
                MoreVert()
            }
            title = Typography.create {
                sx { fontWeight = FontWeight.bold }
                +"Chicken Tikka Masala"
            }
            subheader = Typography.create {
                +"November 13, 2022"
            }
        }
        CardActionArea {
            CardMedia {
                component = img
                image =
                    "https://image.essen-und-trinken.de/11854764/t/Gb/v8/w960/r1/-/chicken-tikka-masala-c3c77cebac45a391e04fcbcff54bee08-chicken-tikka-masala-jpg--20494-.jpg"
            }
            CardContent {
                Typography {
                    variant = TypographyVariant.body2
                    color = "text.secondary"
                    +"Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut"
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
                    label = Typography.create { +"5" }
                }
                Chip {
                    icon = Icon.create { AccessTime() }
                    label = Typography.create { +"45 min" }
                }
                Chip {
                    icon = Icon.create { ShowChart() }
                    label = Typography.create { +"Mittel" }
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