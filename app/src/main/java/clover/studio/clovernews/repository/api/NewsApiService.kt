package clover.studio.clovernews.repository.api

import clover.studio.clovernews.commons.constants.API_KEY
import clover.studio.clovernews.commons.constants.API_KEY_HEADER
import clover.studio.clovernews.commons.constants.BASE_URL
import clover.studio.clovernews.repository.models.api.NewsApiResult
import io.reactivex.Single
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    @GET("top-headlines")
    fun getRecentHeadlines(@Query("q") searchParameter: String?,
                           @Query("country") country: String?,
                           @Query("category") category: String?,
                           @Query("pageSize") pageSize: Int,
                           @Query("page") pageIndex: Int): Single<NewsApiResult>

    companion object {
        fun create(): NewsApiService {

            val client: OkHttpClient.Builder = OkHttpClient.Builder()
            client.addInterceptor { chain ->
                val request = chain.request().newBuilder().addHeader(API_KEY_HEADER, API_KEY).build()
                chain.proceed(request)
            }

            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL)
                    .client(client.build())
                    .build()

            return retrofit.create(NewsApiService::class.java)
        }
    }
}