package com.buyit.buyitseller.utils

import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

fun Fragment.toast(msg: String) {
    Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
}

fun View.hide() {
    visibility = View.GONE
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.setBackgroundColour(color: Int) {
    setBackgroundColor(ContextCompat.getColor(context, color))
}

fun ImageView.setColourFilter(color: Int) {
    setColorFilter(ContextCompat.getColor(context, color))
}

fun Fragment.setStatusBarColor(color: Int) {
    activity!!.window.statusBarColor = ContextCompat.getColor(requireContext(), color)
}