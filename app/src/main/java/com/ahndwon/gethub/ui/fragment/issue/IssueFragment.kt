package com.ahndwon.gethub.ui.fragment.issue

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ahndwon.gethub.R
import com.ahndwon.gethub.ui.adapter.SectionsPageAdapter
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

        for (i in 0..view.issueTabs.tabCount) {
            view.issueTabs.getTabAt(i)
        }

        return view
    }

    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = SectionsPageAdapter(childFragmentManager)
        adapter.addFragment(CreatedFragment().apply {
            val args = Bundle()
            args.putString("isPullRequest", "false")
            arguments = args
        }, "Created")
        adapter.addFragment(AssignedFragment().apply {
            val args = Bundle()
            args.putString("isPullRequest", "false")
            arguments = args
        }, "Assigned")
        adapter.addFragment(MentionedFragment().apply {
            val args = Bundle()
            args.putString("isPullRequest", "false")
            arguments = args
        }, "Mentioned")
        viewPager.adapter = adapter
    }
}