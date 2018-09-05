package com.ahndwon.gethub.ui.fragment.profile

import android.content.Intent
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
import com.ahndwon.gethub.ui.RepoActivity
import com.ahndwon.gethub.ui.adapter.RecentRepoListAdapter
import com.ahndwon.gethub.utils.*
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.android.synthetic.main.fragment_over_view.view.*
import kotlinx.android.synthetic.main.item_recent_repo.view.*

class OverViewFragment : Fragment() {
    companion object {
        val TAG: String = OverViewFragment::class.java.simpleName
        const val MAX_RECENT_REPOS = 8
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_over_view, container, false)


        val myProgressBar = MyProgressBar(requireContext(), view.recentReposContainer)
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
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .fitCenter()
                .into(view.chart)
        val customApplication = activity?.application as CustomApplication
        val colorMap = customApplication.colorMap
        val adapter = RecentRepoListAdapter(colorMap)


        view.recentReposRecyclerView.adapter = adapter
        view.recentReposRecyclerView.layoutManager =
                LinearLayoutManager(activity!!.applicationContext)

        adapter.onClick = { v ->
            val position = view.recentReposRecyclerView.getChildAdapterPosition(v)
            val repo = adapter.repos[position]
//            val item = view.recentReposRecyclerView.getChildViewHolder(v)
            val intent = Intent(requireContext(), RepoActivity::class.java)
            intent.putExtra("repoOwner", repo.owner.login)
            intent.putExtra("repoName", repo.name)
            startActivity(intent)
        }

        val repoApi = provideGithubApi(requireContext())
        val repoCall = repoApi.getUserRepos("updated")
        repoCall.enqueue({ response ->
            myProgressBar.view.visibility = View.GONE
            val statusCode = response.code()
            if (statusCode == 200) {
                val result = response.body()
                result?.let {
                    adapter.repos = it.subList(0, MAX_RECENT_REPOS)
                    adapter.notifyDataSetChanged()
                }
            }
        }, {

        })

        return view
    }
}
