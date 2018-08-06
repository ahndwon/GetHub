package com.ahndwon.gethub.ui.adapter

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.ViewGroup
import com.ahndwon.gethub.api.dao.Repo
import com.ahndwon.gethub.model.LangColor
import com.ahndwon.gethub.ui.viewholder.RecentRepoViewHolder
import kotlinx.android.synthetic.main.item_recent_repo.view.*

class RecentRepoListAdapter(private val colorMap: Map<String, LangColor>) : RecyclerView.Adapter<RecentRepoViewHolder>() {
    var repos: List<Repo> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentRepoViewHolder {
        return RecentRepoViewHolder(parent)
    }

    override fun getItemCount(): Int {
        return if (repos.count() < 12) {
            repos.count()
        } else return 12
    }

    override fun onBindViewHolder(holderRecent: RecentRepoViewHolder, position: Int) {
        val item = repos[position]
        val color = colorMap[item.language]?.color

        if (position < 12) {
            with(holderRecent.itemView) {
                recentRepoName.text = item.name
                recentRepoLanguage.text = item.language
                starCount.text = item.stargazersCount.toString()
                Log.d("RecentRepoListAdapter",
                        "${item.language} - ${colorMap[item.language]?.color}")
                if (color == null) {
                    recentRepoLangColor.visibility = View.INVISIBLE
                } else {
                    recentRepoLangColor.setBackgroundColor(Color.parseColor(color))
                }
            }
        }
    }
}