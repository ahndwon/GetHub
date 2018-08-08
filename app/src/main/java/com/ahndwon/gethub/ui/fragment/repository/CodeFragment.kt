package com.ahndwon.gethub.ui.fragment.repository

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ahndwon.gethub.R
import com.ahndwon.gethub.ui.adapter.SectionsPageAdapter
import com.ahndwon.gethub.ui.fragment.repository.code.*
import kotlinx.android.synthetic.main.fragment_code.view.*


class CodeFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_code, container, false)

        val repoName = arguments?.get("repoName").toString()
        repoName?.let {
            setupViewPager(view.codeContainer, repoName)
        }

        view.codeTabs.setupWithViewPager(view.codeContainer)
        view.codeTabs.tabMode = TabLayout.MODE_SCROLLABLE
        return view
    }

    private fun setupViewPager(viewPager: ViewPager, repoName: String) {
        val args = Bundle()
        args.putString("repoName", repoName)
        val adapter = SectionsPageAdapter(childFragmentManager)
        adapter.addFragment(CodeInfoFragment().apply { arguments = args }, "Info")
        adapter.addFragment(CodeFilesFragment(), "Files")
        adapter.addFragment(CodeCommitsFragment(), "Commits")
        adapter.addFragment(CodeReleasesFragment(), "Releases")
        adapter.addFragment(CodeContributorsFragment(), "Contributors")
        viewPager.adapter = adapter
    }
}
