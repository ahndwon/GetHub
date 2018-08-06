package com.ahndwon.gethub.utils

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.PictureDrawable
import android.support.annotation.Nullable
import com.bumptech.glide.load.Options
import com.bumptech.glide.load.engine.Resource
import com.bumptech.glide.load.resource.SimpleResource
import com.bumptech.glide.load.resource.transcode.ResourceTranscoder
import com.caverock.androidsvg.SVG


//class SvgDrawableTranscoder : ResourceTranscoder<SVG, PictureDrawable> {
//    @Nullable
//    override fun transcode(toTranscode: Resource<SVG>,
//                           options: Options): Resource<PictureDrawable>? {
//        val svg = toTranscode.get()
//        val picture = svg.renderToPicture()
//        val drawable = PictureDrawable(picture)
//        return SimpleResource(drawable)
//    }
//}

class SvgDrawableTranscoder : ResourceTranscoder<SVG, Bitmap> {
    @Nullable
    override fun transcode(toTranscode: Resource<SVG>,
                           options: Options): Resource<Bitmap>? {
        val svg = toTranscode.get()
        val picture = svg.renderToPicture()

        val drawable = Bitmap.createBitmap(picture.width ,
                picture.height + 5, Bitmap.Config.ARGB_8888)
        val bitmap = Bitmap.createBitmap(drawable, 0,0,
                drawable.width / 2, drawable.height)
        val canvas = Canvas(bitmap)
        canvas.translate(-drawable.width / 2f, 5f)
        svg.renderToCanvas(canvas)

        return SimpleResource(bitmap)
    }
}