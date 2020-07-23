package com.example.themoviedb.presenter.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.themoviedb.domain.MovieCastEntity
import com.example.themoviedb.domain.MovieEntity
import com.example.themoviedb.domain.MovieRepository
import com.example.themoviedb.presenter.core.HandleOnce
import kotlinx.coroutines.launch

class CastViewModel(
    private val repository: MovieRepository,
    private val movie: MovieEntity
) : BaseViewModel() {

    init {
        getCastMovie(movie)
    }

    val castMovieData = MutableLiveData<List<MovieCastEntity>>()

    fun getCastMovie(movie: MovieEntity) {
        viewModelScope.launch {
            try {
                val cast = repository.getCastMovie(movie)
                castMovieData.value = cast
            } catch (e: Exception) {
                failureData.value = HandleOnce(e)
            }
        }
    }
}

class CastViewModelViewModelFactory(
    private val repository: MovieRepository,
    private val movie: MovieEntity
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CastViewModel(repository, movie) as T
    }
}