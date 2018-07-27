package clover.studio.clovernews.commons.dagger

import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import clover.studio.clovernews.commons.dagger.scopes.PerFragment
import clover.studio.clovernews.commons.rx.SchedulersFacade
import clover.studio.clovernews.features.filter_search.adapters.CountryListAdapter
import clover.studio.clovernews.features.filter_search.use_cases.SetSelectedCountryUseCase
import clover.studio.clovernews.features.filter_search.view_models.CountryListFragmentViewModelFactory
import clover.studio.clovernews.repository.models.Country
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Named

@Module
class CountriesListFragmentModule {


    @Provides
    @PerFragment
    fun providesLinearLayoutManager(context: Context): LinearLayoutManager {
        return LinearLayoutManager(context)
    }

    @Provides
    fun providesCountryListAdapter(@Named("countries") countries: ArrayList<Country>): CountryListAdapter {
        return CountryListAdapter(countries)
    }

    @Provides
    @PerFragment
    fun provideCountryListFragmentViewModelFactroy(setSelectedCountryUseCase: SetSelectedCountryUseCase,
                                                   schedulersFacade: SchedulersFacade): CountryListFragmentViewModelFactory {
        return CountryListFragmentViewModelFactory(setSelectedCountryUseCase, schedulersFacade, MutableLiveData(), CompositeDisposable())
    }
}