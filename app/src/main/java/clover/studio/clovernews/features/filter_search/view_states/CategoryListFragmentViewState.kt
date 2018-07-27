package clover.studio.clovernews.features.filter_search.view_states

class CategoryListFragmentViewState
private constructor(val status: CategoryListFragmentViewStatus,
                    val categories: ArrayList<String>? = null,
                    val error: Throwable? = null) {

    companion object {

        fun success(categories: ArrayList<String>): CategoryListFragmentViewState {
            return CategoryListFragmentViewState(CategoryListFragmentViewStatus.SUCCESS, categories)
        }

        fun error(error: Throwable): CategoryListFragmentViewState {
            return CategoryListFragmentViewState(CategoryListFragmentViewStatus.ERROR, null, error)
        }
    }
}