package com.ahndwon.gethub.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.ahndwon.gethub.R
import com.ahndwon.gethub.api.provideIssueApi
import com.ahndwon.gethub.ui.adapter.SectionsPageAdapter
import com.ahndwon.gethub.utils.MyProgressBar
import com.ahndwon.gethub.utils.enqueue
import kotlinx.android.synthetic.main.content_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.fragment_issue.*
import kotlinx.android.synthetic.main.fragment_issue.view.*

class IssueFragment : Fragment() {
    companion object {
        val TAG = IssueFragment::class.java.simpleName
    }
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_issue, container, false)

        setupViewPager(view.issueContainer)
        view.issueTabs.setupWithViewPager(view.issueContainer)

        val progressBar = MyProgressBar(activity!!.applicationContext, view.issueContainer)
        progressBar.view.visibility = View.VISIBLE



        val issueApi = provideIssueApi(activity!!.applicationContext)
        val issueCall = issueApi.getIssue()
        issueCall.enqueue({ response ->
            progressBar.view.visibility = View.GONE
            val statusCode = response.code()
            Log.i(TAG, statusCode.toString())

            if (statusCode == 200) {
                val result = response.body()
                result?.let {
                    Toast.makeText(activity!!.applicationContext, result.toString(),
                            Toast.LENGTH_SHORT).show()
                    Log.i(TAG, result.toString()
                    )
                }
            }
        }, {
            Toast.makeText(activity!!.applicationContext, "Issue Fragment",
                    Toast.LENGTH_SHORT).show()
        })
        return view
    }

    class OpenFragment : Fragment() {
        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            return inflater.inflate(R.layout.fragment_open, container, false)
        }
    }

    class ClosedFragment : Fragment() {
        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            return inflater.inflate(R.layout.fragment_closed, container, false)
        }
    }

    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = SectionsPageAdapter(childFragmentManager)
        adapter.addFragment(CreatedFragment(), "Created")
        adapter.addFragment(AssignedFragment(), "Assigned")
        adapter.addFragment(MentionedFragment(), "Mentioned")
        viewPager.adapter = adapter
    }
}