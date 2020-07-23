package com.example.themoviedb.presenter.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.themoviedb.domain.MovieEntity
import com.example.themoviedb.domain.MovieRepository
import com.example.themoviedb.presenter.core.HandleOnce
import kotlinx.coroutines.launch

class SavedMovieViewModel(
    private val repository: MovieRepository
) : BaseViewModel() {

    val savedMovies = repository.savedMovies

    fun saveOrDeleteMovies(movie: MovieEntity){
        viewModelScope.launch {
            try {
                repository.saveOrDeleteMovie(movie)
            }catch (e:Exception){
                failureData.value = HandleOnce(e)
            }
        }
    }
}

class SavedMovieViewModelFactory(
    private val repository: MovieRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SavedMovieViewModel(repository) as T
    }
}