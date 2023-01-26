package com.noscale.core.util

import android.content.pm.PackageManager
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.noscale.core.R

fun View.showSnackbar(snackbarText: String, timeLength: Int) = Snackbar.make(
    this,
    snackbarText,
    timeLength
).show()

fun RecyclerView.spacing() {
    val itemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
    itemDecoration.setDrawable(
        ContextCompat.getDrawable(context,
        R.drawable.item_space_decoration
    )!!)

    addItemDecoration(itemDecoration)
}

fun AppCompatActivity.hasPermissions(vararg permissions: String): Boolean = permissions.all {
    ActivityCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
}