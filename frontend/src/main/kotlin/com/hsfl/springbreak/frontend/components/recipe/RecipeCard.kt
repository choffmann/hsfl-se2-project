package com.hsfl.springbreak.frontend.components.recipe

import com.hsfl.springbreak.frontend.client.viewmodel.UiEvent
import com.hsfl.springbreak.frontend.context.UiStateContext
import react.FC
import react.Props
import react.useContext

val RecipeCard = FC<Props> {
    val uiState = useContext(UiStateContext)

    if(uiState is UiEvent.ShowLoading) {
        RecipeCardSkeleton()
    } else {
        RecipeCardItem {
            title = "Chicken Tikka Masala"
            createdDate = "November 13, 2022"
            creator = "James Sullivan"
            imageSrc = "https://image.essen-und-trinken.de/11854764/t/Gb/v8/w960/r1/-/chicken-tikka-masala-c3c77cebac45a391e04fcbcff54bee08-chicken-tikka-masala-jpg--20494-.jpg"
            shortDescription = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut"
            cost = "5"
            duration = "45 min"
            difficulty = "Mittel"
        }
    }
}