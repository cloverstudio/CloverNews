package clover.studio.clovernews.features.trending_headlines.use_cases

import clover.studio.clovernews.repository.NewsHeadlinesRepository
import clover.studio.clovernews.repository.models.api.NewsHeadline
import io.reactivex.Single
import javax.inject.Inject

class GetTrendingHeadlinesUseCase @Inject constructor(private val newsHeadlinesRepository: NewsHeadlinesRepository) {

    fun getTrendingHeadlines(search: String?): Single<ArrayList<NewsHeadline>> {
        return newsHeadlinesRepository.getTrendingHeadlines(search)
    }
}