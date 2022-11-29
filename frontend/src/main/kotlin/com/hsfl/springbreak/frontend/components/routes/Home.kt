package com.hsfl.springbreak.frontend.components.routes

import com.hsfl.springbreak.frontend.components.recipe.RecipeCard
import csstype.*
import mui.lab.Masonry
import mui.material.*
import mui.system.responsive
import mui.system.sx
import react.FC
import react.Props

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

var Home = FC<Props> {
    val recipeList = listOf(
        Recipe(
            title = "Chicken Tikka Masala",
            createdDate = "November 13, 2022",
            creator = "James Sullivan",
            imageSrc =
            "https://image.essen-und-trinken.de/11854764/t/Gb/v8/w960/r1/-/chicken-tikka-masala-c3c77cebac45a391e04fcbcff54bee08-chicken-tikka-masala-jpg--20494-.jpg",
            shortDescription =
            "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut",
            cost = "5",
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
            cost = "5",
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
            cost = "5",
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
            cost = "5",
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
            title = "Spaghetti Carbonara",
            createdDate = "November 13, 2022",
            creator = "Ron Weasley",
            imageSrc =
            "https://www.springlane.de/magazin/wp-content/uploads/2016/01/Spaghetti_Carbonara_23727_Featured.jpg",
            shortDescription =
            "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut",
            cost = "5",
            duration = "45 min",
            difficulty = "Mittel"
        )
    )

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
            recipeList.forEach {
                RecipeCard {
                    title = it.title
                    createdDate = it.createdDate
                    creator = it.creator
                    imageSrc = it.imageSrc
                    shortDescription = it.shortDescription
                    cost = it.cost
                    duration = it.duration
                    difficulty = it.difficulty
                    creatorImg = it.creatorImg
                }
            }
        }
    }
}