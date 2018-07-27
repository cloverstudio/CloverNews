package clover.studio.clovernews.commons.dagger

import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.support.v4.app.FragmentManager
import clover.studio.clovernews.R
import clover.studio.clovernews.commons.dagger.scopes.PerActivity
import clover.studio.clovernews.commons.rx.SchedulersFacade
import clover.studio.clovernews.features.filter_search.adapters.FilterSearchPagerAdapter
import clover.studio.clovernews.features.filter_search.use_cases.GetSelectedFiltersUseCase
import clover.studio.clovernews.features.filter_search.use_cases.SetSelectedCategoryUseCase
import clover.studio.clovernews.features.filter_search.use_cases.SetSelectedCountryUseCase
import clover.studio.clovernews.features.filter_search.view_models.FilterSearchActivityViewModelFactory
import clover.studio.clovernews.features.filter_search.views.CategoryListFragment
import clover.studio.clovernews.features.filter_search.views.CountryListFragment
import clover.studio.clovernews.features.filter_search.views.FilterSearchActivity
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Named

@Module
class FilterSearchActivityModule {
    @Provides
    @PerActivity
    fun provideCountryListFragment(): CountryListFragment {
        return CountryListFragment()
    }

    @Provides
    @PerActivity
    fun provideCategoryListFragment(): CategoryListFragment {
        return CategoryListFragment()
    }

    @Provides
    @PerActivity
    fun provideFragmentManager(activity: FilterSearchActivity): FragmentManager {
        return activity.supportFragmentManager
    }


    @Provides
    @PerActivity
    fun provideFilterSearchPagerAdapter(fragmentManager: FragmentManager,
                                        context: Context,
                                        countryListFragment: CountryListFragment,
                                        categoryListFragment: CategoryListFragment): FilterSearchPagerAdapter {
        val headerTitles = context.resources.getStringArray(R.array.filter_search_tab_layout_headers)
        return FilterSearchPagerAdapter(fragmentManager, headerTitles, countryListFragment, categoryListFragment)
    }

    @Provides
    @PerActivity
    fun provideFilterSearchActivityViewModelFactroy(getSelectedFiltersUseCase: GetSelectedFiltersUseCase,
                                                    setSelectedCountryUseCase: SetSelectedCountryUseCase,
                                                    setSelectedCategoryUseCase: SetSelectedCategoryUseCase,
                                                    @Named("categories") categories: ArrayList<String>,
                                                    schedulersFacade: SchedulersFacade): FilterSearchActivityViewModelFactory {
        return FilterSearchActivityViewModelFactory(getSelectedFiltersUseCase,
                setSelectedCountryUseCase,
                setSelectedCategoryUseCase,
                categories,
                schedulersFacade,
                MutableLiveData(),
                CompositeDisposable())
    }
}