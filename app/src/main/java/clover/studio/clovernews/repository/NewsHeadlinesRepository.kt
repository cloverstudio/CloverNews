package clover.studio.clovernews.repository

import clover.studio.clovernews.commons.constants.NONE_SELECTED
import clover.studio.clovernews.commons.constants.PAGE_SIZE
import clover.studio.clovernews.repository.api.NewsApiService
import clover.studio.clovernews.repository.data_persistence.SharedPreferencesHelper
import clover.studio.clovernews.repository.models.Country
import clover.studio.clovernews.repository.models.api.NewsHeadline
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Named

class NewsHeadlinesRepository @Inject constructor(private val newsApiService: NewsApiService,
                                                  @Named("countries")
                                                  private val countries: ArrayList<Country>,
                                                  private val sharedPreferencesHelper: SharedPreferencesHelper) {

    fun getTrendingHeadlines(search: String?): Single<ArrayList<NewsHeadline>> {
        var category: String? = sharedPreferencesHelper.getSelectedCategory()
        if (category.equals(NONE_SELECTED)) category = null
        var country: String? = sharedPreferencesHelper.getSelectedCountry()
        country = countries.firstOrNull { it.countryName.equals(country) }?.countryCode

        return newsApiService.getRecentHeadlines(search, country, category, PAGE_SIZE, 0)
                .flatMap { result ->
                    return@flatMap Single.just(ArrayList(result.headlines))
                }
    }

}