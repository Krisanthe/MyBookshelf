package krisanthe.exercise.mybookshelf.model

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.Unique
import java.io.Serializable

@Entity
data class Book(

    @Id var id: Long = 0,

    var title: String? = null,

    var author: String? = null,

    @Unique var isbn: String? = null,

    var pages: Int = 0,

    var rate: Float = 0f
) : Serializable
