package com.example.themoviedb.presenter.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.themoviedb.domain.MovieRepository

class SavedMovieViewModel(
    private val repository: MovieRepository
) : BaseViewModel() {

    val savedMovies = repository.savedMovies
}

class SavedMovieViewModelFactory(
    private val repository: MovieRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SavedMovieViewModel(repository) as T
    }
}