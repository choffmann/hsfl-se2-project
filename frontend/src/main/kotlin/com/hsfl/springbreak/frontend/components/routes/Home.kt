package com.hsfl.springbreak.frontend.components.routes

import com.hsfl.springbreak.frontend.client.presentation.viewmodel.HomeRecipeTab
import com.hsfl.springbreak.frontend.client.presentation.viewmodel.HomeViewModel
import com.hsfl.springbreak.frontend.client.presentation.viewmodel.events.HomeViewEvent
import com.hsfl.springbreak.frontend.client.presentation.viewmodel.events.LifecycleEvent
import com.hsfl.springbreak.frontend.client.presentation.viewmodel.recipe.RecipeListType
import com.hsfl.springbreak.frontend.components.recipe.RecipeCard
import com.hsfl.springbreak.frontend.components.recipe.RecipeList
import com.hsfl.springbreak.frontend.di.di
import com.hsfl.springbreak.frontend.utils.collectAsState
import csstype.Display
import csstype.JustifyContent
import csstype.number
import csstype.px
import mui.icons.material.*
import mui.lab.Masonry
import mui.material.*
import mui.material.Tab
import mui.system.responsive
import mui.system.sx
import org.kodein.di.instance
import react.*
import react.dom.aria.AriaRole
import react.dom.events.SyntheticEvent
import react.dom.html.ReactHTML.div

data class Recipe(
    val title: String,
    val createdDate: String,
    val creator: String,
    val creatorImg: String? = null,
    val imageSrc: String,
    val shortDescription: String,
    val cost: String,
    val duration: String,
    val difficulty: String
)

external interface TabPanelProps : PropsWithChildren {
    var index: HomeRecipeTab
    var value: HomeRecipeTab
}

var TabPanel = FC<TabPanelProps> { props ->
    div {
        role = AriaRole.tabpanel
        hidden = props.value != props.index
        if (props.index == props.value) +props.children
    }
}


var Home = FC<Props> {
    val viewModel: HomeViewModel by di.instance()
    val currentTab = viewModel.currentTab.collectAsState()
    val recipeList = viewModel.recipeList.collectAsState()

    useEffect(Unit) {
        viewModel.onEvent(LifecycleEvent.OnMount)
        cleanup { viewModel.onEvent(LifecycleEvent.OnUnMount) }
    }

    val handleChange: (SyntheticEvent<*, *>, Int) -> Unit = { _, value ->
        when (value) {
            0 -> viewModel.onEvent(HomeViewEvent.OnTabChange(HomeRecipeTab.CheapTab))
            1 -> viewModel.onEvent(HomeViewEvent.OnTabChange(HomeRecipeTab.FastTab))
            2 -> viewModel.onEvent(HomeViewEvent.OnTabChange(HomeRecipeTab.PopularTab))
            3 -> viewModel.onEvent(HomeViewEvent.OnTabChange(HomeRecipeTab.AllTab))
        }
    }

    Box {
        Box {
            Tabs {
                value = currentTab.toInt()
                onChange = handleChange
                Tab {
                    icon = Icon.create { AttachMoney() }
                    iconPosition = IconPosition.start
                    label = Typography.create { +"Günstig" }
                }
                Tab {
                    icon = Icon.create { AccessAlarm() }
                    iconPosition = IconPosition.start
                    label = Typography.create { +"Schnell" }
                }
                Tab {
                    icon = Icon.create { TrendingUp() }
                    iconPosition = IconPosition.start
                    label = Typography.create { +"Beliebt" }
                }
                Tab {
                    icon = Icon.create { AllInclusive() }
                    iconPosition = IconPosition.start
                    label = Typography.create { +"Alle" }
                }

            }
            Divider()
        }
        TabPanel {
            value = currentTab
            index = HomeRecipeTab.CheapTab
            RecipeList { listType = RecipeListType.HomeList.CheapTab }
        }
        TabPanel {
            value = currentTab
            index = HomeRecipeTab.FastTab
            RecipeList { listType = RecipeListType.HomeList.FastTab }
        }
        TabPanel {
            value = currentTab
            index = HomeRecipeTab.PopularTab
            RecipeList { listType = RecipeListType.HomeList.PopularTab }
        }
        TabPanel {
            value = currentTab
            index = HomeRecipeTab.AllTab
            RecipeList { listType = RecipeListType.HomeList.AllTab }
        }
    }
}