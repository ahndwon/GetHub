package com.ahndwon.gethub.ui.fragment.issue

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.ahndwon.gethub.R
import com.ahndwon.gethub.api.model.Issue
import com.ahndwon.gethub.api.provideGithubApi
import com.ahndwon.gethub.ui.adapter.IssueListAdapter
import com.ahndwon.gethub.utils.MyProgressBar
import com.ahndwon.gethub.utils.enqueue
import kotlinx.android.synthetic.main.fragment_open_closed.view.*

class OpenClosedFragment : Fragment() {
    companion object {
        val TAG = OpenClosedFragment::class.java.simpleName!!
    }

    lateinit var filter: String
    lateinit var state: String
    lateinit var isPullRequest: String

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {

        filter = arguments?.get("filter").toString()
        state = arguments?.get("state").toString()
        isPullRequest = arguments?.get("isPullRequest").toString()


        val view = inflater.inflate(R.layout.fragment_open_closed, container, false)

        val progressBar = setProgressBar(view.createdClosedContainer)

        val adapter = IssueListAdapter()
        view.closedRecyclerView.adapter = adapter
        view.closedRecyclerView.layoutManager = LinearLayoutManager(activity!!.applicationContext)

        callEnqueue(adapter, progressBar, filter, state)

        return view
    }

    private fun setProgressBar(viewGroup: ViewGroup): MyProgressBar {
        val progressBar = MyProgressBar(activity!!.applicationContext, viewGroup)
        progressBar.view.visibility = View.VISIBLE
        return progressBar
    }

    private fun callEnqueue(adapter: IssueListAdapter, progressBar: MyProgressBar,
                            filter: String, state: String) {
        val issueApi = provideGithubApi(activity!!.applicationContext)
        val issueCall = issueApi.getIssue(filter, state)

        issueCall.enqueue({ response ->
            progressBar.view.visibility = View.GONE

            val statusCode = response.code()

            if (statusCode == 200) {
                val result = response.body()
                result?.let {
                    if (isPullRequest.equals("true")) {
                        val pullRequestList = ArrayList<Issue>()
                        for (issue in it) {
                            if (issue.pullRequest != null) {
                                pullRequestList.add(issue)
                            } else{
                                Log.d(TAG, "pullrequest null")
                            }
                        }
                        adapter.issues = pullRequestList
                    } else {
                        adapter.issues = it
                    }

                    adapter.notifyDataSetChanged()
                }
            }
        }, {
            Log.i(TAG, "issue enqueue failure")
            Log.d(TAG, "localizedMessage" + it.localizedMessage)
            Toast.makeText(activity!!.applicationContext, "Enqueue Failure",
                    Toast.LENGTH_SHORT).show()
        })
    }
}