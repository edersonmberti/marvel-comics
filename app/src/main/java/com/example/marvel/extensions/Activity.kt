package com.example.marvel.extensions

import android.app.Activity
import android.widget.Toast
import com.example.marvel.R

fun Activity?.showToast(
    messageId: Int = R.string.something_went_wrong,
    length: Int = Toast.LENGTH_LONG
) {
    Toast.makeText(this, messageId, length).show()
}