package com.ahndwon.gethub.ui

import android.graphics.PorterDuff
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.design.widget.NavigationView
import android.support.design.widget.TabLayout
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.GravityCompat
import android.support.v4.view.ViewPager
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.ahndwon.gethub.R
import com.ahndwon.gethub.api.provideGithubApi
import com.ahndwon.gethub.ui.adapter.IconPageAdapter
import com.ahndwon.gethub.ui.fragment.HomeFragment
import com.ahndwon.gethub.ui.fragment.ProfileFragment
import com.ahndwon.gethub.ui.fragment.PullRequestFragment
import com.ahndwon.gethub.ui.fragment.issue.IssueFragment
import com.ahndwon.gethub.utils.GlideApp
import com.ahndwon.gethub.utils.enqueue
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.app_bar_home.*
import kotlinx.android.synthetic.main.content_home.*
import kotlinx.android.synthetic.main.nav_header_home.*


class HomeActivity :
        AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    companion object {
        val TAG: String = HomeActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = null

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

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)


        val userApi = provideGithubApi(this)
        val userCall = userApi.getUser()
        userCall.enqueue({ response ->
            val statusCode = response.code()
            if (statusCode == 200) {
                val result = response.body()
                result?.let {
                    GlideApp.with(this)
                            .load(it.avatarUrl)
                            .placeholder(R.drawable.ic_github)
                            .into(navAvatar)
                    navName.text = it.name
                    navLogin.text = it.login

                    PreferenceManager.getDefaultSharedPreferences(this).edit()
                            .putString("user_login_id", it.login)
                            .apply()
                }
            }
        }, {

        })
    }

    private fun setTabSelected(tab: TabLayout.Tab, adapter: FragmentPagerAdapter) {
        tab.icon?.setColorFilter(resources.getColor(R.color.colorAccent), PorterDuff.Mode.SRC_IN)
//        tab.text = adapter.fragmentTitleList[tab.position]
    }

    private fun setTabIcons() {
        tabs.getTabAt(0)?.setIcon(R.drawable.ic_home)
        tabs.getTabAt(1)?.setIcon(R.drawable.ic_issue_opened)
        tabs.getTabAt(2)?.setIcon(R.drawable.ic_pull_request)
        tabs.getTabAt(3)?.setIcon(R.drawable.ic_person)
    }


    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.home, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> {

            }
            R.id.nav_organizations -> {

            }
            R.id.nav_pinned -> {

            }
            R.id.nav_trending -> {

            }
            R.id.nav_gists -> {

            }
            R.id.nav_send -> {

            }
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun setupViewPager(viewPager: ViewPager, adapter: IconPageAdapter) {
        adapter.addFragment(HomeFragment(), "Home")
        adapter.addFragment(IssueFragment(), "Issue")
        adapter.addFragment(PullRequestFragment(), "Pull Request")
        adapter.addFragment(ProfileFragment(), "Profile")
        viewPager.adapter = adapter
    }
}
