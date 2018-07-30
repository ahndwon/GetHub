package com.ahndwon.gethub.ui.fragment

import android.graphics.PorterDuff
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ahndwon.gethub.R
import com.ahndwon.gethub.ui.adapter.SectionsPageAdapter
import kotlinx.android.synthetic.main.fragment_mentioned.view.*

class MentionedFragment : Fragment() {
    lateinit var isPullRequest: String
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (arguments != null) {
            isPullRequest = arguments!!["isPullRequest"].toString()
        }

        val view = inflater.inflate(R.layout.fragment_mentioned, container, false)

        setupViewPager(view.mentionedContainer)
        view.mentionedTabs.setupWithViewPager(view.mentionedContainer)
        view.mentionedTabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                tab?.customView?.background?.setColorFilter(
                        resources.getColor(android.R.color.darker_gray), PorterDuff.Mode.SRC_IN)
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {

            }
        })
        return view
    }

    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = SectionsPageAdapter(fragmentManager!!)
        val openFragment = OpenClosedFragment().apply {
            val args = Bundle()
            args.putString("filter", "mentioned")
            args.putString("state", "open")
            args.putString("isPullRequest", this@MentionedFragment.isPullRequest)
            arguments = args
        }

        val closedFragment = OpenClosedFragment().apply {
            val args = Bundle()
            args.putString("filter", "mentioned")
            args.putString("state", "closed")
            args.putString("isPullRequest", this@MentionedFragment.isPullRequest)
            arguments = args
        }

        adapter.addFragment(openFragment, "Open")
        adapter.addFragment(closedFragment, "Closed")
        viewPager.adapter = adapter
    }
}