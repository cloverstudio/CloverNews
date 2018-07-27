package clover.studio.clovernews.features.filter_search.views

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.res.ResourcesCompat
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.WindowManager
import clover.studio.clovernews.R
import clover.studio.clovernews.commons.constants.NONE_SELECTED
import clover.studio.clovernews.features.filter_search.adapters.FilterSearchPagerAdapter
import clover.studio.clovernews.features.filter_search.view_models.FilterSearchActivityViewModel
import clover.studio.clovernews.features.filter_search.view_models.FilterSearchActivityViewModelFactory
import clover.studio.clovernews.features.filter_search.view_states.FilterSearchActivityViewState
import clover.studio.clovernews.features.filter_search.view_states.FilterSearchActivityViewStatus
import co.lujun.androidtagview.TagView
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_filter_search.*
import javax.inject.Inject


fun Context.FilterSearchActivity(): Intent {
    return Intent(this, FilterSearchActivity::class.java)
}

class FilterSearchActivity : AppCompatActivity(), HasSupportFragmentInjector {

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var pagerAdapter: FilterSearchPagerAdapter

    @Inject
    lateinit var factory: FilterSearchActivityViewModelFactory

    lateinit var viewModel: FilterSearchActivityViewModel

    private var cloverGreenColor = 0
    private var cloverBlueColor = 0
    private var color1 = IntArray(0)
    private var color2 = IntArray(0)
    private var colors = ArrayList<IntArray>()

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = fragmentInjector

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter_search)
        setupUI()
        setupOnTagTouchListener()
        setupOnTabChangeListener()
        viewModel = ViewModelProviders.of(this, factory).get(FilterSearchActivityViewModel::class.java)
        viewModel.getLiveData().observe(this, Observer { state -> processViewStateUpdate(state) })
        viewModel.onCreate()

    }

    private fun setupUI() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = this.resources.getString(R.string.filter_search_title)
        toolbar.setTitleTextColor(Color.WHITE)
        cloverGreenColor = ResourcesCompat.getColor(this.resources, R.color.clover_green, null)
        cloverBlueColor = ResourcesCompat.getColor(this.resources, R.color.clover_blue, null)
        color1 = intArrayOf(cloverGreenColor, cloverGreenColor, Color.WHITE)
        color2 = intArrayOf(cloverBlueColor, cloverBlueColor, Color.WHITE)
        colors.add(color1)
        colors.add(color2)
        filterSearchViewPager.adapter = pagerAdapter
        filterSearchTabLayout.setupWithViewPager(filterSearchViewPager)
    }

    private fun setupOnTagTouchListener() {
        tagContainer.setOnTagClickListener(object : TagView.OnTagClickListener {
            override fun onTagLongClick(position: Int, text: String?) {}

            override fun onTagClick(position: Int, text: String?) {
            }

            override fun onTagCrossClick(position: Int) {
                viewModel.onTagTapped(tagContainer.getTagText(position))
            }

        })
    }

    private fun setupOnTabChangeListener() {
        filterSearchViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {
                if (position == 0) {
                    val greenColor = ResourcesCompat.getColor(this@FilterSearchActivity.resources, R.color.clover_green, null)
                    val greenBackground = ColorDrawable(greenColor)
                    tabLayoutContainer.setBackgroundColor(greenColor)
                    toolbar.background = greenBackground
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        val window = window
                        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                        window.statusBarColor = greenColor
                    }

                } else {
                    val blueColor = ResourcesCompat.getColor(this@FilterSearchActivity.resources, R.color.clover_blue, null)
                    val blueBackground = ColorDrawable(blueColor)
                    tabLayoutContainer.setBackgroundColor(blueColor)
                    toolbar.background = blueBackground
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        val window = window
                        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                        window.statusBarColor = blueColor
                    }
                }
            }

        })
    }

    private fun processViewStateUpdate(state: FilterSearchActivityViewState?) {
        state?.let { viewState ->
            when (viewState.status) {

                FilterSearchActivityViewStatus.UPDATETAGVIEWS -> updateTags(viewState.country, viewState.category)
            }
        }
    }

    private fun updateTags(country: String, category: String) {
        if (country == NONE_SELECTED && category == NONE_SELECTED) {
            onNoTagsSelected()
            return
        }
        if (country == NONE_SELECTED) {
            onCountryNotSelected(category)
            return
        }

        if (category == NONE_SELECTED) {
            onCategoryNotSelected(country)
            return
        }

        onCountryAndCategorySelected(country, category)

    }

    private fun onNoTagsSelected() {
        tagContainer.visibility = INVISIBLE
        noTagsSelectedTextView.visibility = VISIBLE
    }

    private fun onCountryNotSelected(category: String) {
        colors.clear()
        colors.add(color2)
        tagContainer.setTags(listOf(category), colors)
        tagContainer.visibility = VISIBLE
        noTagsSelectedTextView.visibility = INVISIBLE
    }

    private fun onCategoryNotSelected(country: String) {
        colors.clear()
        colors.add(color1)
        tagContainer.setTags(listOf(country), colors)
        tagContainer.visibility = VISIBLE
        noTagsSelectedTextView.visibility = INVISIBLE
    }

    private fun onCountryAndCategorySelected(country: String, category: String) {
        colors.clear()
        colors.add(color1)
        colors.add(color2)
        tagContainer.setTags(listOf(country, category), colors)
        tagContainer.visibility = VISIBLE
        noTagsSelectedTextView.visibility = INVISIBLE
    }

}
