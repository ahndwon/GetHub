package com.ahndwon.gethub.ui.adapter

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.ahndwon.gethub.api.model.Repo
import com.ahndwon.gethub.model.LangColor
import com.ahndwon.gethub.ui.viewholder.RepoViewHolder
import com.ahndwon.gethub.utils.getSimpleDate
import kotlinx.android.synthetic.main.item_repo.view.*
import java.text.SimpleDateFormat
import java.util.*

class RepoListAdapter(val colorMap: Map<String, LangColor>) : RecyclerView.Adapter<RepoViewHolder>() {
    var repos : List<Repo> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        return RepoViewHolder(parent)
    }

    override fun getItemCount(): Int {
        return repos.count()
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        val item = repos[position]
        val color = colorMap[item.language]?.color

        with(holder.itemView) {
            repoName.text = item.name
            repoStarCount.text = item.stargazersCount.toString()
            repoForkCount.text = item.forksCount.toString()
            repoTime.text = getSimpleDate(item.updatedAt,
                    SimpleDateFormat("MMM d, HH:mm", Locale.getDefault()))
            repoLang.text = item.language

            color?.let {
                repoLangColor.setBackgroundColor(Color.parseColor(color))
            }
            if (!item.private) repoPrivate.visibility = View.GONE
        }
    }
}