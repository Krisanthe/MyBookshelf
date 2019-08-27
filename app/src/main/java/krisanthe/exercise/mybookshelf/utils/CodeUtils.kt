package krisanthe.exercise.mybookshelf.utils

class CodeUtils {
    companion object {

        fun <T1> ifNotNull(value1: T1?, notNull: (T1) -> (Unit)) {
            if (value1 != null) {
                notNull(value1)
            }
        }
    }
}