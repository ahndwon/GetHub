package com.ahndwon.gethub.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ahndwon.gethub.R
import com.ahndwon.gethub.ui.adapter.SectionsPageAdapter
import com.ahndwon.gethub.ui.fragment.issue.AssignedFragment
import com.ahndwon.gethub.ui.fragment.issue.CreatedFragment
import com.ahndwon.gethub.ui.fragment.issue.MentionedFragment
import kotlinx.android.synthetic.main.fragment_pull_request.view.*

class PullRequestFragment : Fragment() {
    companion object {
        val TAG = PullRequestFragment::class.java.simpleName
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_pull_request, container, false)

        setupViewPager(view.pullRequestContainer)
        view.pullRequestTabs.setupWithViewPager(view.pullRequestContainer)

        return view
    }

    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = SectionsPageAdapter(childFragmentManager)
        adapter.addFragment(CreatedFragment().apply {
            val args = Bundle()
            args.putString("isPullRequest", "true")
            arguments = args
        }, "Created")
        adapter.addFragment(AssignedFragment().apply {
            val args = Bundle()
            args.putString("isPullRequest", "true")
            arguments = args
        }, "Assigned")
        adapter.addFragment(MentionedFragment().apply {
            val args = Bundle()
            args.putString("isPullRequest", "true")
            arguments = args
        }, "Mentioned")
        viewPager.adapter = adapter
    }
}