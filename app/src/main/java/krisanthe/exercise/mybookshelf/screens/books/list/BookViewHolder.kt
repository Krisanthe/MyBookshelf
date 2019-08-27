package krisanthe.exercise.mybookshelf.screens.books.list

import android.text.TextUtils
import android.view.View
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import io.reactivex.subjects.PublishSubject
import krisanthe.exercise.mybookshelf.R
import krisanthe.exercise.mybookshelf.model.Book
import krisanthe.exercise.mybookshelf.utils.CodeUtils
import java.util.*

class BookViewHolder(val view: View, clickedSub: PublishSubject<Int>) : RecyclerView.ViewHolder(view) {

    @BindView(R.id.item_book_title)
    lateinit var bookTitle: TextView

    @BindView(R.id.item_book_author)
    lateinit var bookAuthor: TextView

    @BindView(R.id.item_book_isbn)
    lateinit var bookIsbn: TextView

    @BindView(R.id.item_book_pages)
    lateinit var bookPages: TextView

    @BindView(R.id.item_book_rate)
    lateinit var bookRate: RatingBar

    init {
        ButterKnife.bind(this, view)
        view.setOnClickListener { clickedSub.onNext(adapterPosition) }

    }

    internal fun bind(mBook: Book) {
        CodeUtils.ifNotNull(mBook) { book ->
            bookTitle.text = if (TextUtils.isEmpty(book.title)) "title missing" else book.title
            bookAuthor.text = getFormattedText(R.string.author_placeholder, book.author)
            bookPages.text = getFormattedText(R.string.pages_placeholder, book.pages.toString())
            bookIsbn.text = getFormattedText(R.string.isbn_placeholder, getFormattedIsbn(book.isbn!!))
            bookRate.rating = book.rate
        }
    }

    private fun getFormattedText(stringId: Int, content: String?): String {
        return if (TextUtils.isEmpty(content)) "content missing" else view.context.resources.getString(stringId, content)
    }

    private fun getFormattedIsbn(isbn: String): String {
        return StringBuilder(isbn).insert(3, "-").insert(5, "-")
            .insert(10, "-").insert(14, "-")
            .toString().substring(0, 16).toUpperCase(Locale.getDefault())
    }
}