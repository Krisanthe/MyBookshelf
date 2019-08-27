package krisanthe.exercise.mybookshelf.screens.details.core

import io.objectbox.Box
import io.reactivex.Observable
import krisanthe.exercise.mybookshelf.model.Book
import krisanthe.exercise.mybookshelf.model.Book_
import krisanthe.exercise.mybookshelf.screens.details.DetailsActivity
import timber.log.Timber

class DetailsModel(val context: DetailsActivity, private val bookDB: Box<Book>) {

    fun saveBook(book: Book): Observable<Boolean> {
        return Observable.just(createOrUpdateBook(book))
    }

    fun removeBook(book: Book): Observable<Boolean> {
        return Observable.just(removeBookFromDB(book))
    }

    fun finish() {
        context.finish()
    }

    private fun createOrUpdateBook(book: Book): Boolean {
        return try {
            val oldBook = bookDB.query().equal(Book_.isbn, book.isbn!!).build().findFirst()
            if(oldBook != null) {
                bookDB.query().equal(Book_.isbn, book.isbn!!).build().remove()
            }
            bookDB.put(book)
            true
        } catch (e: Exception) {
            Timber.e("Unable to create or update book")
            false
        }
    }

    private fun removeBookFromDB(book: Book): Boolean {
        return try {
            val oldBook = bookDB.query().equal(Book_.isbn, book.isbn!!).build().findFirst()
            if(oldBook != null) {
                bookDB.query().equal(Book_.isbn, book.isbn!!).build().remove()
            }
            true
        } catch (e: Exception) {
            Timber.e("Unable to create or update book")
            false
        }
    }
}