package com.hsfl.springbreak.frontend.components.notfound

import react.FC
import react.Props
import react.router.useNavigate

val EmptyFavorite = FC<Props> {
    val navigator = useNavigate()
    EmptyState {
        imageSrc = "https://media2.giphy.com/media/RIAOV7vXdehyGB6tIg/giphy.gif?cid=ecf05e47couo14eyidz52qzg6gifs1g4ynmjqtl7zn19jxxv&rid=giphy.gif&ct=g"
        title = "Es ist so leer hier!"
        subtitle = "Suche dir ein leckeres Rezept aus und markiere es als Favorite, damit es hier nicht mehr so leer ist"
        buttonTitle = "Zur Übersicht"
        onButtonClick = {
            navigator("/")
        }
    }
}