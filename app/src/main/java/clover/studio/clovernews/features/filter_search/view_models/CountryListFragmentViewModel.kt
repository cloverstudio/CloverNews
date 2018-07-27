package clover.studio.clovernews.features.filter_search.view_models

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import clover.studio.clovernews.commons.rx.SchedulersFacade
import clover.studio.clovernews.features.filter_search.use_cases.SetSelectedCountryUseCase
import clover.studio.clovernews.features.filter_search.view_states.CountryListFragmentViewState
import clover.studio.clovernews.repository.models.Country
import io.reactivex.disposables.CompositeDisposable

class CountryListFragmentViewModel(private val setSelectedCountryUseCase: SetSelectedCountryUseCase,
                                   private val schedulersFacade: SchedulersFacade,
                                   private val data: MutableLiveData<CountryListFragmentViewState>,
                                   private val disposables: CompositeDisposable) : ViewModel() {
    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }

    fun setSelectedCountry(country: Country) {
        disposables.add(setSelectedCountryUseCase.setSelectedCountry(country.countryName)
                .subscribeOn(schedulersFacade.computation())
                .observeOn(schedulersFacade.ui())
                .subscribe({
                }
                ) { error ->
                    data.value = CountryListFragmentViewState.error(error)
                })
    }

    fun getLiveData(): MutableLiveData<CountryListFragmentViewState> = data
}