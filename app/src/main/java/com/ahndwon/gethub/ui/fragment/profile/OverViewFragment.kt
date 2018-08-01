package com.ahndwon.gethub.ui.fragment.profile

import android.graphics.drawable.PictureDrawable
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ahndwon.gethub.R
import com.ahndwon.gethub.utils.GlideApp
import com.ahndwon.gethub.utils.SvgSoftwareLayerSetter
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import kotlinx.android.synthetic.main.fragment_over_view.view.*


class OverViewFragment : Fragment() {

//    private var requestBuilder: RequestBuilder<PictureDrawable>? = null

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_over_view, container, false)

        val uri = Uri.parse("https://ghchart.rshah.org/ahndwon")

        val requestBuilder = GlideApp.with(this)
                .`as`(PictureDrawable::class.java)
                .error(R.drawable.ic_issue_opened)
                .transition(withCrossFade())
                .listener(SvgSoftwareLayerSetter())

        requestBuilder.load(uri).into(view.chart);

        return view
    }
}
