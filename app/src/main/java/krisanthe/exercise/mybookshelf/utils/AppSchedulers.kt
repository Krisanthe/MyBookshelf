package krisanthe.exercise.mybookshelf.utils

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.Executors

class AppSchedulers {

    fun backgroundScheduler() :Scheduler {
        return Schedulers.from(Executors.newCachedThreadPool())
    }

    fun androidThreadScheduler() :Scheduler {
        return AndroidSchedulers.mainThread()
    }
}