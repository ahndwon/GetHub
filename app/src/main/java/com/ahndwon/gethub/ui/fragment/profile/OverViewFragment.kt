package com.ahndwon.gethub.ui.fragment.profile

import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ahndwon.gethub.R
import com.ahndwon.gethub.api.provideGithubApi
import com.ahndwon.gethub.model.LangColor
import com.ahndwon.gethub.ui.adapter.RecentRepoListAdapter
import com.ahndwon.gethub.utils.GlideApp
import com.ahndwon.gethub.utils.MyProgressBar
import com.ahndwon.gethub.utils.SvgSoftwareLayerSetter
import com.ahndwon.gethub.utils.enqueue
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_over_view.view.*
import java.io.InputStreamReader


class OverViewFragment : Fragment() {
    companion object {
        val TAG = OverViewFragment::class.java.simpleName!!
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_over_view, container, false)

        val myProgressBar = MyProgressBar(activity!!.applicationContext, view.recentReposContainer)
        myProgressBar.view.visibility = View.VISIBLE

        val userId = PreferenceManager.getDefaultSharedPreferences(context)
                .getString("user_login_id", null)

        val uri = Uri.parse("https://ghchart.rshah.org/$userId")

        val requestBuilder = GlideApp.with(this)
                .`as`(Bitmap::class.java)
                .placeholder(R.drawable.ic_github)
                .error(R.drawable.ic_issue_opened)
                .listener(SvgSoftwareLayerSetter())

        requestBuilder.load(uri)
                .fitCenter()
                .into(view.chart)

        val adapter = RecentRepoListAdapter(getLangColorMap())
        view.recentReposRecyclerView.adapter = adapter
        view.recentReposRecyclerView.layoutManager = LinearLayoutManager(activity!!.applicationContext)

        val maxRecentRepos = 8

        val repoApi = provideGithubApi(activity!!.applicationContext)
        val repoCall = repoApi.getUserRepos("updated")
        repoCall.enqueue({ response ->
            myProgressBar.view.visibility = View.GONE
            val statusCode = response.code()
            if (statusCode == 200) {
                val result = response.body()
                result?.let {
                    adapter.repos = it.subList(0, maxRecentRepos)
                    adapter.notifyDataSetChanged()
                }
            }
        }, {

        })
//        Log.d(TAG, "img type : ${requestBuilder.load(uri)}")
        return view
    }

    private fun getLangColorMap() : Map<String, LangColor> {
        val source = resources.assets.open("LanguageColor.json")
        val gson = Gson()
        val reader = InputStreamReader(source)
        return gson.fromJson(reader, object : TypeToken<Map<String, LangColor>>() {}.type)
    }
}
