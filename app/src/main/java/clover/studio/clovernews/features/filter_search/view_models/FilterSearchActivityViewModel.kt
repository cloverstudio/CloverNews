package clover.studio.clovernews.features.filter_search.view_models

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import clover.studio.clovernews.commons.constants.NONE_SELECTED
import clover.studio.clovernews.commons.rx.SchedulersFacade
import clover.studio.clovernews.features.filter_search.use_cases.GetSelectedFiltersUseCase
import clover.studio.clovernews.features.filter_search.use_cases.SetSelectedCategoryUseCase
import clover.studio.clovernews.features.filter_search.use_cases.SetSelectedCountryUseCase
import clover.studio.clovernews.features.filter_search.view_states.FilterSearchActivityViewState
import io.reactivex.disposables.CompositeDisposable

class FilterSearchActivityViewModel(private val getSelectedFiltersUseCase: GetSelectedFiltersUseCase,
                                    private val setSelectedCountryUseCase: SetSelectedCountryUseCase,
                                    private val setSelectedCategoryUseCase: SetSelectedCategoryUseCase,
                                    private val categories: ArrayList<String>,
                                    private val schedulersFacade: SchedulersFacade,
                                    private val data: MutableLiveData<FilterSearchActivityViewState>,
                                    private val disposables: CompositeDisposable) : ViewModel() {
    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    fun onCreate() {
        disposables.add(getSelectedFiltersUseCase.observeCurrentTags()
                .subscribeOn(schedulersFacade.computation())
                .observeOn(schedulersFacade.ui())
                .subscribe({ tags ->
                    data.value = FilterSearchActivityViewState.updateTagViews(tags.first, tags.second)
                }, {

                }))
    }


    fun onTagTapped(tag: String) {
        if (categories.contains(tag)) {
            setSelectedCategoryUseCase.setSelectedCategory(NONE_SELECTED)
                    .subscribeOn(schedulersFacade.computation())
                    .observeOn(schedulersFacade.ui())
                    .subscribe()
        } else {
            setSelectedCountryUseCase.setSelectedCountry(NONE_SELECTED)
                    .subscribeOn(schedulersFacade.computation())
                    .observeOn(schedulersFacade.ui())
                    .subscribe()
        }
    }

    fun getLiveData(): MutableLiveData<FilterSearchActivityViewState> = data
}