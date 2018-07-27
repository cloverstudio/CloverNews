package clover.studio.clovernews.features.filter_search.use_cases

import clover.studio.clovernews.repository.SearchFiltersRepository
import io.reactivex.Completable
import javax.inject.Inject

class SetSelectedCategoryUseCase @Inject constructor(private val repository: SearchFiltersRepository) {

    fun setSelectedCategory(category: String): Completable {
        return repository.setSelectedCategory(category)
    }

}