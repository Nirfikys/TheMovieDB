package com.example.themoviedb.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.themoviedb.databinding.MovieItemBinding
import com.example.themoviedb.domain.MoviePreviewEntity
import com.example.themoviedb.ui.core.BaseAdapter

class PreviewMovieAdapter : BaseAdapter<PreviewMovieAdapter.PreviewMovieViewHolder, MoviePreviewEntity>() {

    override fun areContentsTheSame(
        oldItem: MoviePreviewEntity,
        newItem: MoviePreviewEntity
    ): Boolean {
        return oldItem == newItem
    }

    override fun areItemsTheSame(
        oldItem: MoviePreviewEntity,
        newItem: MoviePreviewEntity
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun createHolder(parent: ViewGroup, viewType: Int): PreviewMovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return PreviewMovieViewHolder(
            MovieItemBinding.inflate(inflater, parent, false)
        )
    }

    class PreviewMovieViewHolder(private val binding: MovieItemBinding) :
        BaseAdapter.BaseViewHolder<MoviePreviewEntity>(binding.root){
        override fun onBind(item: MoviePreviewEntity) {
            binding.movie = item
        }
    }
}