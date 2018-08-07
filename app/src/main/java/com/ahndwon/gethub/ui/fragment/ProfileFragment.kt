package com.ahndwon.gethub.ui.fragment

import android.app.Application
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ahndwon.gethub.R
import com.ahndwon.gethub.ui.adapter.SectionsPageAdapter
import com.ahndwon.gethub.ui.fragment.profile.*
import kotlinx.android.synthetic.main.fragment_profile.view.*

class ProfileFragment : Fragment() {
    companion object {
        val TAG: String = ProfileFragment::class.java.simpleName
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        setupViewPager(view.profileContainer)
        view.profileTabs.setupWithViewPager(view.profileContainer)

        return view
    }

    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = SectionsPageAdapter(childFragmentManager)
        adapter.addFragment(OverViewFragment(), "Overview")
        adapter.addFragment(RepositoriesFragment(), "Repos")
        adapter.addFragment(StarsFragment(), "Stars")
        adapter.addFragment(FollowersFragment(), "Followers")
        adapter.addFragment(FollowingFragment(), "Following")
        viewPager.adapter = adapter
    }
}

