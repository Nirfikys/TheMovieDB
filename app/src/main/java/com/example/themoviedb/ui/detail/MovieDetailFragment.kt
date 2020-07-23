package com.example.themoviedb.ui.detail

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.themoviedb.R
import com.example.themoviedb.databinding.MovieInfoLayoutBinding
import com.example.themoviedb.domain.MovieEntity
import com.example.themoviedb.domain.MovieRepository
import com.example.themoviedb.presenter.viewmodel.SavedMovieViewModel
import com.example.themoviedb.presenter.viewmodel.SavedMovieViewModelFactory
import com.example.themoviedb.ui.App
import com.example.themoviedb.ui.MovieDetailFragmentArgs
import com.example.themoviedb.ui.home.MainActivity
import com.google.android.material.transition.MaterialContainerTransform
import javax.inject.Inject

class MovieDetailFragment : Fragment() {
    @Inject
    lateinit var repository: MovieRepository
    private lateinit var binding: MovieInfoLayoutBinding
    private lateinit var movie: MovieEntity
    private val args: MovieDetailFragmentArgs by navArgs()
    private val saveModel: SavedMovieViewModel by viewModels { SavedMovieViewModelFactory(repository) }


    init {
        App.appComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val transformation: MaterialContainerTransform = MaterialContainerTransform().apply {
            fadeMode = MaterialContainerTransform.FADE_MODE_CROSS
            duration = 500
        }
        sharedElementEnterTransition = transformation
        setHasOptionsMenu(true)
        movie = args.movie
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MovieInfoLayoutBinding.inflate(inflater, container, false)
        setupToolbar()
        setupView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.movie = movie
        binding.executePendingBindings()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.movie_list_action_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_add_to_saved)
            saveModel.saveMovies(movie)
        return super.onOptionsItemSelected(item)
    }

    private fun setupView() {

    }

    private fun setupToolbar() {
        (activity as MainActivity).apply {
            setSupportActionBar(binding.toolbar)
            setupToolbar()
            binding.toolbar.title = movie.title
        }
    }
}