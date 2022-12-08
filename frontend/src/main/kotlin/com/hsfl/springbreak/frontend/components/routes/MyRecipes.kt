package com.hsfl.springbreak.frontend.components.routes

import com.hsfl.springbreak.frontend.components.recipe.ShowMyRecipes
import com.hsfl.springbreak.frontend.utils.color
import csstype.px
import mui.icons.material.*
import mui.material.*
import mui.material.Tab
import mui.material.styles.TypographyVariant
import mui.system.sx
import react.FC
import react.Props
import react.create
import react.dom.events.SyntheticEvent
import react.useState

val MyRecipes = FC<Props> {
    var tabValue by useState(0)

    val handleChange: (SyntheticEvent<*, *>, Int) -> Unit = { _, newValue ->
        tabValue = newValue
    }

    Box {
        Tabs {
            value = tabValue
            onChange = handleChange
            Tab {
                icon = Icon.create { Collections() }
                iconPosition = IconPosition.start
                label = Typography.create { +"Galerie" }
            }

            Tab {
                icon = Icon.create { TableChart() }
                iconPosition = IconPosition.start
                label = Typography.create { +"Tabelle" }
            }
        }
        Divider()
    }

    TabPanel {
        value = tabValue
        index = 0
        Typography {
            variant = TypographyVariant.body1
            color = "text.secondary"
            +"Es sind noch keine Rezepte vorhanden"
        }
    }
    TabPanel {
        value = tabValue
        index = 1
        Box {
            sx { marginTop = 16.px }
            ShowMyRecipes()
        }
    }

}