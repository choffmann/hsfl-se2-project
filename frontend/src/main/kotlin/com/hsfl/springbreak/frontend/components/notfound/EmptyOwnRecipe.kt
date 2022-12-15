package com.hsfl.springbreak.frontend.components.notfound

import react.FC
import react.Props
import react.router.useNavigate

val EmptyOwnRecipe = FC<Props> {
    val navigator = useNavigate()
    EmptyState {
        imageSrc = "https://media0.giphy.com/media/uuBjwGAzSwn6yv5hl6/giphy.gif?cid=ecf05e47fvbhbvfrtofz2forl2pgbeje2zkj47fmzst7axzc&rid=giphy.gif&ct=g"
        title = "Du hast (noch) keine Rezepte!"
        subtitle = "Lege ein Rezept an um anderen zeige zu zeigen, was für ein Chefkoch du bist"
        buttonTitle = "Erstelle dein erstes Rezept"
        onButtonClick = {
            navigator("/create-recipe")
        }
    }
}