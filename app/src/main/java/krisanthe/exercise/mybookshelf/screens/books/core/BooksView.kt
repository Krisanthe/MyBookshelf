package krisanthe.exercise.mybookshelf.screens.books.core

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import krisanthe.exercise.mybookshelf.R
import krisanthe.exercise.mybookshelf.model.Book
import krisanthe.exercise.mybookshelf.screens.books.BooksActivity
import krisanthe.exercise.mybookshelf.screens.books.list.BooksAdapter
import java.util.ArrayList
import androidx.recyclerview.widget.DividerItemDecoration


class BooksView(val context: BooksActivity) {

    @BindView(R.id.rv_books_list)
    lateinit var list: RecyclerView

    @BindView(R.id.fab_add)
    lateinit var addBook: FloatingActionButton

    private val newBook = PublishSubject.create<Boolean>()
    var view: View
    var adapter: BooksAdapter

    init {
        val parent = FrameLayout(context.activity as Context)
        parent.layoutParams = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        view = LayoutInflater.from(context.activity).inflate(R.layout.a_book_list, parent, true)
        ButterKnife.bind(this, view)

        list.addItemDecoration(DividerItemDecoration(context.activity, LinearLayoutManager.VERTICAL))
        list.setHasFixedSize(true)

        addBook.setOnClickListener { newBook.onNext(true) }

        adapter = BooksAdapter()

        list.adapter = adapter
        val layoutManager = LinearLayoutManager(context.activity)
        list.layoutManager = layoutManager

    }

    fun getClickObservable(): Observable<Int> {
        return adapter.observableClicks()
    }

    fun getNewBookObservable(): Observable<Boolean> {
        return newBook
    }

    fun view(): View {
        return view
    }

    fun refreshAdapter(books: ArrayList<Book>) {
        adapter.swapAdapter(books)
    }

}