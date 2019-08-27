package krisanthe.exercise.mybookshelf.application

import android.content.Context
import dagger.Module
import dagger.Provides
import io.objectbox.BoxStore
import krisanthe.exercise.mybookshelf.model.Book
import io.objectbox.Box
import io.objectbox.kotlin.boxFor
import krisanthe.exercise.mybookshelf.model.MyObjectBox

@Module
class DatabaseModule {

    @AppScope
    @Provides
    fun provideDatabaseStore(context: Context): BoxStore {
        return MyObjectBox.builder().androidContext(context.applicationContext).build()
    }

    @AppScope
    @Provides
    fun provideDatabase(boxStore: BoxStore): Box<Book>  {
        return boxStore.boxFor(Book::class)
    }
}