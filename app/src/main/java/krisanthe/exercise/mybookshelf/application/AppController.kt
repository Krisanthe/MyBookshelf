package krisanthe.exercise.mybookshelf.application

import android.app.Application
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers


class AppController : Application() {

    private var scheduler: Scheduler? = null
    companion object {
        var appComponent: AppComponent? = null
    }
    private lateinit var instance: AppController


    override fun onCreate() {
        super.onCreate()
        instance = this

        initializeLogger()
        initAppComponent()

    }

    fun subscribeScheduler(): Scheduler? {
        if (scheduler == null) {
            scheduler = Schedulers.io()
        }
        return scheduler
    }


    private fun initAppComponent() {
        appComponent = DaggerAppComponent.builder()
            .appContextModule(AppContextModule(this))
            .databaseModule(DatabaseModule())
            .build()
    }

    private fun initializeLogger() {
//        if (BuildConfig.DEBUG) {
//            Timber.plant(Timber.DebugTree())
//        } else {
//            Timber.plant(object : Timber.Tree() {
//                override fun log(priority: Int, tag: String, message: String, t: Throwable) {
//                    //TODO Realise version
//                }
//            })
//        }
    }
}