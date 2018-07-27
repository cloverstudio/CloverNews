package clover.studio.clovernews.commons.dagger

import clover.studio.clovernews.commons.dagger.scopes.PerFragment
import clover.studio.clovernews.features.filter_search.views.CountryListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class CountriesListFragmentFactory {
    @PerFragment
    @ContributesAndroidInjector(modules = arrayOf(CountriesListFragmentModule::class))
    abstract fun bindCountryListFragment(): CountryListFragment
}