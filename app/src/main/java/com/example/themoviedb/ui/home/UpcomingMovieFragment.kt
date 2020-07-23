package com.example.themoviedb.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.doOnPreDraw
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.themoviedb.R
import com.example.themoviedb.databinding.MovieListLayoutBinding
import com.example.themoviedb.domain.MoviePreviewEntity
import com.example.themoviedb.domain.MovieRepository
import com.example.themoviedb.ui.App
import com.example.themoviedb.ui.core.observe
import javax.inject.Inject

class UpcomingMovieFragment: MovieListFragment<MovieListLayoutBinding>(false) {
    override lateinit var binding: MovieListLayoutBinding
    @Inject
    override lateinit var repository: MovieRepository
    private var transitionView: View? = null

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
        postponeEnterTransition()
        binding.movieRecycler.doOnPreDraw { startPostponedEnterTransition() }
        return binding.root
    }

    override fun setupView() {
        binding.movieRecycler.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.movieRecycler.adapter = adapter
        observe(movieModel.movieInfoData) {
            val movie = it.getContentIfNotHandled() ?: return@observe
            val image = transitionView?.findViewById<ImageView>(R.id.movie_item_image)
            val extras = FragmentNavigatorExtras(image!! to image.transitionName)
            val destination =
                HomeFragmentDirections.actionHomeFragmentToMovieDetailFragment(
                    movie
                )
            findNavController().navigate(destination, extras)
        }
        super.setupView()
    }

    override fun getMovieInfo(moviePreviewEntity: MoviePreviewEntity, view: View) {
        transitionView = view
        super.getMovieInfo(moviePreviewEntity, view)
    }
}