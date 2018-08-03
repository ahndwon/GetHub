package com.ahndwon.gethub.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.ahndwon.gethub.api.model.Repo
import com.ahndwon.gethub.ui.viewholder.RepoViewHolder
import kotlinx.android.synthetic.main.item_repo.view.*

class RecentRepoListAdapter : RecyclerView.Adapter<RepoViewHolder>() {
    var repos: List<Repo> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        return RepoViewHolder(parent)
    }

    override fun getItemCount(): Int {
        return if (repos.count() < 12) {
            repos.count()
        } else return 12
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        val item = repos[position]
        if (position < 12) {
            with(holder.itemView) {
                recentRepoName.text = item.name
                repoLanguage.text = item.language
                starCount.text = item.stargazersCount.toString()
            }
        }

    }
}