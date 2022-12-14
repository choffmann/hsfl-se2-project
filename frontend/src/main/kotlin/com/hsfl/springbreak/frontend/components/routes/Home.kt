package com.hsfl.springbreak.frontend.components.routes

import com.hsfl.springbreak.frontend.client.data.model.Recipe
import com.hsfl.springbreak.frontend.client.presentation.viewmodel.HomeRecipeTab
import com.hsfl.springbreak.frontend.client.presentation.viewmodel.HomeViewModel
import com.hsfl.springbreak.frontend.client.presentation.viewmodel.events.HomeViewEvent
import com.hsfl.springbreak.frontend.client.presentation.viewmodel.events.LifecycleEvent
import com.hsfl.springbreak.frontend.components.recipe.RecipeCard
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
            TabView { list = recipeList.map { it.recipe } }
        }
        TabPanel {
            value = currentTab
            index = HomeRecipeTab.FastTab
            TabView { list = recipeList.map { it.recipe } }
        }
        TabPanel {
            value = currentTab
            index = HomeRecipeTab.PopularTab
            TabView { list = recipeList.map { it.recipe } }
        }
        TabPanel {
            value = currentTab
            index = HomeRecipeTab.AllTab
            TabView { list = recipeList.map { it.recipe } }
        }
    }
}


external interface TabViewProps : Props {
    var list: List<Recipe>
}

val TabView = FC<TabViewProps> { props ->
    Box {
        sx {
            flexGrow = number(1.0)
            margin = 8.px
            display = Display.flex
            justifyContent = JustifyContent.center
        }

        Masonry {
            sx {
                minWidth = 1000.px
                maxWidth = 1400.px
            }
            columns = responsive(3)
            spacing = responsive(2)
            props.list.forEach {
                RecipeCard {
                    isMyRecipe = true
                    id = it.id
                    title = it.title
                    createdDate = "Bla"
                    creator = "${it.creator.firstName} ${it.creator.lastName}"
                    imageSrc = it.image ?: ""
                    shortDescription = it.shortDescription
                    cost = it.price.toInt().toString()
                    duration = it.duration.toInt().toString()
                    difficulty = it.difficulty.name
                    creatorImg = it.creator.image
                }
            }
        }
    }
}