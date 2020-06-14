package com.rcorchero.app.features.airingtoday

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rcorchero.app.R
import com.rcorchero.app.core.extensions.inflate
import com.rcorchero.app.core.extensions.loadFromUrl
import com.rcorchero.app.core.extensions.visible
import com.rcorchero.presentation.model.TVShowView
import kotlinx.android.synthetic.main.item_tv_show.view.*
import javax.inject.Inject
import kotlin.properties.Delegates

class AiringTodayAdapter
@Inject constructor() : RecyclerView.Adapter<AiringTodayAdapter.ViewHolder>() {

    internal var collection: List<TVShowView> by Delegates.observable(emptyList()) { _, _, _ ->
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(parent.inflate(R.layout.item_tv_show))

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) =
        viewHolder.bind(collection[position])

    override fun getItemCount() = collection.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(tvShowView: TVShowView) {
            showTitle(tvShowView.name)
            showPoster(tvShowView.posterUrl)
            showVoteAverage(tvShowView.voteAverage)
        }

        private fun showTitle(name: String) {
            itemView.tvName.text = name
        }

        private fun showVoteAverage(voteAverage: String) {
            if (voteAverage.isNotEmpty()) {
                itemView.tvVoteAverage.visible()
                itemView.tvVoteAverage.text = voteAverage
            }
        }

        private fun showPoster(posterUrl: String) {
            itemView.ivPoster.loadFromUrl(posterUrl, R.drawable.ic_launcher_foreground)
        }
    }
}