package clover.studio.clovernews.features.trending_headlines.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import clover.studio.clovernews.R
import clover.studio.clovernews.commons.extensions.inflate
import clover.studio.clovernews.repository.models.api.NewsHeadline
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row_headlines.view.*
import javax.inject.Inject

class TrendingHeadlinesRecyclerViewAdapter @Inject constructor(private var headlines: ArrayList<NewsHeadline>,
                                                               private val picasso: Picasso) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var tapListener: (url: String, title: String) -> Unit

    fun updateList(headlines: ArrayList<NewsHeadline>) {
        this.headlines = ArrayList(headlines)
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return HeadlinesHolder(parent.inflate(R.layout.row_headlines), picasso)
    }

    override fun getItemCount(): Int {
        return headlines.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as HeadlinesHolder).bindHeadline(headlines[position], tapListener)
    }

    class HeadlinesHolder(v: View, private val picasso: Picasso) : RecyclerView.ViewHolder(v) {
        private val view: View = v
        fun bindHeadline(headline: NewsHeadline, tapListener: (url: String, title: String) -> Unit) {
            view.descriptionTextView.text = headline.description
            view.headlineTitle.text = headline.title
            view.headlineImageView.setImageResource(R.drawable.clover_logo)
            picasso.cancelRequest(view.headlineImageView)
            if (headline.urlToImage != null && headline.urlToImage.isNotEmpty())
                picasso.load(headline.urlToImage)
                        .placeholder(R.drawable.clover_logo)
                        .error(R.drawable.clover_logo)
                        .into(view.headlineImageView)
            view.setOnClickListener {
                tapListener(headline.url, headline.title)
            }
        }
    }
}