package com.hsfl.springbreak.frontend.components.routes.create

import com.hsfl.springbreak.frontend.context.StepperContext
import csstype.px
import dom.html.HTMLTextAreaElement
import mui.icons.material.CloseOutlined
import mui.icons.material.Euro
import mui.material.*
import mui.system.responsive
import mui.system.sx
import react.*

val RecipeCreateStepOne = FC<Props> {
    val currentStep = useContext(StepperContext)
    var shortDesc by useState("")
    var difficulty by useState("")
    var category by useState("")
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
            }
        }
        FormControl {
            InputLabel { +"Kurzbeschreibung" }
            OutlinedInput {
                multiline = true
                label = Typography.create { +"Kurzbeschreibung" }
                endAdornment = InputAdornment.create {
                    +"${shortDesc.length}/100"
                }
                value = shortDesc
                onChange = {
                    // TODO: Can't delete on 100
                    if (shortDesc.length < 100) {
                        val target = it.target as HTMLTextAreaElement
                        shortDesc = target.value
                    }
                }
            }
        }
        FormControl {
            InputLabel { +"Preis" }
            OutlinedInput {
                type = "number"
                label = Typography.create { +"Preis" }
                endAdornment = InputAdornment.create {
                    Euro()
                }
            }
        }
        FormControl {
            InputLabel { +"Dauer" }
            OutlinedInput {
                type = "number"
                label = Typography.create { +"Dauer" }
                endAdornment = InputAdornment.create {
                    +"min"
                }
            }
        }
        FormControl {
            InputLabel { +"Schwierigkeit" }
            required = true
            Select {
                label = Typography.create { +"Schwierigkeit" }
                value = difficulty
                onChange = { event, _ ->
                    difficulty = event.target.value
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
        }
        FormControl {
            InputLabel { +"Kategorie" }
            Select {
                label = Typography.create { +"Kategorie" }
                value = category
                onChange = { event, _ ->
                    category = event.target.value
                }
                if (category.isNotEmpty()) {
                    endAdornment = InputAdornment.create {
                        IconButton {
                            sx { marginRight = 8.px }
                            edge = IconButtonEdge.start
                            size = Size.small
                            onClick = {
                                category = ""
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
        }
    }
}