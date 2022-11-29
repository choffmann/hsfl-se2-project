package com.hsfl.springbreak.frontend.components.recipe

import com.hsfl.springbreak.frontend.client.viewmodel.UiEvent
import com.hsfl.springbreak.frontend.context.UiStateContext
import react.FC
import react.useContext

val RecipeCard = FC<RecipeCardItemProps> { props ->
    val uiState = useContext(UiStateContext)

    if(uiState is UiEvent.ShowLoading) {
        RecipeCardSkeleton()
    } else {
        RecipeCardItem {
            title = props.title
            createdDate = props.createdDate
            creator = props.creator
            creatorImg = props.creatorImg
            imageSrc = props.imageSrc
            shortDescription = props.shortDescription
            cost = props.cost
            duration = props.duration
            difficulty = props.difficulty
        }
    }
}