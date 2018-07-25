package com.ahndwon.gethub.utils

import android.R
import android.content.Context
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ProgressBar

class MyProgressBar(val context: Context, val viewGroup: ViewGroup) {
    val view by lazy {
        ProgressBar(context, null,
                R.attr.progressBarStyleLarge)
    }
    init {
        view.indeterminateDrawable.setColorFilter(
                context.resources.getColor(com.ahndwon.gethub.R.color.colorAccent),
                android.graphics.PorterDuff.Mode.MULTIPLY)
        val params = FrameLayout.LayoutParams(200, 200)
        params.gravity = Gravity.CENTER
        viewGroup.addView(view, params)
    }
}
