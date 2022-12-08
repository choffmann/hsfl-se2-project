package com.hsfl.springbreak.frontend.components.recipe

import com.hsfl.springbreak.frontend.utils.color
import mui.material.*
import react.FC
import react.Props

val ShowMyRecipes = FC<Props> {
    TableContainer {
        Table {
            TableHead {
                TableCell { +"Rezeptname" }
                TableCell { +"Kategorie" }
                TableCell { +"Schwierigkeit" }
                TableCell { +"Aufrufe" }
            }

            TableBody {
                TableCell {
                    colSpan = 4
                    Typography {
                        color = "text.secondary"
                        +"Es sind noch keine Rezepte vorhanden"
                    }
                }
            }
        }
    }
}