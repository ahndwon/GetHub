package com.ahndwon.gethub.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.ahndwon.gethub.R
import com.ahndwon.gethub.api.provideIssueApi
import com.ahndwon.gethub.ui.adapter.IssueListAdapter
import com.ahndwon.gethub.utils.MyProgressBar
import com.ahndwon.gethub.utils.enqueue
import kotlinx.android.synthetic.main.fragment_open.view.*

class OpenFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_open, container, false)

        val progressBar = MyProgressBar(activity!!.applicationContext, view.createdOpenContainer)
        progressBar.view.visibility = View.VISIBLE

        val adapter = IssueListAdapter()
        view.openRecyclerView.adapter = adapter
        view.openRecyclerView.layoutManager = LinearLayoutManager(activity!!.applicationContext)

        val issueApi = provideIssueApi(activity!!.applicationContext)
        val issueCall = issueApi.getIssue("created", "open")

        issueCall.enqueue({ response ->
            progressBar.view.visibility = View.GONE

            val statusCode = response.code()
            Log.d(IssueFragment.TAG, "issue statusCode : $statusCode")
            Toast.makeText(activity!!.applicationContext, "${IssueFragment.TAG} enqueue",
                    Toast.LENGTH_SHORT).show()

            if (statusCode == 200) {
                val result = response.body()
                result?.let {
                    adapter.issues = it
                    adapter.notifyDataSetChanged()
                    Log.i(IssueFragment.TAG, result.toString())
                }
            }
        }, {
            Log.i(IssueFragment.TAG, "issue enqueue failure")
            Log.d(IssueFragment.TAG, "localizedMessage" + it.localizedMessage)
            Toast.makeText(activity!!.applicationContext, "Enqueue Failure",
                    Toast.LENGTH_SHORT).show()
        })

        return view
    }
}