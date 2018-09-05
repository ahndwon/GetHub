package com.ahndwon.gethub.ui

import android.graphics.PorterDuff
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import com.ahndwon.gethub.R
import com.ahndwon.gethub.ui.adapter.IconPageAdapter
import com.ahndwon.gethub.ui.fragment.repository.CodeFragment
import com.ahndwon.gethub.ui.fragment.repository.IssuesFragment
import com.ahndwon.gethub.ui.fragment.repository.ProjectsFragment
import com.ahndwon.gethub.ui.fragment.repository.PullRequestsFragment
import com.ahndwon.gethub.ui.fragment.repository.code.CodeFilesFragment
import kotlinx.android.synthetic.main.app_bar_home.*
import kotlinx.android.synthetic.main.content_home.*

class RepoActivity : AppCompatActivity() {
    var onBackPressed: (() -> Unit)? = null

    companion object {
        val TAG: String = RepoActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repo)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        val bundle = intent.extras
        val repoName = bundle.getString("repoName")
        repoName?.let {
            supportActionBar!!.title = it
        }
        val repoOwner = bundle.getString("repoOwner")

        val adapter = IconPageAdapter(supportFragmentManager)
        setupViewPager(container, adapter, repoName, repoOwner)

        tabs.setupWithViewPager(container)
        setTabIcons()
        tabs.getTabAt(0)?.let {
            setTabSelected(it, adapter)
        }

        tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                tab.icon?.setColorFilter(
                        ContextCompat.getColor(this@RepoActivity, R.color.black),
                        PorterDuff.Mode.SRC_IN)
//                tab.text = ""
            }

            override fun onTabSelected(tab: TabLayout.Tab) {
                setTabSelected(tab, adapter)
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val itemId = item?.itemId
        when (itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setTabSelected(tab: TabLayout.Tab, adapter: IconPageAdapter) {
        tab.icon?.setColorFilter(
                ContextCompat.getColor(this, R.color.colorAccent), PorterDuff.Mode.SRC_IN)
//        tab.text = adapter.fragmentTitleList[tab.position]
    }

    private fun setTabIcons() {
        tabs.getTabAt(0)?.setIcon(R.drawable.ic_code)
        tabs.getTabAt(1)?.setIcon(R.drawable.ic_issue_opened)
        tabs.getTabAt(2)?.setIcon(R.drawable.ic_pull_request)
        tabs.getTabAt(3)?.setIcon(R.drawable.ic_project)
    }

    private fun setupViewPager(
            viewPager: ViewPager, adapter: IconPageAdapter, repoName: String, repoOwner: String) {
        val args = Bundle()
        args.putString("repoName", repoName)
        args.putString("repoOwner", repoOwner)
        adapter.addFragment(CodeFragment().apply { arguments = args }, "Code")
        adapter.addFragment(IssuesFragment().apply { arguments = args }, "Issues")
        adapter.addFragment(PullRequestsFragment().apply { arguments = args }, "Pull Requests")
        adapter.addFragment(ProjectsFragment().apply { arguments = args }, "Projects")
        viewPager.adapter = adapter
    }

    override fun onBackPressed() {
        val currentFragment = getVisibleFragment()
        Log.d(TAG, currentFragment.toString())
        currentFragment?.let {
            val currentChildFragment = getVisibleChildFragment(currentFragment.childFragmentManager)

            Log.d(TAG, currentChildFragment.toString())
            if (currentChildFragment is CodeFilesFragment && onBackPressed != null) {

                if (currentChildFragment.clickCount == 0) {
                    super.onBackPressed()
                    return
                }
                onBackPressed?.invoke()
            }
            else super.onBackPressed()
        }
    }

    private fun getVisibleFragment(): Fragment? {
        val fragments = supportFragmentManager.fragments
        if (fragments != null) {
            for (fragment in fragments) {
                if (fragment != null && fragment.userVisibleHint)
                    return fragment
            }
        }
        return null
    }

    private fun getVisibleChildFragment(childManager: FragmentManager): Fragment? {
        val fragments = childManager.fragments
        if (fragments != null) {
            for (fragment in fragments) {
                if (fragment != null && fragment.userVisibleHint)
                    return fragment
            }
        }
        return null
    }
}