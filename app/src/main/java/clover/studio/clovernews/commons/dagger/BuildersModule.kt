package clover.studio.clovernews.commons.dagger

import clover.studio.clovernews.commons.dagger.scopes.PerActivity
import clover.studio.clovernews.features.filter_search.views.FilterSearchActivity
import clover.studio.clovernews.features.trending_headlines.views.TrendingHeadlinesActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BuildersModule {
    @PerActivity
    @ContributesAndroidInjector(modules = arrayOf(TrendingHeadlinesActivityModule::class))
    abstract fun bindTrendingHeadlinesActivity(): TrendingHeadlinesActivity

    @PerActivity
    @ContributesAndroidInjector(modules = arrayOf(FilterSearchActivityModule::class,
            CountriesListFragmentFactory::class,
            CategoriesListFragmentFactory::class))
    abstract fun bindFilterSearchActivity(): FilterSearchActivity

}