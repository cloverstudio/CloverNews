package clover.studio.clovernews.features.filter_search.view_states

import clover.studio.clovernews.repository.models.Country

class CountryListFragmentViewState
private constructor(val status: CountryListFragmentViewStatus,
                    val countries: ArrayList<Country>? = null,
                    val error: Throwable? = null) {

    companion object {

        fun success(countries: ArrayList<Country>): CountryListFragmentViewState {
            return CountryListFragmentViewState(CountryListFragmentViewStatus.SUCCESS, countries)
        }

        fun error(error: Throwable): CountryListFragmentViewState {
            return CountryListFragmentViewState(CountryListFragmentViewStatus.ERROR, null, error)
        }
    }
}