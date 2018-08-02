package com.ahndwon.gethub.ui.adapter

import android.support.v7.widget.RecyclerView
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.view.ViewGroup
import com.ahndwon.gethub.R
import com.ahndwon.gethub.api.model.Event
import com.ahndwon.gethub.api.model.Repo
import com.ahndwon.gethub.ui.viewholder.HomeViewHolder
import com.ahndwon.gethub.ui.viewholder.RepoViewHolder
import com.ahndwon.gethub.utils.GlideApp
import kotlinx.android.synthetic.main.item_home.view.*
import kotlinx.android.synthetic.main.item_repo.view.*
import java.text.SimpleDateFormat
import java.util.*

class RecentRepoListAdapter : RecyclerView.Adapter<RepoViewHolder>() {
    var repos: List<Repo> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        return RepoViewHolder(parent)
    }

    override fun getItemCount(): Int {
        return repos.count()
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        val item = repos[position]

        with(holder.itemView) {
            recentRepoName.text = item.name
            repoLanguage.text = item.language
            starCount.text = item.stargazersCount.toString()
        }
    }
}