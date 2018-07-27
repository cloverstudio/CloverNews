package clover.studio.clovernews.features.trending_headlines.view_states

import clover.studio.clovernews.repository.models.api.NewsHeadline

class TrendingHeadlinesActivityViewState
private constructor(val status: TrendingHeadlinesActivityViewStatus,
                    val headlines: ArrayList<NewsHeadline>? = null,
                    val country: String? = null,
                    val category: String? = null,
                    val error: Throwable? = null) {

    companion object {
        fun loading(): TrendingHeadlinesActivityViewState {
            return TrendingHeadlinesActivityViewState(TrendingHeadlinesActivityViewStatus.LOADING)
        }

        fun success(data: ArrayList<NewsHeadline>): TrendingHeadlinesActivityViewState {
            return TrendingHeadlinesActivityViewState(TrendingHeadlinesActivityViewStatus.SUCCESS, data)
        }

        fun updateTagViews(country: String, category: String): TrendingHeadlinesActivityViewState {
            return TrendingHeadlinesActivityViewState(TrendingHeadlinesActivityViewStatus.UPDATETAGVIEWS, null, country, category)
        }

        fun error(error: Throwable): TrendingHeadlinesActivityViewState {
            return TrendingHeadlinesActivityViewState(TrendingHeadlinesActivityViewStatus.ERROR, null, null, null, error)
        }

    }
}