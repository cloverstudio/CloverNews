package clover.studio.clovernews.features.filter_search.use_cases

import clover.studio.clovernews.repository.SearchFiltersRepository
import io.reactivex.Observable
import javax.inject.Inject

class GetSelectedFiltersUseCase @Inject constructor(private val searchFiltersRepository: SearchFiltersRepository) {

    fun observeCurrentTags(): Observable<Pair<String, String>> {
        return searchFiltersRepository.observeTags()
    }

}