package krisanthe.exercise.mybookshelf.screens.details.core

import android.text.TextUtils
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import krisanthe.exercise.mybookshelf.model.Book
import krisanthe.exercise.mybookshelf.utils.AppSchedulers
import timber.log.Timber

class DetailsPresenter(
    private val schedulers: AppSchedulers,
    private val model: DetailsModel,
    private val view: DetailsView,
    private val subscriptions: CompositeDisposable
) {

    fun onCreate() {
        subscriptions.add(clickSaveEvent())
        subscriptions.add(clickRemoveEvent())
    }

    fun onDestroy() {
        subscriptions.clear()
    }

    private fun clickSaveEvent(): Disposable {
        return view.getSaveObservable().subscribe({ bookToSave ->
            if (verify(bookToSave)) {
                subscriptions.add(savedBook(bookToSave))
            }
        }, { throwable ->
            Timber.e(throwable, throwable.message)
        })
    }

    private fun clickRemoveEvent(): Disposable {
        return view.getRemoveObservable().subscribe( { bookToRemove -> subscriptions.add(removeBook(bookToRemove)) }, { throwable ->
            Timber.e(throwable, throwable.message)
        })
    }

    private fun savedBook(book: Book): Disposable {
        return model.saveBook(book)
            .subscribeOn(schedulers.backgroundScheduler())
            .observeOn(schedulers.androidThreadScheduler())
            .subscribe({ model.finish() }, { throwable ->
                Timber.e(throwable, throwable.message)
            })
    }

    private fun verify(book: Book): Boolean {
        return  (if (TextUtils.isEmpty(book.title)) {
                    view.onTitleValidation("Title field cannot be empty"); false
                } else {view.onTitleValidation(""); true}) &&
                (if (TextUtils.isEmpty(book.author)) {
                    view.onAuthorValidation("Author field cannot be empty"); false
                } else {view.onAuthorValidation(""); true}) &&
                isIsbnVerify(book.isbn) &&
                (if (TextUtils.isEmpty(book.pages.toString())) {
                    view.onPagesValidation("Pages field cannot be empty"); false
                } else {view.onPagesValidation(""); true})
    }


    private fun isIsbnVerify(isbn: String?): Boolean {
        if (TextUtils.isEmpty(isbn)) {
            view.onISBNValidation("ISBN cannot be empty")
            return false
        } else if (isbn!!.length != 13) {
            view.onISBNValidation("ISBN number must have 13 digits")
            return false
        } else view.onISBNValidation("")
        return true
    }

    private fun removeBook(book: Book): Disposable {
        return model.removeBook(book)
            .subscribeOn(schedulers.backgroundScheduler())
            .observeOn(schedulers.androidThreadScheduler())
            .subscribe({ model.finish() }, { throwable ->
                Timber.e(throwable, throwable.message)
            })
    }
}