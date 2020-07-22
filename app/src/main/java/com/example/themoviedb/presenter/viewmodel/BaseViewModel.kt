package com.example.themoviedb.presenter.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.themoviedb.presenter.core.HandleOnce

abstract class BaseViewModel: ViewModel() {
    var failureData: MutableLiveData<HandleOnce<Throwable>> = MutableLiveData()
    var progressData: MutableLiveData<Boolean> = MutableLiveData()

    protected fun handleFailure(failure: Throwable) {
        this.failureData.value = HandleOnce(failure)
        updateProgress(false)
    }

    protected fun <T> handleValidResult(liveData: MutableLiveData<T>, data:T?){
        updateProgress(false)
        if (data != null)
            liveData.value = data
    }

    protected fun updateProgress(progress: Boolean) {
        this.progressData.value = progress
    }
}