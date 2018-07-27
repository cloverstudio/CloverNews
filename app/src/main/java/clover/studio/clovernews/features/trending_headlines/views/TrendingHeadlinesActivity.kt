package clover.studio.clovernews.features.trending_headlines.views

import android.arch.lifecycle.Observer
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.View.*
import android.view.inputmethod.InputMethodManager
import clover.studio.clovernews.R
import clover.studio.clovernews.commons.constants.NONE_SELECTED
import clover.studio.clovernews.commons.rx.SchedulersFacade
import clover.studio.clovernews.features.filter_search.views.FilterSearchActivity
import clover.studio.clovernews.features.headline.views.HeadlineActivity
import clover.studio.clovernews.features.trending_headlines.adapters.TrendingHeadlinesRecyclerViewAdapter
import clover.studio.clovernews.features.trending_headlines.view_models.TrendingHeadlinesActivityViewModel
import clover.studio.clovernews.features.trending_headlines.view_states.TrendingHeadlinesActivityViewState
import clover.studio.clovernews.features.trending_headlines.view_states.TrendingHeadlinesActivityViewStatus
import clover.studio.clovernews.repository.models.api.NewsHeadline
import co.lujun.androidtagview.TagView
import com.jakewharton.rxbinding2.support.v7.widget.RxSearchView
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_trending_headlines.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class TrendingHeadlinesActivity : AppCompatActivity() {

    @Inject
    lateinit var headlinesAdapter: TrendingHeadlinesRecyclerViewAdapter

    @Inject
    lateinit var linearLayoutManager: LinearLayoutManager

    @Inject
    lateinit var viewModel: TrendingHeadlinesActivityViewModel

    @Inject
    lateinit var schedulersFacade: SchedulersFacade

    @Inject
    lateinit var inputManager: InputMethodManager

    private var searchView: SearchView? = null


    private var lastSearchText = ""

    private var cloverGreenColor = 0
    private var cloverBlueColor = 0
    private var color1 = IntArray(0)
    private var color2 = IntArray(0)
    private var colors = ArrayList<IntArray>()

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trending_headlines)
        setupUI()
        setupTapListeners()
        viewModel.getLiveData().observe(this, Observer { state -> onViewStateUpdate(state) })
        viewModel.onCreate()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.trending_headlines_menu, menu)
        searchView = menu?.findItem(R.id.searchView)?.actionView as SearchView
        searchView?.let { view ->
            RxSearchView.queryTextChanges(view)
                    .debounce(500, TimeUnit.MILLISECONDS)
                    .map { text -> text.toString() }
                    .filter { text -> text != lastSearchText }
                    .observeOn(schedulersFacade.ui())
                    .subscribe { text ->
                        lastSearchText = text
                        headlinesAdapter.updateList(ArrayList())
                        viewModel.getTrendingHeadlines(text)
                    }
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.filterIcon) {
            startActivity(FilterSearchActivity())
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        viewModel.onResume()
    }

    override fun onPause() {
        super.onPause()
        viewModel.onPause()
    }

    private fun setupUI() {
        setSupportActionBar(toolbar)
        toolbar.setTitleTextColor(Color.WHITE)
        cloverGreenColor = ResourcesCompat.getColor(this.resources, R.color.clover_green, null)
        cloverBlueColor = ResourcesCompat.getColor(this.resources, R.color.clover_blue, null)
        color1 = intArrayOf(cloverGreenColor, cloverGreenColor, Color.WHITE)
        color2 = intArrayOf(cloverBlueColor, cloverBlueColor, Color.WHITE)
        colors.add(color1)
        colors.add(color2)
        headlinesRecyclerView.adapter = headlinesAdapter
        headlinesRecyclerView.layoutManager = linearLayoutManager
        headlinesRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    inputManager.hideSoftInputFromWindow(headlinesRecyclerView.windowToken, 0)
                    searchView?.clearFocus()
                }
            }

            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!headlinesRecyclerView.canScrollVertically(1)) {
                    //TODO: implement paging if we change search from tending headlines to everything
                }
            }

        })
    }

    private fun setupTapListeners() {

        tagContainer.setOnTagClickListener(object : TagView.OnTagClickListener {
            override fun onTagLongClick(position: Int, text: String?) {
            }

            override fun onTagClick(position: Int, text: String?) {
                startActivity(FilterSearchActivity())
            }

            override fun onTagCrossClick(position: Int) {
            }

        })

        headlinesAdapter.tapListener = { url, title ->
            onTap(url, title)
        }
    }

    private fun onTap(url: String, title: String) {
        startActivity(HeadlineActivity(url, title))
    }

    private fun onViewStateUpdate(viewState: TrendingHeadlinesActivityViewState?) {
        viewState?.let { state ->
            when (state.status) {
                TrendingHeadlinesActivityViewStatus.LOADING -> processLoadingState()
                TrendingHeadlinesActivityViewStatus.ERROR -> processErrorState(state.error)
                TrendingHeadlinesActivityViewStatus.SUCCESS -> processSuccesState(state.headlines)
                TrendingHeadlinesActivityViewStatus.UPDATETAGVIEWS -> processUpdateTagViews(state.country!!, state.category!!)
            }
        }
    }

    private fun processLoadingState() {
        loaderView.visibility = VISIBLE
    }

    private fun processErrorState(error: Throwable?) {
        loaderView.visibility = GONE
        val rootLayout = findViewById<View>(android.R.id.content)
        val snackbar = Snackbar.make(rootLayout, R.string.trending_headlines_error, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.try_again) {
                    viewModel.getTrendingHeadlines(lastSearchText)
                }
        snackbar.show()
    }

    private fun processUpdateTagViews(country: String, category: String) {
        updateTags(country, category)
        viewModel.getTrendingHeadlines(lastSearchText)
    }

    private fun processSuccesState(headlines: ArrayList<NewsHeadline>?) {
        loaderView.visibility = GONE
        headlines?.let { newsHeadlines ->
            headlinesAdapter.updateList(newsHeadlines)
            if (newsHeadlines.size < 1)
                emptySearchTextView.visibility = VISIBLE
            else
                emptySearchTextView.visibility = GONE
            return
        }
        emptySearchTextView.visibility = VISIBLE
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
        tagContainer.visibility = View.VISIBLE
        noTagsSelectedTextView.visibility = GONE
    }

    private fun onCategoryNotSelected(country: String) {
        colors.clear()
        colors.add(color1)
        tagContainer.setTags(listOf(country), colors)
        tagContainer.visibility = View.VISIBLE
        noTagsSelectedTextView.visibility = GONE
    }

    private fun onCountryAndCategorySelected(country: String, category: String) {
        colors.clear()
        colors.add(color1)
        colors.add(color2)
        tagContainer.setTags(listOf(country, category), colors)
        tagContainer.visibility = View.VISIBLE
        noTagsSelectedTextView.visibility = GONE
    }
}
