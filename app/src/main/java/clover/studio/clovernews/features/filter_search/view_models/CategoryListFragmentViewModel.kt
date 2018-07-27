package clover.studio.clovernews.features.filter_search.view_models

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import clover.studio.clovernews.commons.rx.SchedulersFacade
import clover.studio.clovernews.features.filter_search.use_cases.SetSelectedCategoryUseCase
import clover.studio.clovernews.features.filter_search.view_states.CategoryListFragmentViewState
import io.reactivex.disposables.CompositeDisposable

class CategoryListFragmentViewModel(private val setSelectedCategoryUseCase: SetSelectedCategoryUseCase,
                                    private val schedulersFacade: SchedulersFacade,
                                    private val data: MutableLiveData<CategoryListFragmentViewState>,
                                    private val disposables: CompositeDisposable) : ViewModel() {
    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }

    fun setSelectedCategory(category: String) {
        disposables.add(setSelectedCategoryUseCase.setSelectedCategory(category)
                .subscribeOn(schedulersFacade.computation())
                .observeOn(schedulersFacade.ui())
                .subscribe({
                }
                ) { error ->
                    data.value = CategoryListFragmentViewState.error(error)
                })
    }

    fun getLiveData(): MutableLiveData<CategoryListFragmentViewState> = data
}