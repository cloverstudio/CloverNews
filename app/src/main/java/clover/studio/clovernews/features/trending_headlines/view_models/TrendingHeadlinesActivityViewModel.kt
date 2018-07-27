package clover.studio.clovernews.features.trending_headlines.view_models

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import clover.studio.clovernews.commons.constants.TIMEOUT_MILLIS
import clover.studio.clovernews.commons.rx.SchedulersFacade
import clover.studio.clovernews.features.filter_search.use_cases.GetSelectedFiltersUseCase
import clover.studio.clovernews.features.trending_headlines.use_cases.GetTrendingHeadlinesUseCase
import clover.studio.clovernews.features.trending_headlines.view_states.TrendingHeadlinesActivityViewState
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.TimeUnit

class TrendingHeadlinesActivityViewModel(private val getTrendingHeadlinesUseCase: GetTrendingHeadlinesUseCase,
                                         private val getSelectedFiltersUseCase: GetSelectedFiltersUseCase,
                                         private val schedulersFacade: SchedulersFacade,
                                         private val data: MutableLiveData<TrendingHeadlinesActivityViewState>,
                                         private val disposables: CompositeDisposable) : ViewModel() {
    override fun onCleared() {
        disposables.clear()
    }

    fun onCreate() {
        getTrendingHeadlines("")
    }

    fun onResume() {
        disposables.add(getSelectedFiltersUseCase.observeCurrentTags()
                .subscribeOn(schedulersFacade.computation())
                .observeOn(schedulersFacade.ui())
                .subscribe({ tags ->
                    data.value = TrendingHeadlinesActivityViewState.updateTagViews(tags.first, tags.second)
                }, { error ->
                    data.value = TrendingHeadlinesActivityViewState.error(error)
                }))
    }

    fun onPause() {
        disposables.clear()
    }

    fun getLiveData(): MutableLiveData<TrendingHeadlinesActivityViewState> = data

    fun getTrendingHeadlines(search: String) {
        val searchText = if (search.length < 3) null else search
        disposables.add(getTrendingHeadlinesUseCase.getTrendingHeadlines(searchText)
                .timeout(TIMEOUT_MILLIS, TimeUnit.MILLISECONDS, schedulersFacade.io())
                .subscribeOn(schedulersFacade.io())
                .observeOn(schedulersFacade.ui())
                .doOnSubscribe { data.value = TrendingHeadlinesActivityViewState.loading() }
                .subscribe({ headlines ->
                    data.value = TrendingHeadlinesActivityViewState.success(headlines)
                }, { error ->
                    data.value = TrendingHeadlinesActivityViewState.error(error)

                })
        )
    }

}