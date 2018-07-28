package com.ahndwon.gethub.ui

import android.graphics.PorterDuff
import android.opengl.Visibility
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.TabLayout
import android.support.v4.view.GravityCompat
import android.support.v4.view.ViewPager
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.ahndwon.gethub.R
import com.ahndwon.gethub.api.provideUserApi
import com.ahndwon.gethub.ui.adapter.IconPageAdapter
import com.ahndwon.gethub.ui.fragment.HomeFragment
import com.ahndwon.gethub.ui.fragment.IssueFragment
import com.ahndwon.gethub.ui.fragment.ProfileFragment
import com.ahndwon.gethub.ui.fragment.PullRequestFragment
import com.ahndwon.gethub.ui.adapter.SectionsPageAdapter
import com.ahndwon.gethub.utils.GlideApp
import com.ahndwon.gethub.utils.enqueue
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_sign_in.view.*
import kotlinx.android.synthetic.main.app_bar_home.*
import kotlinx.android.synthetic.main.content_home.*
import kotlinx.android.synthetic.main.nav_header_home.*

class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
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
        tabs.getTabAt(0)?.apply {
            icon?.setColorFilter(resources.getColor(R.color.colorAccent), PorterDuff.Mode.SRC_IN)
            text = adapter.fragmentTitleList[this.position]
        }
        tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                tab.icon?.setColorFilter(resources.getColor(R.color.black), PorterDuff.Mode.SRC_IN)
                tab.text = ""
            }

            override fun onTabSelected(tab: TabLayout.Tab) {
                tab.icon?.setColorFilter(resources.getColor(R.color.colorAccent), PorterDuff.Mode.SRC_IN)
                tab.text = adapter.fragmentTitleList[tab.position]
            }
        })

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)


        val userApi = provideUserApi(this)
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
                }
            }
        }, {

        })
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
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.home, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_home -> {
                // Handle the camera action
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
