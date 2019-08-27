package krisanthe.exercise.mybookshelf.screens.details.dagger

import dagger.Component
import krisanthe.exercise.mybookshelf.application.AppComponent
import krisanthe.exercise.mybookshelf.screens.details.DetailsActivity

@DetailsScope
@Component(modules = [DetailsModule::class], dependencies = [AppComponent::class])
interface DetailsComponent {
    fun inject(context: DetailsActivity)
}