package com.hsfl.springbreak.frontend.components.recipe

import com.hsfl.springbreak.frontend.client.presentation.viewmodel.CategoryListViewModel
import com.hsfl.springbreak.frontend.client.presentation.viewmodel.events.CategoryListEvent
import com.hsfl.springbreak.frontend.components.notfound.EmptyCategory
import com.hsfl.springbreak.frontend.di.di
import com.hsfl.springbreak.frontend.utils.collectAsState
import org.kodein.di.instance
import react.FC
import react.Props
import react.useEffect

external interface CategoryDetailListProps : Props {
    var id: Int
}

val CategoryDetailList = FC<CategoryDetailListProps> { props ->
    val viewModel: CategoryListViewModel by di.instance()
    val recipeList = viewModel.recipeList.collectAsState()
    useEffect(Unit) {
        viewModel.onEvent(CategoryListEvent.OnCategoryClick(props.id))
    }
    if (recipeList.isEmpty()) EmptyCategory()
    else RecipeList { list = recipeList }
}