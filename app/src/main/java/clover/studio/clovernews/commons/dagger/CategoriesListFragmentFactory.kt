package clover.studio.clovernews.commons.dagger

import clover.studio.clovernews.commons.dagger.scopes.PerFragment
import clover.studio.clovernews.features.filter_search.views.CategoryListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class CategoriesListFragmentFactory {
    @PerFragment
    @ContributesAndroidInjector(modules = arrayOf(CategoriesListFragmentModule::class))
    abstract fun bindCategoryListFragment(): CategoryListFragment
}