package com.ahndwon.gethub.ui.fragment.profile

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.PictureDrawable
import android.net.Uri
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Half
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ahndwon.gethub.R
import com.ahndwon.gethub.api.provideGithubApi
import com.ahndwon.gethub.ui.adapter.RecentRepoListAdapter
import com.ahndwon.gethub.utils.*
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.RequestOptions
import jp.wasabeef.glide.transformations.CropTransformation
import kotlinx.android.synthetic.main.fragment_over_view.*
import kotlinx.android.synthetic.main.fragment_over_view.view.*
import java.nio.charset.Charset
import java.security.MessageDigest


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

        val adapter = RecentRepoListAdapter()
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
}
