package com.ahndwon.gethub.ui.fragment.issue

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ahndwon.gethub.R
import com.ahndwon.gethub.ui.adapter.SectionsPageAdapter
import kotlinx.android.synthetic.main.fragment_assigned.view.*

class AssignedFragment : Fragment() {
    companion object {
        val TAG = AssignedFragment::class.java.simpleName!!
    }

    private lateinit var isPullRequest: String

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {

        isPullRequest = arguments?.get("isPullRequest").toString()

        val view = inflater.inflate(R.layout.fragment_assigned, container, false)
        setupViewPager(view.assignedContainer)
        view.assignedTabs.setupWithViewPager(view.assignedContainer)

        return view
    }

    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = SectionsPageAdapter(childFragmentManager)
        val openFragment = OpenClosedFragment().apply {
            val args = Bundle()
            args.putString("filter", "assigned")
            args.putString("state", "open")
            args.putString("isPullRequest", this@AssignedFragment.isPullRequest)
            arguments = args
        }

        val closedFragment = OpenClosedFragment().apply {
            val args = Bundle()
            args.putString("filter", "assigned")
            args.putString("state", "closed")
            args.putString("isPullRequest", this@AssignedFragment.isPullRequest)
            arguments = args
        }

        adapter.addFragment(openFragment, "Open")
        adapter.addFragment(closedFragment, "Closed")
        viewPager.adapter = adapter
    }
}