package com.example.themoviedb.ui

import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.view.ActionMode
import com.example.themoviedb.R

class MovieActionModeCallback(
    private val itemClick: (MenuItem?) -> Boolean,
    private val destroyActionMode: (ActionMode?) -> Unit
) : ActionMode.Callback {
    override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
        return itemClick(item)
    }

    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        val inflater = mode?.menuInflater
        inflater?.inflate(R.menu.movie_list_action_menu, menu)
        return true
    }

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        return true
    }

    override fun onDestroyActionMode(mode: ActionMode?) {
        destroyActionMode(mode)
    }

}
