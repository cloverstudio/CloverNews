package clover.studio.clovernews.features.filter_search.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import clover.studio.clovernews.R
import clover.studio.clovernews.commons.extensions.inflate
import clover.studio.clovernews.repository.models.Country
import kotlinx.android.synthetic.main.row_country_list.view.*
import javax.inject.Inject

class CountryListAdapter @Inject constructor(private var countries: ArrayList<Country>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var tapListener: (country: Country) -> Unit

    fun updateList(countries: ArrayList<Country>) {
        this.countries = ArrayList(countries)
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CountriesHolder(parent.inflate(R.layout.row_country_list))
    }

    override fun getItemCount(): Int {
        return countries.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as CountriesHolder).bindView(countries[position], tapListener)
    }

    class CountriesHolder(v: View) : RecyclerView.ViewHolder(v) {
        private val view: View = v
        fun bindView(country: Country, tapListener: (country: Country) -> Unit) {
            country.image?.let {
                view.countryFlag.setImageDrawable(it)
            }
            view.countryTextView.text = country.countryName
            view.setOnClickListener {
                tapListener.invoke(country)
            }
        }
    }
}