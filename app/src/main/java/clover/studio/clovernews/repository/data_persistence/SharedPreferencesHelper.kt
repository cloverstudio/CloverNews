package clover.studio.clovernews.repository.data_persistence

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import clover.studio.clovernews.commons.constants.SHARED_PREFS_CATEGORY
import clover.studio.clovernews.commons.constants.SHARED_PREFS_COUNTRY
import clover.studio.clovernews.commons.constants.SHARED_PREFS_INITIAL_CATEGORY
import clover.studio.clovernews.commons.constants.SHARED_PREFS_INITIAL_COUNTRY
import javax.inject.Inject

class SharedPreferencesHelper @Inject constructor(context: Context) {

    private val sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)


    fun getSelectedCountry(): String {
        return sharedPreferences.getString(SHARED_PREFS_COUNTRY, SHARED_PREFS_INITIAL_COUNTRY)
    }

    fun setSelectedCountry(countryCode: String): Boolean {
        return sharedPreferences.edit().putString(SHARED_PREFS_COUNTRY, countryCode).commit()
    }

    fun getSelectedCategory(): String {
        return sharedPreferences.getString(SHARED_PREFS_CATEGORY, SHARED_PREFS_INITIAL_CATEGORY)
    }

    fun setSelectedCategory(category: String): Boolean {
        return sharedPreferences.edit().putString(SHARED_PREFS_CATEGORY, category).commit()
    }

}