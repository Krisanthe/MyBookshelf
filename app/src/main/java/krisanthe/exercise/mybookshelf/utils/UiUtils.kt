package krisanthe.exercise.mybookshelf.utils

import android.content.Context

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import krisanthe.exercise.mybookshelf.R

object UiUtils {

    fun launchFragment(context: Context, fragmentToLaunch: Fragment) {
        val supportFragmentManager = (context as AppCompatActivity).supportFragmentManager
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, fragmentToLaunch, fragmentToLaunch.tag)
            .addToBackStack(null)
            .commitAllowingStateLoss()
    }
}