package clover.studio.clovernews.features.filter_search.view_states

class FilterSearchActivityViewState
private constructor(val status: FilterSearchActivityViewStatus,
                    val country: String,
                    val category: String) {

    companion object {

        fun updateTagViews(country: String, category: String): FilterSearchActivityViewState {
            return FilterSearchActivityViewState(FilterSearchActivityViewStatus.UPDATETAGVIEWS, country, category)
        }
    }
}