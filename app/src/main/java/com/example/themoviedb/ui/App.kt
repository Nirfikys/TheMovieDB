package com.example.themoviedb.ui

import android.app.Application
import com.example.themoviedb.presenter.modules.AppModule
import com.example.themoviedb.presenter.modules.RemoteModule
import dagger.Component
import javax.inject.Singleton

class App : Application() {
    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()

        initAppComponent()
    }

    private fun initAppComponent() {
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this)).build()
    }


}

@Singleton
@Component(modules = [AppModule::class, RemoteModule::class])
interface AppComponent {
    fun inject(fragment: PopularMovieFragment)
}