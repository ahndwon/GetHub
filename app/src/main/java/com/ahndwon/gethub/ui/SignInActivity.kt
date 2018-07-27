package com.ahndwon.gethub.ui

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.customtabs.CustomTabsIntent
import android.util.Log
import com.ahndwon.gethub.R
import com.ahndwon.gethub.api.authApi
import com.ahndwon.gethub.api.updateToken
import com.ahndwon.gethub.utils.enqueue
import kotlinx.android.synthetic.main.activity_sign_in.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class SignInActivity : AppCompatActivity() {

    companion object {
        val TAG = SignInActivity::class.java.simpleName

        const val CLIENT_ID = "27769b070f11c43b2293"
        const val CLIENT_SECRET = "7a7ca5346b9428abf208bb9aa933bb9e3305858f"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        oauthButton.setOnClickListener {
            login()
        }

    }


    private fun login() {
        val authUri = Uri.Builder().scheme("https")
                .authority("github.com")
                .appendPath("login")
                .appendPath("oauth")
                .appendPath("authorize")
                .appendQueryParameter("client_id", CLIENT_ID)
                .appendQueryParameter("scope", "repo")
                .build()


        val intent = CustomTabsIntent.Builder().build()
        intent.launchUrl(this, authUri)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        toast("onNewIntent")
        check(intent != null)
        check(intent?.data != null)

        val uri = intent?.data
        val code = uri?.getQueryParameter("code") ?: throw IllegalStateException("no code!!")

        getAccessToken(code)
    }

    private fun getAccessToken(code: String) {
        Log.i(TAG, "code: $code")

        val call = authApi.getAccessToken(CLIENT_ID, CLIENT_SECRET, code)
        call.enqueue({
            it.body()?.let {
                updateToken(this, it.accessToken)
                toast("로그인에 성공했습니다.")
                Log.i(TAG, "Access Token : ${it.accessToken}")

                startActivity<HomeActivity>()
            }
        }, {
            toast(it.message.toString())
        })
    }
}
