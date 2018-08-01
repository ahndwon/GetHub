package com.ahndwon.gethub.ui.fragment.profile

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ahndwon.gethub.R
import com.ahndwon.gethub.utils.GlideApp
import kotlinx.android.synthetic.main.fragment_over_view.view.*


class OverViewFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_over_view, container, false)

        GlideApp.with(this)
                .load("http://ghchart.rshah.org/ahndwon")
                .placeholder(R.drawable.ic_github)
                .into(view.chart)
        return view
    }
}
