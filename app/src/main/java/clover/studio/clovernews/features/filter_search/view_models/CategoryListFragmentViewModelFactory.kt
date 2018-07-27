package clover.studio.clovernews.features.filter_search.view_models

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import clover.studio.clovernews.commons.rx.SchedulersFacade
import clover.studio.clovernews.features.filter_search.use_cases.SetSelectedCategoryUseCase
import clover.studio.clovernews.features.filter_search.view_states.CategoryListFragmentViewState
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class CategoryListFragmentViewModelFactory
@Inject constructor(private val setSelectedCategoryUseCase: SetSelectedCategoryUseCase,
                    private val schedulersFacade: SchedulersFacade,
                    private val data: MutableLiveData<CategoryListFragmentViewState>,
                    private val disposables: CompositeDisposable) : ViewModelProvider.Factory {


    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CategoryListFragmentViewModel::class.java))
            return CategoryListFragmentViewModel(
                    setSelectedCategoryUseCase,
                    schedulersFacade,
                    data,
                    disposables
            ) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}