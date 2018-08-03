package com.ahndwon.gethub.ui.adapter

import android.support.v7.widget.RecyclerView
import android.text.SpannableStringBuilder
import android.view.ViewGroup
import com.ahndwon.gethub.R
import com.ahndwon.gethub.api.model.Event
import com.ahndwon.gethub.ui.viewholder.HomeViewHolder
import com.ahndwon.gethub.utils.GlideApp
import kotlinx.android.synthetic.main.item_home.view.*
import java.text.SimpleDateFormat
import java.util.*

class EventListAdapter : RecyclerView.Adapter<HomeViewHolder>() {
    var events: List<Event> = emptyList()
    var watchTypes: HashMap<String, (Event) -> SpannableStringBuilder> = HashMap()

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
            itemCreatedAt.text = getSimpleDate(item.createdAt)
            itemEventContent.text = defineEvent(item)
//            itemIcon.setImageDrawable(getIconImage(item.watchType))
        }
    }

//    private fun getIconImage(watchType: String): Drawable? {
//        when (watchType) {
//            "WatchEvent" -> return getDrawable(Resources.getSystem(),
//                    R.drawable.ic_star, theme)
//            "CreateEvent" -> return getDrawable(Resources.getSystem(),
//                    R.drawable.ic_repo, Resources.getSystem().newTheme())
//            "ForkEvent" -> return getDrawable(Resources.getSystem(),
//                    R.drawable.ic_repo_forked, Resources.getSystem().newTheme())
//            "MemberEvent" -> return getDrawable(Resources.getSystem(),
//                    R.drawable.ic_person, null)
//            else -> {
//                IllegalStateException("not defined event")
//                return null
//            }
//        }
//    }

    private fun getSimpleDate(date: String): String {
        val timeZone = TimeZone.getTimeZone("Africa/Casablanca")
        val splitDate = date.replace("Z", ".000" + timeZone.displayName)
        println(splitDate)
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.getDefault())
        val parsedDate = dateFormat.parse(splitDate)
        val simpleDate = SimpleDateFormat("EEE, MMM d, HH:mm", Locale.getDefault())
        return simpleDate.format(parsedDate)
    }

    private fun defineEvent(item: Event): CharSequence {
        if (!watchTypes.containsKey(item.watchType)) {
            return item.watchType
        } else return watchTypes[item.watchType]!!.invoke(item)
    }
}