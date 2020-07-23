package com.example.themoviedb.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ActionMode
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.themoviedb.R
import com.example.themoviedb.databinding.MovieListLayoutBinding
import com.example.themoviedb.domain.MoviePreviewEntity
import com.example.themoviedb.domain.MovieRepository
import com.example.themoviedb.presenter.viewmodel.MovieViewModel
import com.example.themoviedb.presenter.viewmodel.MovieViewModelFactory
import com.example.themoviedb.ui.core.observe
import com.google.android.material.transition.MaterialContainerTransform
import javax.inject.Inject

class PopularMovieFragment : Fragment() {
    lateinit var binding: MovieListLayoutBinding

    @Inject
    lateinit var repository: MovieRepository
    val movieModel: MovieViewModel by viewModels { MovieViewModelFactory(repository, true) }
    val adapter = PreviewMovieAdapter()
    private var actionMode: ActionMode? = null
    private var transitionView:View? = null

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

    private fun setupView() {
        binding.movieRecycler.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.movieRecycler.adapter = adapter
        adapter.setOnClick({ moviePreviewEntity, view ->
            if (moviePreviewEntity == null) return@setOnClick
            if (actionMode != null)
                selectOrUnSelect(moviePreviewEntity)
            else
                getMovieInfo(moviePreviewEntity, view)
        }, { moviePreviewEntity, view ->
            if (moviePreviewEntity == null) return@setOnClick
            if (actionMode == null)
                actionMode =
                    (activity as AppCompatActivity).startSupportActionMode(
                        MovieActionModeCallback(
                            ::selectItem,
                            ::destroyActionMode
                        )
                    )
            selectOrUnSelect(moviePreviewEntity)
        })


        observe(movieModel.moviePreviews) {
            adapter.changeAdapterData(it.movies)
        }
        observe(movieModel.movieInfoData) {
            val movie = it.getContentIfNotHandled() ?: return@observe
            val image = transitionView?.findViewById<ImageView>(R.id.movie_item_image)
            val extras = FragmentNavigatorExtras( image!! to image.transitionName )
            val destination =
                HomeFragmentDirections.actionHomeFragmentToMovieDetailFragment(movie)
            findNavController().navigate(destination, extras)
        }
    }

    private fun getMovieInfo(moviePreviewEntity: MoviePreviewEntity, view:View) {
        transitionView = view
        movieModel.getMovieInfo(moviePreviewEntity)
    }

    private fun destroyActionMode(action: ActionMode?) {
        actionMode = null
        adapter.clearSelected()
    }

    private fun selectItem(menuItem: MenuItem?): Boolean {
        val selectedMovie = adapter.getSelectedMovie().toList()
        movieModel.saveMovies(selectedMovie)
//        when (menuItem?.itemId) {
//            R.id.action_menu_person_delete -> adapter.getSelectedPerson()
//                .forEach { creditModel.deletePerson(it) }
//            else -> return false
//        }
        actionMode?.finish()
        return true
    }

    private fun selectOrUnSelect(moviePreviewEntity: MoviePreviewEntity) {
        adapter.selectOrUnSelectMovie(moviePreviewEntity)
        if (adapter.selected.isEmpty()) actionMode?.finish()
    }
}