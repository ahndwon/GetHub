package com.ahndwon.gethub.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.ahndwon.gethub.R
import kotlinx.android.synthetic.main.app_bar_home.*

class RepoActivity : AppCompatActivity() {
    companion object {
        val TAG: String = RepoActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repo)
        setSupportActionBar(toolbar)

        val bundle = intent.extras
        val repoName = bundle.getString("repoName")
        repoName?.let {
            Log.i(TAG, repoName)
        }
    }
}