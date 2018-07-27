package clover.studio.clovernews.features.trending_headlines.view_models

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import clover.studio.clovernews.commons.rx.SchedulersFacade
import clover.studio.clovernews.features.filter_search.use_cases.GetSelectedFiltersUseCase
import clover.studio.clovernews.features.trending_headlines.use_cases.GetTrendingHeadlinesUseCase
import clover.studio.clovernews.features.trending_headlines.view_states.TrendingHeadlinesActivityViewState
import io.reactivex.disposables.CompositeDisposable

class TrendingHeadlinesActivityViewModelFactory(private val getTrendingHeadlinesUseCase: GetTrendingHeadlinesUseCase,
                                                private val getSelectedFiltersUseCase: GetSelectedFiltersUseCase,
                                                private val schedulersFacade: SchedulersFacade,
                                                private val data: MutableLiveData<TrendingHeadlinesActivityViewState>,
                                                private val disposables: CompositeDisposable) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TrendingHeadlinesActivityViewModel::class.java))
            return TrendingHeadlinesActivityViewModel(
                    getTrendingHeadlinesUseCase,
                    getSelectedFiltersUseCase,
                    schedulersFacade,
                    data,
                    disposables) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}