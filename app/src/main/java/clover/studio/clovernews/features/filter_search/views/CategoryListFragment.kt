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
import clover.studio.clovernews.features.filter_search.adapters.CategoryListAdapter
import clover.studio.clovernews.features.filter_search.view_models.CategoryListFragmentViewModel
import clover.studio.clovernews.features.filter_search.view_models.CategoryListFragmentViewModelFactory
import clover.studio.clovernews.features.filter_search.view_states.CategoryListFragmentViewState
import clover.studio.clovernews.features.filter_search.view_states.CategoryListFragmentViewStatus
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_category_list.*
import javax.inject.Inject


class CategoryListFragment : Fragment() {
    @Inject
    lateinit var layoutManager: LinearLayoutManager

    @Inject
    lateinit var adapter: CategoryListAdapter

    @Inject
    lateinit var factory: CategoryListFragmentViewModelFactory

    private lateinit var viewModel: CategoryListFragmentViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_category_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        categoriesRecyclerView.layoutManager = layoutManager
        categoriesRecyclerView.adapter = adapter
        adapter.tapListener = { category ->
            viewModel.setSelectedCategory(category)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        categoriesRecyclerView.layoutManager = null
        categoriesRecyclerView.adapter = null

    }


    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, factory).get(CategoryListFragmentViewModel::class.java)
        viewModel.getLiveData().observe(this, Observer { state -> onViewStateUpdate(state) })
    }


    private fun onViewStateUpdate(state: CategoryListFragmentViewState?) {
        state?.let { viewState ->
            when (viewState.status) {

                CategoryListFragmentViewStatus.SUCCESS -> onSuccess(viewState.categories)
                CategoryListFragmentViewStatus.ERROR -> onError(viewState.error)
            }
        }
    }

    private fun onSuccess(categories: ArrayList<String>?) {
        categories?.let {
            adapter.updateList(it)
        }
    }

    private fun onError(error: Throwable?) {

    }
}