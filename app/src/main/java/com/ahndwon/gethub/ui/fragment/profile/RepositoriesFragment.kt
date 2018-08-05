package com.ahndwon.gethub.ui.fragment.profile

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ahndwon.gethub.R
import com.ahndwon.gethub.api.provideGithubApi
import com.ahndwon.gethub.model.LangColor
import com.ahndwon.gethub.ui.adapter.RepoListAdapter
import com.ahndwon.gethub.utils.MyProgressBar
import com.ahndwon.gethub.utils.enqueue
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_repositories.view.*
import java.io.InputStreamReader


class RepositoriesFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_repositories, container, false)
        val context = activity!!.applicationContext
        val adapter = RepoListAdapter(getLangColorMap())
        view.reposRecyclerView.adapter = adapter
        view.reposRecyclerView.layoutManager = LinearLayoutManager(context)

        val myProgressBar = MyProgressBar(context, view.repoContainer)
        myProgressBar.view.visibility = View.VISIBLE

        val repoApi = provideGithubApi(context)
        val repoCall = repoApi.getUserRepos("full_name")
        repoCall.enqueue({ response ->
            val statusCode = response.code()

            if (statusCode == 200) {
                val result = response.body()
                result?.let {
                    myProgressBar.view.visibility = View.GONE
                    adapter.repos = it
                    adapter.notifyDataSetChanged()
                }
            }
        }, {

        })
        return view
    }

    private fun getLangColorMap() : Map<String, LangColor> {
        val source = resources.assets.open("LanguageColor.json")
        val gson = Gson()
        val reader = InputStreamReader(source)
        return gson.fromJson(reader, object : TypeToken<Map<String, LangColor>>() {}.type)
    }
}
