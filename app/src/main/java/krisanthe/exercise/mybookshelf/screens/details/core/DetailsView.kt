package krisanthe.exercise.mybookshelf.screens.details.core

import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.RatingBar
import butterknife.BindView
import butterknife.ButterKnife
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import krisanthe.exercise.mybookshelf.R
import krisanthe.exercise.mybookshelf.model.Book
import krisanthe.exercise.mybookshelf.screens.details.DetailsActivity
import krisanthe.exercise.mybookshelf.utils.CodeUtils
import android.widget.Toast


class DetailsView(private val detailsActivity: DetailsActivity, var book: Book) {

    @BindView(R.id.ratingBar)
    lateinit var ratingBar: RatingBar

    @BindView(R.id.et_title)
    lateinit var titleET: TextInputEditText

    @BindView(R.id.et_author)
    lateinit var authorET: TextInputEditText

    @BindView(R.id.et_isbn)
    lateinit var isbnET: TextInputEditText

    @BindView(R.id.et_pages)
    lateinit var pagesET: TextInputEditText

    @BindView(R.id.til_title)
    lateinit var titleTIL: TextInputLayout

    @BindView(R.id.til_author)
    lateinit var authorTIL: TextInputLayout

    @BindView(R.id.til_isbn)
    lateinit var isbnTIL: TextInputLayout

    @BindView(R.id.til_pages)
    lateinit var pagesTIL: TextInputLayout

    @BindView(R.id.bt_save)
    lateinit var save: Button

    @BindView(R.id.bt_remove)
    lateinit var remove: Button

    private var view: View
    private val saveSub = PublishSubject.create<Book>()
    private val removeSub = PublishSubject.create<Book>()

    init {
        val layout = FrameLayout(detailsActivity)
        layout.layoutParams = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        view = LayoutInflater.from(detailsActivity).inflate(R.layout.a_book_modify, layout, true)
        ButterKnife.bind(this, view)

        setupInitialData()
        setupListeners()
        setupWatcher()
    }

    private fun setupInitialData() {
        CodeUtils.ifNotNull(book) { book ->
            titleET.text = getEditable(if (TextUtils.isEmpty(book.title)) "" else book.title!!)
            authorET.text = getEditable(if (TextUtils.isEmpty(book.author)) "" else book.author!! )
            isbnET.text = getEditable(if (TextUtils.isEmpty(book.isbn)) "" else book.isbn!! )
            pagesET.text = getEditable(if(book.pages == 0) "" else book.pages.toString())
            ratingBar.rating = book.rate
        }
    }

    private fun setupListeners() {
        save.setOnClickListener { getCurrentBookValue()
            saveSub.onNext(book)}
        remove.setOnClickListener {removeSub.onNext(book)}
        ratingBar.setOnRatingBarChangeListener { _, rating, _ ->  book.rate = rating}
    }

    private fun getCurrentBookValue() {
        book.title = if (TextUtils.isEmpty(titleET.text)) "" else titleET.text.toString()
        book.author = if (TextUtils.isEmpty(authorET.text)) "" else authorET.text.toString()
        book.pages = if (TextUtils.isEmpty(pagesET.text)) 0 else pagesET.text.toString().toInt()
    }

    private fun setupWatcher() {
        isbnET.afterIsbnTextChanged { book.isbn = it.replace("-", "") }
    }

    fun getSaveObservable(): Observable<Book> {
        return saveSub
    }

    fun getRemoveObservable(): Observable<Book> {
        return removeSub
    }

    private fun getEditable(data: String): Editable {
        return Editable.Factory.getInstance().newEditable(data)
    }

    private fun TextInputEditText.afterIsbnTextChanged(afterTextChanged: (String) -> Unit) {
        this.addTextChangedListener(object : TextWatcher {
            var isDelete: Boolean = false
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                isDelete = (p3 == 0)
            }

            override fun afterTextChanged(editable: Editable?) {
                if(editable != null) {
                    if(!isDelete && (editable.length == 3 || editable.length == 5 || editable.length == 10 || editable.length == 14 )) {
                        editable.append("-")
                    }
                    afterTextChanged.invoke(editable.toString())
                }
            }
        })
    }

    fun view(): View {
        return view
    }

    fun onISBNValidation(message: String) {
        isbnTIL.error = message
    }

    fun onTitleValidation(message: String) {
        titleTIL.error = message
    }

    fun onAuthorValidation(message: String) {
        authorTIL.error = message
    }

    fun onPagesValidation(message: String) {
        pagesTIL.error = message
    }
}