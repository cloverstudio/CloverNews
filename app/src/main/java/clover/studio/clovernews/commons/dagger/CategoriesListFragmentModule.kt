package clover.studio.clovernews.commons.dagger

import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import clover.studio.clovernews.R
import clover.studio.clovernews.commons.dagger.scopes.PerFragment
import clover.studio.clovernews.commons.rx.SchedulersFacade
import clover.studio.clovernews.features.filter_search.adapters.CategoryListAdapter
import clover.studio.clovernews.features.filter_search.use_cases.SetSelectedCategoryUseCase
import clover.studio.clovernews.features.filter_search.view_models.CategoryListFragmentViewModelFactory
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@Module
class CategoriesListFragmentModule {

    @Provides
    @PerFragment
    fun providesLinearLayoutManager(context: Context): LinearLayoutManager {
        return LinearLayoutManager(context)
    }

    @Provides
    @PerFragment
    fun providesCategoryListAdapter(context: Context): CategoryListAdapter {
        return CategoryListAdapter(ArrayList(context.resources.getStringArray(R.array.categories).asList()))
    }

    @Provides
    @PerFragment
    fun provideCategoryListFragmentViewModelFactroy(setSelectedCategoryUseCase: SetSelectedCategoryUseCase,
                                                    schedulersFacade: SchedulersFacade): CategoryListFragmentViewModelFactory {
        return CategoryListFragmentViewModelFactory(setSelectedCategoryUseCase, schedulersFacade, MutableLiveData(), CompositeDisposable())
    }
}