package com.ahndwon.gethub.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.ahndwon.gethub.R
import com.ahndwon.gethub.api.model.Event
import com.ahndwon.gethub.ui.viewholder.HomeViewHolder
import com.ahndwon.gethub.utils.GlideApp
import kotlinx.android.synthetic.main.item_home.view.*

class EventListAdapter : RecyclerView.Adapter<HomeViewHolder>() {
    var events: List<Event> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder(parent)
    }

    override fun getItemCount(): Int {
        return events.count()
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val item = events[position]

        with(holder.itemView) {
            GlideApp.with(this)
                    .load(item.actor.avatarUrl)
                    .placeholder(R.drawable.ic_github)
                    .into(itemAvatar)

            itemUserName.text = item.actor.login
            itemCreatedAt.text = item.createdAt
            itemEventContent.text = defineEvent(item)
        }
    }

    private fun defineEvent(item: Event): CharSequence {
        val itemActor = item.actor.login
        when (item.watchType) {
            "WatchEvent"
            -> return "$itemActor starred ${item.repo.name}"
            "CreateEvent"
            -> return "$itemActor created repository ${item.repo.name}"
            "ForkEvent"
            -> return "$itemActor forked repository ${item.payload.forkee.fullName}"
            else -> return "event not defined"
        }
    }
}