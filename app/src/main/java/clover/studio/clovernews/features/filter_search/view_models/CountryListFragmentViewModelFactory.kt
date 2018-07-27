package clover.studio.clovernews.features.filter_search.view_models

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import clover.studio.clovernews.commons.rx.SchedulersFacade
import clover.studio.clovernews.features.filter_search.use_cases.SetSelectedCountryUseCase
import clover.studio.clovernews.features.filter_search.view_states.CountryListFragmentViewState
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class CountryListFragmentViewModelFactory
@Inject constructor(private val setSelectedCountryUseCase: SetSelectedCountryUseCase,
                    private val schedulersFacade: SchedulersFacade,
                    private val data: MutableLiveData<CountryListFragmentViewState>,
                    private val disposables: CompositeDisposable) : ViewModelProvider.Factory {


    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CountryListFragmentViewModel::class.java))
            return CountryListFragmentViewModel(
                    setSelectedCountryUseCase,
                    schedulersFacade,
                    data,
                    disposables
            ) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}