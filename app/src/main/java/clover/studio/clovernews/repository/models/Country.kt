package clover.studio.clovernews.repository.models

import android.graphics.drawable.Drawable

data class Country(val countryCode: String, val countryName: String, val image: Drawable? = null)