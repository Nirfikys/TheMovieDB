package com.example.themoviedb.presenter.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.themoviedb.domain.MovieRepository
import com.example.themoviedb.domain.PageMovieEntity
import kotlinx.coroutines.launch

class PopularViewModel(
    private val repository: MovieRepository
) : BaseViewModel() {

    init {
        updateMoviePreview()
    }

    val moviePreviews = MutableLiveData<PageMovieEntity>()

    fun updateMoviePreview() {
        viewModelScope.launch {
            try {
                val page = repository.getPopularMovies(1)
                handleValidResult(moviePreviews, page)
            } catch (e: Exception) {
                handleFailure(e)
            }
        }
    }
}

class PopularViewModelFactory(
    private val repository: MovieRepository
):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PopularViewModel(repository) as T
    }
}