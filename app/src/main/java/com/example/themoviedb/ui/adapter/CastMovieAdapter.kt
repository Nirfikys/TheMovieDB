package com.example.themoviedb.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.themoviedb.databinding.CastItemBinding
import com.example.themoviedb.databinding.MovieItemBinding
import com.example.themoviedb.domain.MovieCastEntity
import com.example.themoviedb.domain.MoviePreviewEntity
import com.example.themoviedb.ui.core.BaseAdapter

class CastMovieAdapter :
    BaseAdapter<CastMovieAdapter.CastMovieViewHolder, MovieCastEntity>() {

    override fun areContentsTheSame(oldItem: MovieCastEntity, newItem: MovieCastEntity): Boolean {
        return oldItem == newItem
    }

    override fun areItemsTheSame(oldItem: MovieCastEntity, newItem: MovieCastEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun createHolder(parent: ViewGroup, viewType: Int): CastMovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return CastMovieViewHolder(
            CastItemBinding.inflate(inflater, parent, false)
        )
    }

    class CastMovieViewHolder(private val binding: CastItemBinding) :
        BaseAdapter.BaseViewHolder<MovieCastEntity>(binding.root) {
        override fun onBind(item: MovieCastEntity) {
            binding.cast = item
        }
    }
}