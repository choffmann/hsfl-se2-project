package com.hsfl.springbreak.frontend.components.recipe

import csstype.px
import mui.material.*
import mui.system.sx
import react.FC
import react.Props
import react.create

var RecipeCardSkeleton = FC<Props> {
    Card {
        sx { maxWidth = recipeCardWidth.px }
        CardHeader {
            avatar = Skeleton.create {
                variant = SkeletonVariant.circular
                Avatar()
            }
            title =  Skeleton.create {
                variant = SkeletonVariant.text
                Typography {+"xxxxxxxxxxxxxxxxxxxxxxxx"}
            }
            subheader = Skeleton.create {
                variant = SkeletonVariant.text
                Typography {+"xxxxxxxxxxxxxxxxx"}
            }
        }
        CardActionArea {
            Skeleton {
                variant = SkeletonVariant.rectangular
                sx { height = 250.px }
            }
        }
        CardContent {
            Skeleton {
                variant = SkeletonVariant.text
            }
            Skeleton {
                variant = SkeletonVariant.text
            }
        }
    }
}