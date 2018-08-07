package com.ahndwon.gethub.ui.fragment.profile

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ahndwon.gethub.R
import com.ahndwon.gethub.api.provideGithubApi
import com.ahndwon.gethub.ui.RepoActivity
import com.ahndwon.gethub.ui.adapter.RepoListAdapter
import com.ahndwon.gethub.utils.CustomApplication
import com.ahndwon.gethub.utils.MyProgressBar
import com.ahndwon.gethub.utils.enqueue
import kotlinx.android.synthetic.main.fragment_repositories.view.*
import kotlinx.android.synthetic.main.item_repo.view.*


class RepositoriesFragment : Fragment() {
    companion object {
        val TAG: String = RepositoriesFragment::class.java.simpleName
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_repositories, container, false)
        val context = activity!!.applicationContext

        val customApplication = activity?.application as CustomApplication
        val colorMap = customApplication.colorMap
        val adapter = RepoListAdapter(colorMap)
        adapter.onClick = { v ->
            val item = view.reposRecyclerView.getChildViewHolder(v)
            val intent = Intent(activity!!.applicationContext, RepoActivity::class.java)
            intent.putExtra("repoName", item.itemView.repoName.text)
            startActivity(intent)
        }
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
}
