package com.example.themoviedb.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.themoviedb.databinding.SavedLayoutBinding
import com.example.themoviedb.domain.MovieRepository
import com.example.themoviedb.presenter.viewmodel.SavedMovieViewModel
import com.example.themoviedb.presenter.viewmodel.SavedMovieViewModelFactory
import com.example.themoviedb.ui.core.observe
import javax.inject.Inject

class SavedListFragment : Fragment() {
    lateinit var binding: SavedLayoutBinding

    @Inject
    lateinit var repository: MovieRepository
    val savedMovieModel: SavedMovieViewModel by viewModels { SavedMovieViewModelFactory(repository) }
    val adapter = PreviewMovieAdapter()

    init {
        App.appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SavedLayoutBinding.inflate(inflater, container, false)
        setupToolbar()
        setupView()
        return binding.root
    }

    private fun setupView() {
        binding.savedMovieRecycler.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.savedMovieRecycler.adapter = adapter

        observe(savedMovieModel.savedMovies){
            adapter.changeAdapterData(it)
        }
    }

    private fun setupToolbar() {
        (activity as MainActivity).apply {
            setSupportActionBar(binding.toolbar)
            setupToolbar()
        }
    }
}