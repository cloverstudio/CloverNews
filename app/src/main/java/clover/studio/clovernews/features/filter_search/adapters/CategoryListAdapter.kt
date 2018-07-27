package clover.studio.clovernews.features.filter_search.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import clover.studio.clovernews.R
import clover.studio.clovernews.commons.extensions.inflate
import kotlinx.android.synthetic.main.row_category_list.view.*
import javax.inject.Inject

class CategoryListAdapter @Inject constructor(private var categories: ArrayList<String>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var tapListener: (category: String) -> Unit

    fun updateList(categories: ArrayList<String>) {
        this.categories = ArrayList(categories)
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CategoriesHolder(parent.inflate(R.layout.row_category_list))
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as CategoriesHolder).bindView(categories[position], tapListener)
    }

    class CategoriesHolder(v: View) : RecyclerView.ViewHolder(v) {
        private val view: View = v
        fun bindView(category: String, tapListener: (category: String) -> Unit) {
            view.categoryTextView.text = category
            view.setOnClickListener {
                tapListener.invoke(category)
            }
        }
    }
}