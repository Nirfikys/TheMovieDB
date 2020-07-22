package com.example.themoviedb.presenter.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.themoviedb.domain.MovieRepository
import com.example.themoviedb.domain.PageMovieEntity
import com.example.themoviedb.presenter.core.HandleOnce
import kotlinx.coroutines.launch

class MovieViewModel(
    private val repository: MovieRepository,
    private val popular: Boolean
) : BaseViewModel() {

    init {
        updateMoviePreview()
    }

    val moviePreviews = MutableLiveData<PageMovieEntity>()
    val movieChangePageInProgress = MutableLiveData<Boolean>(false)

    fun getNextPage() {
        val previewsPage = moviePreviews.value ?: return
        val nextPage = previewsPage.page + 1
        if (previewsPage.totalPages >= nextPage) {
            getPage(nextPage)
        }
    }

    fun getPrevPage() {
        val previewsPage = moviePreviews.value ?: return
        val prevPage = previewsPage.page - 1
        if (prevPage != 0) {
            getPage(prevPage)
        }
    }

    fun updateMoviePreview() {
        getPage(1)
    }

    private fun getPage(nextPage: Int) {
        viewModelScope.launch {
            try {
                val page =
                    if (popular) repository.getPopularMovies(nextPage)
                    else repository.getUpcomingMovies(nextPage)
                moviePreviews.value = page
            } catch (e: Exception) {
                failureData.value = HandleOnce(e)
            } finally {
                movieChangePageInProgress.value = false
            }
        }
    }
}

class MovieViewModelFactory(
    private val repository: MovieRepository,
    private val popular: Boolean
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MovieViewModel(repository, popular) as T
    }
}