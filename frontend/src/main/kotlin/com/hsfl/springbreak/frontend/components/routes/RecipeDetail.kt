package com.hsfl.springbreak.frontend.components.routes

import com.hsfl.springbreak.frontend.client.data.model.*
import com.hsfl.springbreak.frontend.client.data.model.Recipe
import csstype.*
import emotion.react.css
import mui.icons.material.*
import mui.material.*
import mui.material.styles.TypographyVariant
import mui.system.sx
import react.*
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.span

val RecipeDetail = FC<Props> {
    val recipe by useState(
        Recipe(
            id = 1,
            title = "Chicken Tikka Masala",
            creator = User(
                id = 1,
                firstName = "Jeeve",
                lastName = "Philodendron",
                email = "jeeve.philodendron@plants.com",
                password = "geheim",
                image = "https://fangblatt.de/media/image/12/b8/b5/Philodendron-Green-Princess-8_600x600.jpg"
            ),
            price = 15.0,
            createTime = "2022-12-12T20:30.20307",
            image = "https://image.essen-und-trinken.de/11854764/t/Gb/v8/w960/r1/-/chicken-tikka-masala-c3c77cebac45a391e04fcbcff54bee08-chicken-tikka-masala-jpg--20494-.jpg",
            shortDescription =
            "Ein sehr leckeres Gericht. Es passt perfekt zu Basmatireis",
            duration = 45.0,
            difficulty = Difficulty(
                id = 1,
                name = "Mittel"
            ),
            category = Category(
                id = 1,
                name = "Nudeln"
            ),
            ingredients = listOf(
                Ingredient(id = 1, name = "Knoblauchzehen", amount = 6, unit = ""),
                Ingredient(id = 2, name = "Senfsaat", amount = 1, unit = "EL"),
                Ingredient(id = 3, name = "Paprikapulver", amount = 1, unit = "EL"),
                Ingredient(id = 3, name = "Garam Masala", amount = 3, unit = "EL"),
                Ingredient(id = 4, name = "Tomatenmark", amount = 2, unit = "EL"),
                Ingredient(id = 5, name = "Naturjoghurt", amount = 200, unit = "g"),
            ),
            longDescription = "1. Knoblauch und Ingwer mit dem feinsten Aufsatz der Käsereibe reiben und in eine Schüssel geben. Die Chilis so fein wie möglich schneiden und mit dem Knoblauch und Ingwer mischen. Einen guten Schuss Olivenöl in einer Pfanne erhitzen und die Senfsaat hinengeben. Sobald sie zu platzen beginnen, zusammen mit Cumin, Paprikapulver, dem gemahlenen Koriander und 2 El Garam Masala zur Knoblauch-Ingwer-Mischung geben. Die Hälfte der Mischung mit dem Joghurt vermengen, mit den Putenstücken in eine Schüssel geben, umrühren und ca.eine halbe Stunde marinieren.\n\n2. Butter schmelzen, Zwiebeln und die übrige Hälfte der Gewürzmischung dazugeben, ca. 15 min. köcheln lassen ohne es zu sehr braun werden zu lassen- es sollte angenehm riechen! Tomatenmark, die gemahlenen Nüsse, einen halben Liter Wasser und einen halben Tl Salz dazugeben. Gut umrühren und einige Minuten köcheln lassen bis die Soße reduziert hat und leicht dick geworden ist und zur Seite stellen.\n\n3. Die marinierten Putenstücke in eine heiße Pfanne geben und anbraten.\n\n4. Die Soße erhitzen und Crème Double und den anderen El Garam Masala hinzugeben. Sobald es zu kochen beginnt, Herd abschalten und die gebratenen Putenstücke dazugeben. Gegebenenfalls nachwürzen und mit gehacktem Koriander und Limettensaft servieren. Dazu passt Basmatireis.",
            views = 120
        )
    )

    Box {
        /**
         * Show recipe image
         */
        recipe.image?.let { image ->
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
                title = recipe.title
                subtitle = recipe.shortDescription
            }
            RecipeAction()
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
                metaDataText = "${recipe.creator.firstName} ${recipe.creator.lastName}"
                userFistName = recipe.creator.firstName
                userLastName = recipe.creator.lastName
                profileImage = recipe.creator.image
            }

            RecipeMetaData {
                metaDataText = recipe.difficulty.name
                chipIcon = Icon.create { ShowChart() }
            }


            RecipeMetaData {
                metaDataText = recipe.difficulty.name
                chipIcon = Icon.create { Category() }
            }

            RecipeMetaData {
                metaDataText = recipe.price.toInt().toString()
                chipIcon = Icon.create { Euro() }
            }

            RecipeMetaData {
                metaDataText = recipe.duration.toInt().toString()
                chipIcon = Icon.create { AccessTime() }
            }

            RecipeMetaData {
                metaDataText = recipe.views.toString()
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
                    recipe.ingredients.map {
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
                +recipe.longDescription
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

private val RecipeAction = FC<Props> { props ->
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

        /**
         * If authorized
         */
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

