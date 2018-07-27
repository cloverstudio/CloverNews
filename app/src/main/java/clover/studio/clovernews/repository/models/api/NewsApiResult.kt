package clover.studio.clovernews.repository.models.api

import com.google.gson.annotations.SerializedName


data class NewsApiResult(val status: String,
                         val totalResults: Int,
                         @SerializedName("articles")
                         val headlines: ArrayList<NewsHeadline>)

data class NewsHeadline(val source: Source,
                        val author: String,
                        val title: String,
                        val description: String,
                        val url: String,
                        val urlToImage: String?,
                        val publishedAt: String)

data class Source(val id: String, val name: String)
