package krisanthe.exercise.mybookshelf.screens.details.dagger

import dagger.Module
import dagger.Provides
import io.objectbox.Box
import io.reactivex.disposables.CompositeDisposable
import krisanthe.exercise.mybookshelf.model.Book
import krisanthe.exercise.mybookshelf.screens.details.DetailsActivity
import krisanthe.exercise.mybookshelf.screens.details.core.DetailsModel
import krisanthe.exercise.mybookshelf.screens.details.core.DetailsPresenter
import krisanthe.exercise.mybookshelf.screens.details.core.DetailsView
import krisanthe.exercise.mybookshelf.utils.AppSchedulers


@Module
class DetailsModule(val context: DetailsActivity, val book: Book) {

    @DetailsScope
    @Provides
    fun provideContext() : DetailsActivity {
        return context
    }

    @DetailsScope
    @Provides
    fun provideModel(bookDB : Box<Book>): DetailsModel {
        return DetailsModel(context, bookDB)
    }

    @DetailsScope
    @Provides
    fun provideView(): DetailsView {
        return DetailsView(context, book)
    }

    @DetailsScope
    @Provides
    fun providePresenter(model: DetailsModel, view: DetailsView, schedulers : AppSchedulers): DetailsPresenter {
        val subscriptions = CompositeDisposable()
        return DetailsPresenter(schedulers, model, view, subscriptions)
    }
}