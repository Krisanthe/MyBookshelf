package krisanthe.exercise.mybookshelf.screens.books

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import krisanthe.exercise.mybookshelf.application.AppController
import krisanthe.exercise.mybookshelf.model.Book
import krisanthe.exercise.mybookshelf.screens.books.core.BooksPresenter
import krisanthe.exercise.mybookshelf.screens.books.core.BooksView
import krisanthe.exercise.mybookshelf.screens.books.dagger.BooksModule
import javax.inject.Inject
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.NonNull
import krisanthe.exercise.mybookshelf.screens.books.dagger.DaggerBooksComponent
import krisanthe.exercise.mybookshelf.screens.details.DetailsActivity

class BooksActivity : Fragment() {

    @Inject
    lateinit var presenter: BooksPresenter

    @Inject
    lateinit var view: BooksView

    override fun onCreate(saveInstantState: Bundle?) {
        super.onCreate(saveInstantState)

        DaggerBooksComponent.builder()
            .appComponent(AppController.appComponent)
            .booksModule(BooksModule(this))
            .build()
            .inject(this)

        presenter.onCreate()
    }

    override fun onResume() {
        super.onResume()
        presenter.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    override fun onCreateView(
        @NonNull inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return view.view()
    }

    fun goToEdit(book: Book) {
        val i = Intent(activity, DetailsActivity::class.java)
        i.putExtra("book", book)
        startActivity(i)
    }

}

