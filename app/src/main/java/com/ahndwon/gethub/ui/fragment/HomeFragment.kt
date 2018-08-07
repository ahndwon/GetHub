package com.ahndwon.gethub.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ahndwon.gethub.R
import com.ahndwon.gethub.api.provideGithubApi
import com.ahndwon.gethub.ui.HomeActivity
import com.ahndwon.gethub.ui.adapter.EventListAdapter
import com.ahndwon.gethub.utils.MyProgressBar
import com.ahndwon.gethub.utils.enqueue
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeFragment : Fragment() {
//    lateinit var listAdapter: EventListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val progressBar = MyProgressBar(activity!!.applicationContext, view.homeFragment)
        progressBar.view.visibility = View.VISIBLE

        val listAdapter = EventListAdapter()
        listAdapter.watchTypes["WatchEvent"] = { item ->
            makeEventString(item.actor.login, "starred", item.repo.name)
        }
        listAdapter.watchTypes["CreateEvent"] = { item ->
            makeEventString(item.actor.login, "created repository", item.repo.name)
        }
        listAdapter.watchTypes["ForkEvent"] = { item ->
            makeEventString(item.actor.login, "forked repository", item.repo.name)
        }
        listAdapter.watchTypes["MemberEvent"] = { item ->
            val actorName = SpannableStringBuilder(item.actor.login)
            val action = SpannableStringBuilder(item.payload.action)
            action.setSpan(android.text.style.StyleSpan(android.graphics.Typeface.BOLD),
                    0, item.payload.action.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            val member = SpannableStringBuilder(item.payload.member.login)
            val repository = SpannableStringBuilder(item.repo.name)
            SpannableStringBuilder().apply {
                append(actorName)
                append(" ")
                append(action)
                append(" ")
                append(member)
                append(" as a collaborator to ")
                append(repository)
            }
        }

        view.homeRecyclerView.adapter = listAdapter
        view.homeRecyclerView.layoutManager = LinearLayoutManager(activity!!.applicationContext)

        val eventsApi = provideGithubApi(activity!!.applicationContext)
        val eventsCall = eventsApi.getEvents()
        eventsCall.enqueue({ response ->
            progressBar.view.visibility = View.GONE
            val statusCode = response.code()
            Log.i(HomeActivity.TAG, statusCode.toString())

            if (statusCode == 200) {
                val result = response.body()
                result?.let {
                    listAdapter.events = it
                    listAdapter.notifyDataSetChanged()
                }
            }
        }, { t ->
            Log.d(HomeActivity.TAG, "localizedMessage" + t.localizedMessage)
        })

        return view
    }

    private fun makeEventString(firstText: String, middleText: String, lastText: String)
            : SpannableStringBuilder {
        val firstString = SpannableStringBuilder(firstText)
        val middleString = SpannableStringBuilder(middleText)
        middleString.setSpan(android.text.style.StyleSpan(android.graphics.Typeface.BOLD),
                0, middleText.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        val lastString = SpannableStringBuilder(lastText)

        firstString.append(" ")
                .append(middleString)
                .append(" ")
                .append(lastString)

        return firstString
    }
}
