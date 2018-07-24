package com.ahndwon.gethub.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ProgressBar
import com.ahndwon.gethub.R
import com.ahndwon.gethub.api.provideEventsApi
import com.ahndwon.gethub.api.provideUserApi
import com.ahndwon.gethub.ui.adapter.EventListAdapter
import com.ahndwon.gethub.utils.enqueue
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeFragment : Fragment() {
    lateinit var listAdapter: EventListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val progressBar = ProgressBar(activity!!.applicationContext, null,
                android.R.attr.progressBarStyleLarge)
        progressBar.indeterminateDrawable.setColorFilter(resources.getColor(R.color.colorAccent),
                android.graphics.PorterDuff.Mode.MULTIPLY)
        val params = FrameLayout.LayoutParams(200, 200)
        params.gravity = Gravity.CENTER
        view.homeFragment.addView(progressBar, params)
        progressBar.visibility = View.VISIBLE

        listAdapter = EventListAdapter()
        view.homeRecyclerView.adapter = listAdapter
        view.homeRecyclerView.layoutManager = LinearLayoutManager(activity!!.applicationContext)

        val eventsApi = provideEventsApi(activity!!.applicationContext)
        val eventsCall = eventsApi.getEvents()
        eventsCall.enqueue({ response ->
            progressBar.visibility = View.GONE
            val statusCode = response.code()
            Log.i(HomeActivity.TAG, statusCode.toString())

            if (statusCode == 200) {
                val result = response.body()
                result?.let {
                    listAdapter.events = it
                    listAdapter.notifyDataSetChanged()
                }
            } else {
//                toast("error - $statusCode")
            }
        }, { t ->
            //            toast(t.localizedMessage)
            Log.d(HomeActivity.TAG, "localizedMessage" + t.localizedMessage)
        })

        return view
    }

}
