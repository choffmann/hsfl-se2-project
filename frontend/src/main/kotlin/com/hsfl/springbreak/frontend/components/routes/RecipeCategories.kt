package com.hsfl.springbreak.frontend.components.routes

import com.hsfl.springbreak.frontend.client.presentation.state.UiEvent
import com.hsfl.springbreak.frontend.client.presentation.viewmodel.CategoryListViewModel
import com.hsfl.springbreak.frontend.client.presentation.viewmodel.events.CategoryListEvent
import com.hsfl.springbreak.frontend.client.presentation.viewmodel.events.LifecycleEvent
import com.hsfl.springbreak.frontend.context.UiStateContext
import com.hsfl.springbreak.frontend.di.di
import com.hsfl.springbreak.frontend.utils.collectAsState
import mui.icons.material.ChevronRight
import mui.material.*
import org.kodein.di.instance
import react.FC
import react.Props
import react.router.useNavigate
import react.useContext
import react.useEffect

val RecipeCategories = FC<Props> {
    val viewModel: CategoryListViewModel by di.instance()
    val categoryList = viewModel.categoryList.collectAsState()
    val uiState = useContext(UiStateContext)
    val isLoading = uiState is UiEvent.ShowLoading
    val navigator = useNavigate()

    useEffect(Unit) {
        viewModel.onEvent(LifecycleEvent.OnMount)
        cleanup { viewModel.onEvent(LifecycleEvent.OnUnMount) }
    }
    List {
        if (isLoading) {
            for (i in 0..5) {
                ListItem {
                    ListItemText {
                        Skeleton {
                            variant = SkeletonVariant.text
                        }
                    }
                    ListItemIcon {
                        Skeleton {
                            variant = SkeletonVariant.circular
                        }
                    }
                }
            }
        } else categoryList.map { category ->
            ListItemButton {
                onClick = {
                    //viewModel.onEvent(CategoryListEvent.OnCategoryClick(category.id))
                    navigator("/category/${category.id}")
                }
                ListItemText { +category.name }
                ListItemIcon {
                    ChevronRight()
                }
            }
        }
    }
}