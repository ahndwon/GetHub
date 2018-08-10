package com.ahndwon.gethub.ui.fragment.repository.code

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ahndwon.gethub.R
import com.ahndwon.gethub.api.dao.Content
import com.ahndwon.gethub.api.provideGithubApi
import com.ahndwon.gethub.ui.RepoActivity
import com.ahndwon.gethub.ui.adapter.FileListAdapter
import com.ahndwon.gethub.utils.enqueue
import kotlinx.android.synthetic.main.fragment_code_files.view.*


class CodeFilesFragment : Fragment() {
    var path: String = ""
    private var clickCount = 0
    private val fileList = ArrayList<List<Content>>()

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_code_files, container, false)

        val repoName = arguments?.getString("repoName").toString()
        val repoOwner = arguments?.getString("repoOwner").toString()

        val adapter = FileListAdapter()
        view.filesRecyclerView.adapter = adapter
        view.filesRecyclerView.layoutManager = LinearLayoutManager(activity!!.applicationContext)

        (activity as RepoActivity).onBackPressed = {
            if (clickCount > 0) {
                clickCount--
                adapter.files = fileList[clickCount]
                adapter.notifyDataSetChanged()
            } else {
                null
            }
        }

        val githubApi = provideGithubApi(activity!!.applicationContext)
        var contentsCall = githubApi.getContents(repoOwner, repoName, path)
        contentsCall.enqueue({ response ->
            val statusCode = response.code()
            if (statusCode == 200) {
                val result = response.body()
                result?.let {
                    if (!fileList.contains(it)) fileList.add(it)
                    adapter.files = fileList[clickCount]
                    adapter.notifyDataSetChanged()
                }
            }
        }, {

        })

        adapter.onClick = { v ->
            val position = view.filesRecyclerView.getChildAdapterPosition(v)
            val item = adapter.files[position]
            path = item.path
            contentsCall = githubApi.getContents(repoOwner, repoName, path)
            contentsCall.enqueue({ response ->
                val statusCode = response.code()
                if (statusCode == 200) {
                    val result = response.body()
                    result?.let {
                        clickCount++
                        if (!fileList.contains(it)) fileList.add(it)
                        adapter.files = fileList[clickCount]
                        adapter.notifyDataSetChanged()
                    }
                }
            }, {

            })
        }
        return view
    }
}
