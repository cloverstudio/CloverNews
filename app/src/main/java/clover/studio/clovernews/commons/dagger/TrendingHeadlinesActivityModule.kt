package clover.studio.clovernews.commons.dagger

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.widget.LinearLayoutManager
import clover.studio.clovernews.commons.dagger.scopes.PerActivity
import clover.studio.clovernews.commons.rx.SchedulersFacade
import clover.studio.clovernews.features.filter_search.use_cases.GetSelectedFiltersUseCase
import clover.studio.clovernews.features.trending_headlines.adapters.TrendingHeadlinesRecyclerViewAdapter
import clover.studio.clovernews.features.trending_headlines.use_cases.GetTrendingHeadlinesUseCase
import clover.studio.clovernews.features.trending_headlines.view_models.TrendingHeadlinesActivityViewModel
import clover.studio.clovernews.features.trending_headlines.view_models.TrendingHeadlinesActivityViewModelFactory
import clover.studio.clovernews.features.trending_headlines.views.TrendingHeadlinesActivity
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@Module
class TrendingHeadlinesActivityModule {

    @Provides
    @PerActivity
    fun provideLinearLayoutManager(context: TrendingHeadlinesActivity): LinearLayoutManager {
        return LinearLayoutManager(context)
    }

    @Provides
    @PerActivity
    fun provideTrendingHeadlinesRecyclerViewAdapter(picasso: Picasso): TrendingHeadlinesRecyclerViewAdapter {
        return TrendingHeadlinesRecyclerViewAdapter(ArrayList(), picasso)
    }


    @Provides
    @PerActivity
    fun provideTrendingHeadlinesViewModelFactory(getTrendingHeadlinesUseCase: GetTrendingHeadlinesUseCase,
                                                 getSelectedFiltersUseCase: GetSelectedFiltersUseCase,
                                                 schedulersFacade: SchedulersFacade): TrendingHeadlinesActivityViewModelFactory {
        return TrendingHeadlinesActivityViewModelFactory(getTrendingHeadlinesUseCase,
                getSelectedFiltersUseCase,
                schedulersFacade,
                MutableLiveData(),
                CompositeDisposable())
    }

    @Provides
    @PerActivity
    fun provideTrendingHeadlinesViewModel(trendingHeadlinesActivityViewModelFactory: TrendingHeadlinesActivityViewModelFactory,
                                          activity: TrendingHeadlinesActivity): TrendingHeadlinesActivityViewModel {
        return ViewModelProviders.of(activity, trendingHeadlinesActivityViewModelFactory).get(TrendingHeadlinesActivityViewModel::class.java)
    }

}
