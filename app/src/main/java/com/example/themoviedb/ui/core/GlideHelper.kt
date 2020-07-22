package com.example.themoviedb.ui.core

import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.themoviedb.R

object GlideHelper {

    const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"

    fun loadImage(context: Context, path: String?, iv: ImageView) {
        if (path.isNullOrBlank()){
            Glide.with(context).load(R.drawable.ic_error_black_48dp).into(iv)
            return
        }
        val completedPath = IMAGE_BASE_URL + path

        Glide.with(context)
            .load(completedPath)
            .error(R.drawable.ic_error_black_48dp)
            .into(iv)
    }

    @JvmStatic
    @BindingAdapter("movieImage")
    fun ImageView.loadImage(image: String?) {
        loadImage(this.context, image, this)
    }
}