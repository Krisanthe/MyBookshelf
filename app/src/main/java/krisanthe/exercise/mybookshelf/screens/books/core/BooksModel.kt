package krisanthe.exercise.mybookshelf.screens.books.core

import io.objectbox.Box
import io.reactivex.Observable
import krisanthe.exercise.mybookshelf.model.Book
import krisanthe.exercise.mybookshelf.model.Book_
import krisanthe.exercise.mybookshelf.screens.books.BooksActivity

class BooksModel(val context: BooksActivity, private val bookDB: Box<Book>) {

    fun provideBookList(): Observable<List<Book>> {
        return Observable.just(bookDB.query().order(Book_.title).build().find())
    }

    fun goToEdit(book: Book) {
        context.goToEdit(book)
    }
}