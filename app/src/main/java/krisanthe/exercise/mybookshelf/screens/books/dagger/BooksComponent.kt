package krisanthe.exercise.mybookshelf.screens.books.dagger

import dagger.Component
import krisanthe.exercise.mybookshelf.application.AppComponent
import krisanthe.exercise.mybookshelf.screens.books.BooksActivity

@BooksScope
@Component(modules = [BooksModule::class], dependencies = [AppComponent::class])
interface BooksComponent {
    fun inject(bookActivity: BooksActivity)
}