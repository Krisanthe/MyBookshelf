package krisanthe.exercise.mybookshelf.application

import android.content.Context
import dagger.Module
import dagger.Provides
import krisanthe.exercise.mybookshelf.utils.AppSchedulers



@Module
class AppContextModule(var context: Context) {

    @AppScope
    @Provides
    fun provideContext(): Context {
        return context
    }

    @AppScope
    @Provides
    fun provideScheduler(): AppSchedulers {
        return AppSchedulers()
    }
}