package com.example.themoviedb.ui.home

import android.view.MenuItem
import android.view.View
import androidx.appcompat.view.ActionMode
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.themoviedb.R
import com.example.themoviedb.domain.MoviePreviewEntity
import com.example.themoviedb.domain.MovieRepository
import com.example.themoviedb.presenter.viewmodel.MovieViewModel
import com.example.themoviedb.presenter.viewmodel.MovieViewModelFactory
import com.example.themoviedb.ui.adapter.PreviewMovieAdapter
import com.example.themoviedb.ui.core.observe
import com.google.android.material.snackbar.Snackbar
import java.io.IOException

abstract class MovieListFragment<T : ViewDataBinding>(private val popular: Boolean) : Fragment() {
    abstract val binding: T
    abstract var repository: MovieRepository
    val movieModel: MovieViewModel by viewModels { MovieViewModelFactory(repository, popular) }
    val adapter = PreviewMovieAdapter()
    protected var actionMode: Boolean = false

    open fun setupView() {
        adapter.setOnClick({ moviePreviewEntity, view ->
            if (moviePreviewEntity == null) return@setOnClick
            if (actionMode)
                selectOrUnSelect(moviePreviewEntity)
            else
                getMovieInfo(moviePreviewEntity, view)
        }, { moviePreviewEntity, view ->
            if (moviePreviewEntity == null) return@setOnClick
            if (!actionMode) {
                (activity as MainActivity).createActionModeForMovie(
                    this as MovieListFragment<ViewDataBinding>,
                    MovieActionModeCallback(
                        ::selectItem,
                        ::destroyActionMode
                    )
                )
                selectOrUnSelect(moviePreviewEntity)
                actionMode = true
            }
        })
        observe(movieModel.moviePreviews) {
            adapter.changeAdapterData(it.movies)
        }
        observe(movieModel.failureData) {
            val exception = it.getContentIfNotHandled() ?: return@observe
            val message = when (exception) {
                is IOException -> requireContext().getText(R.string.error_network)
                else -> requireContext().getText(R.string.error)
            }
            showMessage(message)
        }
        observe(movieModel.movieSaveStatus) {
            val value = it.getContentIfNotHandled() ?: return@observe
            showMessage(requireContext().getText(R.string.successfully))
        }
    }

    private fun showMessage(message: CharSequence) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }

    protected open fun getMovieInfo(moviePreviewEntity: MoviePreviewEntity, view: View) {
        movieModel.getMovieInfo(moviePreviewEntity)
    }

    protected fun destroyActionMode(action: ActionMode?) {
        actionMode = false
        adapter.clearSelected()
    }

    protected fun selectItem(menuItem: MenuItem?): Boolean {
        val selectedMovie = adapter.getSelectedMovie().toList()
        movieModel.saveOrDeleteMovies(selectedMovie)
        (activity as MainActivity).cancelActionMode(this as MovieListFragment<ViewDataBinding>)
        return true
    }

    protected fun selectOrUnSelect(moviePreviewEntity: MoviePreviewEntity) {
        adapter.selectOrUnSelectMovie(moviePreviewEntity)
        if (adapter.selected.isEmpty()) (activity as MainActivity).cancelActionMode(this as MovieListFragment<ViewDataBinding>)
    }
}