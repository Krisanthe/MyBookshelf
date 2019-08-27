package krisanthe.exercise.mybookshelf.application

import dagger.Component
import io.objectbox.Box
import krisanthe.exercise.mybookshelf.model.Book
import krisanthe.exercise.mybookshelf.utils.AppSchedulers

@AppScope
@Component(modules = [AppContextModule::class, DatabaseModule::class])
interface AppComponent {

    fun bookDao() : Box<Book>
    fun appSchedulers(): AppSchedulers
}