package krisanthe.exercise.mybookshelf.screens.books.core

import io.reactivex.disposables.CompositeDisposable
import krisanthe.exercise.mybookshelf.utils.AppSchedulers
import io.reactivex.disposables.Disposable

import krisanthe.exercise.mybookshelf.model.Book
import timber.log.Timber

class BooksPresenter(
    private val schedulers : AppSchedulers,
    private val model: BooksModel,
    private val view: BooksView,
    private val subscriptions: CompositeDisposable) {

    var bookList =arrayListOf<Book>()

    fun onCreate() {
        subscriptions.add(getBookList())
        subscriptions.add(clickItemEvent())
        subscriptions.add(addNewBookEvent())
    }

    fun onDestroy() {
        subscriptions.clear()
    }

    fun onResume() {
        subscriptions.add(getBookList())
    }

    private fun clickItemEvent(): Disposable {
        return view.getClickObservable().subscribe {index -> model.goToEdit(bookList[index])}
    }

    private fun addNewBookEvent(): Disposable {
        return view.getNewBookObservable().subscribe { model.goToEdit(Book())}
    }

    private fun getBookList(): Disposable {
        return model.provideBookList()
            .subscribeOn(schedulers.backgroundScheduler())
            .observeOn(schedulers.androidThreadScheduler())
            .subscribe({ books ->
                this.bookList = books as ArrayList<Book>
                view.refreshAdapter(this.bookList)
            }, { throwable ->
                Timber.e(throwable, throwable.message)
            })
    }
}