package krisanthe.exercise.mybookshelf.screens.books.dagger

import dagger.Module
import dagger.Provides
import io.objectbox.Box
import io.reactivex.disposables.CompositeDisposable
import krisanthe.exercise.mybookshelf.model.Book
import krisanthe.exercise.mybookshelf.screens.books.BooksActivity
import krisanthe.exercise.mybookshelf.screens.books.core.BooksModel
import krisanthe.exercise.mybookshelf.screens.books.core.BooksPresenter
import krisanthe.exercise.mybookshelf.screens.books.core.BooksView
import krisanthe.exercise.mybookshelf.utils.AppSchedulers

@Module
class BooksModule(val context: BooksActivity) {

    @BooksScope
    @Provides
    fun provideContext() : BooksActivity {
        return context
    }

    @BooksScope
    @Provides
    fun provideView(context: BooksActivity) : BooksView {
        return BooksView(context)
    }

    @BooksScope
    @Provides
    fun provideModel(bookDB: Box<Book>) : BooksModel {
        return BooksModel(context, bookDB)
    }

    @BooksScope
    @Provides
    fun providePresenter(schedulers: AppSchedulers, model: BooksModel, view: BooksView) : BooksPresenter {
        val subscriptions = CompositeDisposable()
        return BooksPresenter(schedulers, model, view, subscriptions)
    }
}