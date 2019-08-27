package krisanthe.exercise.mybookshelf.screens.books.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.Observable
import io.reactivex.annotations.NonNull
import io.reactivex.subjects.PublishSubject
import krisanthe.exercise.mybookshelf.R
import krisanthe.exercise.mybookshelf.model.Book
import java.util.*

class BooksAdapter : RecyclerView.Adapter<BookViewHolder>() {

    private val itemClicks = PublishSubject.create<Int>()
    var bookList: ArrayList<Book>? = ArrayList()

    override fun getItemCount(): Int {
        return if (bookList != null && bookList!!.size > 0)  {
            bookList!!.size
        } else {
             0
        }
    }

    @NonNull
    override fun onCreateViewHolder(@NonNull viewGroup: ViewGroup, i: Int): BookViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.i_book, viewGroup, false)
        return BookViewHolder(view, itemClicks)
    }

    override
    fun onBindViewHolder(@NonNull bookViewHolder: BookViewHolder, i: Int) {
        bookViewHolder.bind(bookList!![i])
    }

    fun observableClicks(): Observable<Int> {
        return itemClicks
    }

    fun swapAdapter(books: ArrayList<Book>) {
        this.bookList!!.clear()
        this.bookList!!.addAll(books)
        notifyDataSetChanged()
    }
}