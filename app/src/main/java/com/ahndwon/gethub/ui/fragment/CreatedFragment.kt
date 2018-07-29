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
import kotlinx.android.synthetic.main.fragment_created.view.*

class CreatedFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_created, container, false)
        setupViewPager(view.createdContainer)
        view.createdTabs.setupWithViewPager(view.createdContainer)
        view.createdTabs.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
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
        adapter.addFragment(OpenFragment(), "Open")
        adapter.addFragment(ClosedFragment(), "Closed")
        viewPager.adapter = adapter
    }
}