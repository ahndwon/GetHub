package com.ahndwon.gethub.ui.fragment.repository.code

import android.graphics.Color
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ahndwon.gethub.R
import com.ahndwon.gethub.api.provideGithubApi
import com.ahndwon.gethub.utils.CustomApplication
import com.ahndwon.gethub.utils.DownloadFileFromURL
import com.ahndwon.gethub.utils.enqueue
import com.ahndwon.gethub.utils.getSimpleDate
import kotlinx.android.synthetic.main.fragment_code_info.*
import java.io.File
import java.net.URI
import java.text.SimpleDateFormat
import java.util.*


class CodeInfoFragment : Fragment() {
    companion object {
        val TAG: String = CodeInfoFragment::class.java.simpleName
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_code_info, container, false)

        val repoName = arguments?.get("repoName").toString()
        val repoOwner = arguments?.get("repoOwner").toString()
        val userId = PreferenceManager.getDefaultSharedPreferences(activity!!.applicationContext)
                .getString("user_login_id", null)

        val githubApi = provideGithubApi(activity!!.applicationContext)
        val repoCall = githubApi.getRepo(repoOwner, repoName)
        repoCall.enqueue ({ response ->
            val statusCode = response.code()
            if (statusCode == 200) {
                val result = response.body()
                result?.let {
                    val dateFormat = SimpleDateFormat("MMM d YYYY HH:mm", Locale.getDefault())
                    val repoNameText = "${result.owner.login}/${result.name}"
                    val createdDate = "Created at ${getSimpleDate(result.createdAt, dateFormat)}"
                    val updatedDate = "Updated at ${getSimpleDate(result.updatedAt, dateFormat)}"

                    repoInfoRepoName.text = repoNameText
                    repoInfoCreatedDate.text = createdDate
                    repoInfoUpdatedDate.text = updatedDate

                    repoInfoWatchTextView.text = result.watchersCount.toString()
                    repoInfoStarTextView.text = result.stargazersCount.toString()
                    repoInfoStarTextView.text = result.forksCount.toString()

                    val color = (activity?.application as CustomApplication)
                            .colorMap[result.language]?.color
                    repoInfoLangColor.setBackgroundColor(Color.parseColor(color))
                    repoInfoLanguage.text = result.language
                }
            }
        }, {

        })

        val readMeCall = githubApi.getReadMe(userId, repoName)
        readMeCall.enqueue({ response ->
            val statusCode = response.code()
            if (statusCode == 200) {
                val result = response.body()
                result?.let {
                    repoReadMe.loadMarkdownFile(result.downloadUrl)
                }
            }
        }, {

        })

        return view
    }
}
