package krisanthe.exercise.mybookshelf.screens.details

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import krisanthe.exercise.mybookshelf.application.AppController
import krisanthe.exercise.mybookshelf.model.Book
import krisanthe.exercise.mybookshelf.screens.details.core.DetailsPresenter
import krisanthe.exercise.mybookshelf.screens.details.core.DetailsView
import krisanthe.exercise.mybookshelf.screens.details.dagger.DaggerDetailsComponent
import krisanthe.exercise.mybookshelf.screens.details.dagger.DetailsModule
import javax.inject.Inject

class DetailsActivity : AppCompatActivity() {

    @Inject
    lateinit var view: DetailsView

    @Inject
    lateinit var presenter: DetailsPresenter

    override fun onCreate(saveInstantState: Bundle?) {
        super.onCreate(saveInstantState)

        val book = intent.extras.get("book") as Book

        DaggerDetailsComponent.builder()
            .appComponent(AppController.appComponent)
            .detailsModule(DetailsModule(this, book)).build().inject(this)

        setContentView(view.view())
        presenter.onCreate()

    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }
}