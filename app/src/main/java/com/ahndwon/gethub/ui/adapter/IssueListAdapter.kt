package com.ahndwon.gethub.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.ahndwon.gethub.api.model.Issue
import com.ahndwon.gethub.ui.viewholder.IssueViewHolder


class IssueListAdapter: RecyclerView.Adapter<IssueViewHolder>() {
    var issues: List<Issue> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IssueViewHolder {
        return IssueViewHolder(parent)
    }

    override fun getItemCount(): Int {
        return issues.count()
    }

    override fun onBindViewHolder(holder: IssueViewHolder, position: Int) {
        val item = issues[position]

        with(holder.itemView) {

        }
    }

}