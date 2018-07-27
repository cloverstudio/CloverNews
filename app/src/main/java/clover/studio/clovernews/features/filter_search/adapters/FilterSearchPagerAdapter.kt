package clover.studio.clovernews.features.filter_search.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import clover.studio.clovernews.features.filter_search.views.CategoryListFragment
import clover.studio.clovernews.features.filter_search.views.CountryListFragment
import javax.inject.Inject

class FilterSearchPagerAdapter
@Inject constructor(fragmentManager: FragmentManager,
                    private val headerTitles: Array<String>,
                    private val countryListFragment: CountryListFragment,
                    private val categoryListFragment: CategoryListFragment)
    : FragmentStatePagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        return if (position == 0) countryListFragment else categoryListFragment
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return if (position == 0) headerTitles[0] else
            headerTitles[1]
    }
}