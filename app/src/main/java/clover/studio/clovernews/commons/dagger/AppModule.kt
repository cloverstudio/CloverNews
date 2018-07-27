package clover.studio.clovernews.commons.dagger

import android.app.Activity
import android.content.Context
import android.support.v4.content.res.ResourcesCompat
import android.view.inputmethod.InputMethodManager
import clover.studio.clovernews.R
import clover.studio.clovernews.commons.CloverNewsApp
import clover.studio.clovernews.commons.constants.API_KEY
import clover.studio.clovernews.commons.constants.API_KEY_HEADER
import clover.studio.clovernews.commons.constants.BASE_URL
import clover.studio.clovernews.repository.SearchFiltersRepository
import clover.studio.clovernews.repository.api.NewsApiService
import clover.studio.clovernews.repository.data_persistence.SharedPreferencesHelper
import clover.studio.clovernews.repository.models.Country
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
class AppModule(private val app: CloverNewsApp) {
    @Provides
    @Singleton
    fun provideContext(): Context = app

    @Provides
    @Singleton
    fun provideSharedPreferencesHelper(): SharedPreferencesHelper {
        return SharedPreferencesHelper(context = app)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val client: OkHttpClient.Builder = OkHttpClient.Builder()
        client.addInterceptor { chain ->
            val request = chain.request().newBuilder().addHeader(API_KEY_HEADER, API_KEY).build()
            chain.proceed(request)
        }
        return client.build()
    }

    @Provides
    @Singleton
    fun provideNewsApiService(okHttpClient: OkHttpClient): NewsApiService {
        val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .build()

        return retrofit.create(NewsApiService::class.java)
    }

    @Provides
    @Singleton
    fun providePicasso(): Picasso {
        return Picasso.get()
    }

    @Provides
    @Singleton
    fun provideSearchFiltersRepository(sharedPreferencesHelper: SharedPreferencesHelper): SearchFiltersRepository {
        return SearchFiltersRepository(sharedPreferencesHelper)
    }

    @Provides
    @Singleton
    @Named("countries")
    fun provideCountries(context: Context): ArrayList<Country> {
        val countryCodes = context.resources.getStringArray(R.array.country_codes)
        val countryNames = context.resources.getStringArray(R.array.country_names)
        val countries = ArrayList<Country>()
        countryCodes.mapIndexed { i, code ->
            val image = ResourcesCompat.getDrawable(context.resources,
                    context.resources.getIdentifier("ic_list_country_$code",
                            "drawable", context.packageName), null)
            countries.add(Country(code, countryNames[i], image))
        }
        return ArrayList(countries.sortedBy { it.countryName })
    }

    @Provides
    @Singleton
    @Named("categories")
    fun provideCategories(context: Context): ArrayList<String> {
        return ArrayList(context.resources.getStringArray(R.array.categories).toList())
    }

    @Provides
    fun provideInputMethodManager(): InputMethodManager {
        return app.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    }

}