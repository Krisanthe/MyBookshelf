package krisanthe.exercise.mybookshelf.screens

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import krisanthe.exercise.mybookshelf.R
import krisanthe.exercise.mybookshelf.screens.books.BooksActivity
import krisanthe.exercise.mybookshelf.utils.UiUtils

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.a_main)
        UiUtils.launchFragment(this, BooksActivity())
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (supportFragmentManager.backStackEntryCount == 0) {
            System.exit(1)
        }
    }
}
