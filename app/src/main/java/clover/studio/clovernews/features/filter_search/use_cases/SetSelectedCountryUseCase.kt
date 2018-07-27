package clover.studio.clovernews.features.filter_search.use_cases

import clover.studio.clovernews.repository.SearchFiltersRepository
import io.reactivex.Completable
import javax.inject.Inject

class SetSelectedCountryUseCase @Inject constructor(private val repository: SearchFiltersRepository) {

    fun setSelectedCountry(country: String): Completable {
        return repository.setSelectedCountry(country)
    }

}