package com.ahndwon.gethub.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.ahndwon.gethub.R
import com.ahndwon.gethub.api.provideIssueApi
import com.ahndwon.gethub.ui.adapter.IssueListAdapter
import com.ahndwon.gethub.ui.adapter.SectionsPageAdapter
import com.ahndwon.gethub.utils.MyProgressBar
import com.ahndwon.gethub.utils.enqueue
import kotlinx.android.synthetic.main.fragment_issue.view.*
import kotlinx.android.synthetic.main.fragment_open.view.*

class IssueFragment : Fragment() {
    companion object {
        val TAG = IssueFragment::class.java.simpleName
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_issue, container, false)

        setupViewPager(view.issueContainer)
        view.issueTabs.setupWithViewPager(view.issueContainer)

        return view
    }

//    class OpenFragment : Fragment() {
//        override fun onCreateView(inflater: LayoutInflater,
//                                  container: ViewGroup?, savedInstanceState: Bundle?): View? {
//            return inflater.inflate(R.layout.fragment_open, container, false)
//        }
//    }
//
//    class ClosedFragment : Fragment() {
//        override fun onCreateView(inflater: LayoutInflater,
//                                  container: ViewGroup?, savedInstanceState: Bundle?): View? {
//            return inflater.inflate(R.layout.fragment_closed, container, false)
//        }
//    }

    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = SectionsPageAdapter(childFragmentManager)
        adapter.addFragment(CreatedFragment(), "Created")
        adapter.addFragment(AssignedFragment(), "Assigned")
        adapter.addFragment(MentionedFragment(), "Mentioned")
        viewPager.adapter = adapter
    }
}