package com.example.themoviedb.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.themoviedb.databinding.MovieItemBinding
import com.example.themoviedb.domain.MoviePreviewEntity
import com.example.themoviedb.ui.core.BaseAdapter

class PreviewMovieAdapter :
    BaseAdapter<PreviewMovieAdapter.PreviewMovieViewHolder, MoviePreviewEntity>() {
    val selected = HashSet<Int>()

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

    override fun onBindViewHolder(holder: PreviewMovieViewHolder, position: Int) {
        holder.selected = selected.contains(position)
        super.onBindViewHolder(holder, position)
    }

    fun selectOrUnSelectMovie(movie: MoviePreviewEntity) {
        val position = super.items.indexOf(movie)
        if (position == -1) return
        if (selected.contains(position))
            selected.remove(position)
        else
            selected.add(position)
        notifyItemChanged(position)
    }

    fun clearSelected() {
        val selectedMovie = selected.toList()
        selected.clear()
        selectedMovie.forEach { notifyItemChanged(it) }
    }

    fun getSelectedMovie(): List<MoviePreviewEntity> {
        return super.items.filterIndexed { index, _ -> selected.contains(index) }
    }

    class PreviewMovieViewHolder(private val binding: MovieItemBinding) :
        BaseAdapter.BaseViewHolder<MoviePreviewEntity>(binding.root) {
        var selected = false
        override fun onBind(item: MoviePreviewEntity) {
            binding.movie = item
            binding.previewMovieCard.isChecked = selected
            binding.invalidateAll()
        }
    }
}