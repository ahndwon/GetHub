package com.ahndwon.gethub.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ahndwon.gethub.R
import com.ahndwon.gethub.ui.adapter.SectionsPageAdapter
import kotlinx.android.synthetic.main.fragment_created.view.*
import kotlinx.android.synthetic.main.fragment_issue.view.*

class CreatedFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_created, container, false)
        setupViewPager(view.createdContainer)
        view.createdTabs.setupWithViewPager(view.createdContainer)

        return view
    }

    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = SectionsPageAdapter(fragmentManager!!)
        adapter.addFragment(IssueFragment.OpenFragment(), "Open")
        adapter.addFragment(IssueFragment.ClosedFragment(), "Closed")
        viewPager.adapter = adapter
    }
}