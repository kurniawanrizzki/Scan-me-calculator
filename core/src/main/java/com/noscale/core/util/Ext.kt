package com.noscale.core.util

import android.view.View
import androidx.annotation.IdRes
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar

fun AppCompatActivity.setupActionBar(@IdRes toolbarId: Int, action: ActionBar.() -> Unit) {
    setSupportActionBar(findViewById(toolbarId))
    supportActionBar?.run { action }
}

fun View.showSnackbar(snackbarText: String, timeLength: Int) = Snackbar.make(
    this,
    snackbarText,
    timeLength
).show()