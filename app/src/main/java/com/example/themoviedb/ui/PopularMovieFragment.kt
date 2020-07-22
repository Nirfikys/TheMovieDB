package com.example.themoviedb.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.themoviedb.databinding.MovieListLayoutBinding
import com.example.themoviedb.domain.MovieRepository
import com.example.themoviedb.presenter.viewmodel.MovieViewModel
import com.example.themoviedb.presenter.viewmodel.MovieViewModelFactory
import com.example.themoviedb.ui.core.observe
import javax.inject.Inject

class PopularMovieFragment : Fragment() {
    lateinit var binding: MovieListLayoutBinding
    @Inject
    lateinit var repository: MovieRepository
    val movieModel: MovieViewModel by viewModels { MovieViewModelFactory(repository) }
    val adapter = PreviewMovieAdapter()

    init {
        App.appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MovieListLayoutBinding.inflate(inflater, container, false)
        binding.movieModel = movieModel
        binding.lifecycleOwner = this
        setupView()
        return binding.root
    }

    private fun setupView() {
        binding.movieRecycler.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.movieRecycler.adapter = adapter


        observe(movieModel.moviePreviews) {
            adapter.changeAdapterData(it.movies)
        }
    }
}