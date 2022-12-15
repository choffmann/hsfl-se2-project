package com.hsfl.springbreak.frontend.components.recipe

import react.FC
import react.router.useNavigate

val RecipeCard = FC<RecipeCardItemProps> { props ->
    val navigator = useNavigate()
    RecipeCardItem {
        isMyRecipe = props.isMyRecipe
        isFavorite = props.isFavorite
        onFavoriteClick = props.onFavoriteClick
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