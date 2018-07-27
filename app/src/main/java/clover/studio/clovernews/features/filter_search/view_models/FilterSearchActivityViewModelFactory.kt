package clover.studio.clovernews.features.filter_search.view_models

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import clover.studio.clovernews.commons.rx.SchedulersFacade
import clover.studio.clovernews.features.filter_search.use_cases.GetSelectedFiltersUseCase
import clover.studio.clovernews.features.filter_search.use_cases.SetSelectedCategoryUseCase
import clover.studio.clovernews.features.filter_search.use_cases.SetSelectedCountryUseCase
import clover.studio.clovernews.features.filter_search.view_states.FilterSearchActivityViewState
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class FilterSearchActivityViewModelFactory
@Inject constructor(private val getSelectedFiltersUseCase: GetSelectedFiltersUseCase,
                    private val setSelectedCountryUseCase: SetSelectedCountryUseCase,
                    private val setSelectedCategoryUseCase: SetSelectedCategoryUseCase,
                    private val categories: ArrayList<String>,
                    private val schedulersFacade: SchedulersFacade,
                    private val data: MutableLiveData<FilterSearchActivityViewState>,
                    private val disposables: CompositeDisposable) : ViewModelProvider.Factory {


    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FilterSearchActivityViewModel::class.java))
            return FilterSearchActivityViewModel(
                    getSelectedFiltersUseCase,
                    setSelectedCountryUseCase,
                    setSelectedCategoryUseCase,
                    categories,
                    schedulersFacade,
                    data,
                    disposables
            ) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}