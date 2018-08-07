package com.ahndwon.gethub.ui

import android.graphics.PorterDuff
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.ahndwon.gethub.R
import com.ahndwon.gethub.ui.adapter.IconPageAdapter
import com.ahndwon.gethub.ui.fragment.repository.CodeFragment
import com.ahndwon.gethub.ui.fragment.repository.IssuesFragment
import com.ahndwon.gethub.ui.fragment.repository.ProjectsFragment
import com.ahndwon.gethub.ui.fragment.repository.PullRequestsFragment
import kotlinx.android.synthetic.main.app_bar_home.*
import kotlinx.android.synthetic.main.content_home.*

class RepoActivity : AppCompatActivity() {
    companion object {
        val TAG: String = RepoActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repo)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeButtonEnabled(true)


        val bundle = intent.extras
        val repoName = bundle.getString("repoName")
        repoName?.let {
            supportActionBar!!.title = it
        }

        val adapter = IconPageAdapter(supportFragmentManager)
        setupViewPager(container, adapter)

        tabs.setupWithViewPager(container)
        setTabIcons()
        tabs.getTabAt(0)?.let {
            setTabSelected(it, adapter)
        }

        tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                tab.icon?.setColorFilter(resources.getColor(R.color.black), PorterDuff.Mode.SRC_IN)
                tab.text = ""
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
        tab.icon?.setColorFilter(resources.getColor(R.color.colorAccent), PorterDuff.Mode.SRC_IN)
//        tab.text = adapter.fragmentTitleList[tab.position]
    }

    private fun setTabIcons() {
        tabs.getTabAt(0)?.setIcon(R.drawable.ic_code)
        tabs.getTabAt(1)?.setIcon(R.drawable.ic_issue_opened)
        tabs.getTabAt(2)?.setIcon(R.drawable.ic_pull_request)
        tabs.getTabAt(3)?.setIcon(R.drawable.ic_project)
    }

    private fun setupViewPager(viewPager: ViewPager, adapter: IconPageAdapter) {
        adapter.addFragment(CodeFragment(), "Code")
        adapter.addFragment(IssuesFragment(), "Issues")
        adapter.addFragment(PullRequestsFragment(), "Pull Requests")
        adapter.addFragment(ProjectsFragment(), "Projects")
        viewPager.adapter = adapter
    }
}