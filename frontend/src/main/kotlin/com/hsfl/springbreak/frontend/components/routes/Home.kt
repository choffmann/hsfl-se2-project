package com.hsfl.springbreak.frontend.components.routes

import com.hsfl.springbreak.frontend.components.routes.home.AllTab
import com.hsfl.springbreak.frontend.components.routes.home.CheapTab
import com.hsfl.springbreak.frontend.components.routes.home.FastTab
import com.hsfl.springbreak.frontend.components.routes.home.PopularTab
import mui.material.*
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
    var index: Int
    var value: Int
}

var TabPanel = FC<TabPanelProps> { props ->
    div {
        role = AriaRole.tabpanel
        hidden = props.value != props.index
        if (props.value == props.index) +props.children
    }
}


var Home = FC<Props> {
    val list = listOf(
        Recipe(
            title = "Chicken Tikka Masala",
            createdDate = "November 13, 2022",
            creator = "James Sullivan",
            imageSrc =
            "https://image.essen-und-trinken.de/11854764/t/Gb/v8/w960/r1/-/chicken-tikka-masala-c3c77cebac45a391e04fcbcff54bee08-chicken-tikka-masala-jpg--20494-.jpg",
            shortDescription =
            "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut",
            cost = "1",
            duration = "45 min",
            difficulty = "Mittel"
        ),
        Recipe(
            title = "Flammkuchen",
            createdDate = "November 13, 2022",
            creator = "Mike Glotzkowski",
            creatorImg = "https://i.imgflip.com/2/3e74xu.jpg",
            imageSrc =
            "https://img.chefkoch-cdn.de/rezepte/1112251217261411/bilder/1021874/crop-960x720/einfacher-flammkuchen.jpg",
            shortDescription =
            "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut",
            cost = "5",
            duration = "45 min",
            difficulty = "Mittel"
        ), Recipe(
            title = "Ratatouille ala Remy",
            createdDate = "November 13, 2022",
            creator = "Remy Ratte",
            creatorImg = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRajFS3H5bfZokcLlPh6gBgLj7CAa5UU1z7sQ&usqp=CAU",
            imageSrc =
            "https://stillcracking.com/wp-content/uploads/2016/02/Ratatouille1-550x375.jpg",
            shortDescription =
            "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut",
            cost = "3",
            duration = "45 min",
            difficulty = "Mittel"
        ), Recipe(
            title = "Spaghetti Carbonara",
            createdDate = "November 13, 2022",
            creator = "Ron Weasley",
            imageSrc =
            "https://www.springlane.de/magazin/wp-content/uploads/2016/01/Spaghetti_Carbonara_23727_Featured.jpg",
            shortDescription =
            "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut",
            cost = "1",
            duration = "45 min",
            difficulty = "Mittel"
        ), Recipe(
            title = "Chicken Tikka Masala",
            createdDate = "November 13, 2022",
            creator = "James Sullivan",
            imageSrc =
            "https://image.essen-und-trinken.de/11854764/t/Gb/v8/w960/r1/-/chicken-tikka-masala-c3c77cebac45a391e04fcbcff54bee08-chicken-tikka-masala-jpg--20494-.jpg",
            shortDescription =
            "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut",
            cost = "6",
            duration = "45 min",
            difficulty = "Mittel"
        ), Recipe(
            title = "Ratatouille ala Remy",
            createdDate = "November 13, 2022",
            creator = "Remy Ratte",
            creatorImg = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRajFS3H5bfZokcLlPh6gBgLj7CAa5UU1z7sQ&usqp=CAU",
            imageSrc =
            "https://stillcracking.com/wp-content/uploads/2016/02/Ratatouille1-550x375.jpg",
            shortDescription =
            "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut",
            cost = "10",
            duration = "45 min",
            difficulty = "Mittel"
        ),
        Recipe(
            title = "Flammkuchen",
            createdDate = "November 13, 2022",
            creator = "Mike Glotzkowski",
            creatorImg = "https://i.imgflip.com/2/3e74xu.jpg",
            imageSrc =
            "https://img.chefkoch-cdn.de/rezepte/1112251217261411/bilder/1021874/crop-960x720/einfacher-flammkuchen.jpg",
            shortDescription =
            "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut",
            cost = "15",
            duration = "45 min",
            difficulty = "Mittel"
        ), Recipe(
            title = "Spaghetti Carbonara",
            createdDate = "November 13, 2022",
            creator = "Ron Weasley",
            imageSrc =
            "https://www.springlane.de/magazin/wp-content/uploads/2016/01/Spaghetti_Carbonara_23727_Featured.jpg",
            shortDescription =
            "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut",
            cost = "3",
            duration = "45 min",
            difficulty = "Mittel"
        )
    )

    var tabValue by useState(0)

    val handleChange: (SyntheticEvent<*, *>, Int) -> Unit = { _, newValue ->
        tabValue = newValue
    }

    Box {
        Box {
            Tabs {
                value = tabValue
                onChange = handleChange
                Tab {
                    label = Typography.create { +"Günstig" }
                }
                Tab {
                    label = Typography.create { +"Schnell" }
                }
                Tab {
                    label = Typography.create { +"Beliebt" }
                }
                Tab {
                    label = Typography.create { +"Alle" }
                }

            }
            Divider()
        }
        TabPanel {
            value = tabValue
            index = 0
            CheapTab { recipeList = list.sortedBy { it.title } }
        }
        TabPanel {
            value = tabValue
            index = 1
            FastTab { recipeList = list.sortedBy { it.creator } }
        }
        TabPanel {
            value = tabValue
            index = 2
            PopularTab { recipeList = list.sortedBy { it.cost } }
        }
        TabPanel {
            value = tabValue
            index = 3
            AllTab { recipeList = list }
        }
    }


}