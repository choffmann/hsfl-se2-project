package com.hsfl.springbreak.frontend.components.recipe

import com.hsfl.springbreak.frontend.client.presentation.state.UiEvent
import com.hsfl.springbreak.frontend.context.UiStateContext
import react.FC
import react.router.useNavigate
import react.useContext

val RecipeCard = FC<RecipeCardItemProps> { props ->
    val uiState = useContext(UiStateContext)
    val navigator = useNavigate()

    if(uiState is UiEvent.ShowLoading) {
        RecipeCardSkeleton()
    } else {
        RecipeCardItem {
            id = props.id
            title = props.title
            createdDate = props.createdDate
            creator = props.creator
            creatorImg = props.creatorImg
            imageSrc = props.imageSrc
            shortDescription = props.shortDescription
            cost = props.cost
            duration = props.duration
            difficulty = props.difficulty
            onClick = {
                navigator("/recipe/$it")
            }
        }
    }
}