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
import com.ahndwon.gethub.ui.adapter.DirectoryListAdapter
import com.ahndwon.gethub.ui.adapter.FileListAdapter
import com.ahndwon.gethub.utils.enqueue
import kotlinx.android.synthetic.main.fragment_code_files.view.*


class CodeFilesFragment : Fragment() {
    var path: String = ""
    var clickCount = 0
    private val fileList = ArrayList<List<Content>>()
    private val dirList = ArrayList<String>()

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_code_files, container, false)

        val repoName = arguments?.getString("repoName").toString()
        val repoOwner = arguments?.getString("repoOwner").toString()

        val fileAdapter = FileListAdapter()
        view.filesRecyclerView.adapter = fileAdapter
        view.filesRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        val dirAdapter = DirectoryListAdapter()
        view.dirRecyclerView.adapter = dirAdapter
        view.dirRecyclerView.layoutManager = LinearLayoutManager(requireContext()).apply {
            orientation = LinearLayoutManager.HORIZONTAL
        }

        (activity as RepoActivity).onBackPressed = {
            if (clickCount > 0) {
                dirList.removeAt(clickCount)
                dirAdapter.directoryList = dirList
                dirAdapter.notifyDataSetChanged()

                clickCount--

                fileAdapter.files = fileList[clickCount]
                fileAdapter.notifyDataSetChanged()
            }
        }

        val githubApi = provideGithubApi(requireContext())
        var contentsCall = githubApi.getContents(repoOwner, repoName, path)
        contentsCall.enqueue({ response ->
            val statusCode = response.code()
            if (statusCode == 200) {
                val result = response.body()
                result?.let {
                    if (!dirList.contains(path)) dirList.add(path)
                    dirAdapter.directoryList = dirList
                    dirAdapter.notifyDataSetChanged()

                    if (!fileList.contains(it)) fileList.add(it)
                    fileAdapter.files = fileList[clickCount]
                    fileAdapter.notifyDataSetChanged()
                }
            }
        }, {

        })

        fileAdapter.onClick = { v ->
            val position = view.filesRecyclerView.getChildAdapterPosition(v)
            val item = fileAdapter.files[position]
            path = item.path
            contentsCall = githubApi.getContents(repoOwner, repoName, path)
            contentsCall.enqueue({ response ->
                val statusCode = response.code()
                if (statusCode == 200) {
                    val result = response.body()
                    result?.let {
                        clickCount++

                        if (!dirList.contains(path)) dirList.add(path)
                        dirAdapter.directoryList = dirList
                        dirAdapter.notifyDataSetChanged()

                        if (!fileList.contains(it)) fileList.add(it)
                        fileAdapter.files = fileList[clickCount]
                        fileAdapter.notifyDataSetChanged()
                    }
                }
            }, {

            })
        }
        return view
    }
}
