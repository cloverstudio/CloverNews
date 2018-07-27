package clover.studio.clovernews.features.filter_search.views

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import clover.studio.clovernews.R
import clover.studio.clovernews.features.filter_search.adapters.CountryListAdapter
import clover.studio.clovernews.features.filter_search.view_models.CountryListFragmentViewModel
import clover.studio.clovernews.features.filter_search.view_models.CountryListFragmentViewModelFactory
import clover.studio.clovernews.features.filter_search.view_states.CountryListFragmentViewState
import clover.studio.clovernews.features.filter_search.view_states.CountryListFragmentViewStatus
import clover.studio.clovernews.repository.models.Country
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_country_list.*
import javax.inject.Inject

class CountryListFragment : Fragment() {
    @Inject
    lateinit var layoutManager: LinearLayoutManager

    @Inject
    lateinit var adapter: CountryListAdapter

    @Inject
    lateinit var factory: CountryListFragmentViewModelFactory

    private lateinit var viewModel: CountryListFragmentViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_country_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        countriesRecyclerView.layoutManager = layoutManager
        countriesRecyclerView.adapter = adapter
        adapter.tapListener = { country ->
            viewModel.setSelectedCountry(country)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        countriesRecyclerView.layoutManager = null
        countriesRecyclerView.adapter = null

    }


    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, factory).get(CountryListFragmentViewModel::class.java)
        viewModel.getLiveData().observe(this, Observer { state -> onViewStateUpdate(state) })
    }


    private fun onViewStateUpdate(state: CountryListFragmentViewState?) {
        state?.let { viewState ->
            when (viewState.status) {
                CountryListFragmentViewStatus.SUCCESS -> onSuccess(viewState.countries)
                CountryListFragmentViewStatus.ERROR -> onError(viewState.error)
            }
        }
    }

    private fun onSuccess(countries: ArrayList<Country>?) {
        countries?.let {
            adapter.updateList(it)
        }
    }

    private fun onError(error: Throwable?) {

    }
}