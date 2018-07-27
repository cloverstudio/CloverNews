package clover.studio.clovernews.repository

import clover.studio.clovernews.repository.data_persistence.SharedPreferencesHelper
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

class SearchFiltersRepository @Inject constructor(private val sharedPreferencesHelper: SharedPreferencesHelper) {

    private val tagsBehaviorSubject =
            BehaviorSubject.createDefault(Pair(sharedPreferencesHelper.getSelectedCountry(),
                    sharedPreferencesHelper.getSelectedCategory()))


    fun observeTags(): Observable<Pair<String, String>> {
        return tagsBehaviorSubject
    }


    fun setSelectedCategory(category: String): Completable {
        return if (sharedPreferencesHelper.setSelectedCategory(category)) {
            val currentCountry = tagsBehaviorSubject.value.first
            tagsBehaviorSubject.onNext(Pair(currentCountry, category))
            Completable.complete()
        } else Completable.error(Error("category_not_saved"))
    }

    fun setSelectedCountry(country: String): Completable {
        return if (sharedPreferencesHelper.setSelectedCountry(country)) {
            val currentCategory = tagsBehaviorSubject.value.second
            tagsBehaviorSubject.onNext(Pair(country, currentCategory))
            Completable.complete()
        } else Completable.error(Error("country_not_saved"))

    }

}