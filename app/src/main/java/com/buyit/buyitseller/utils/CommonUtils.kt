package com.buyit.buyitseller.utils

import android.annotation.SuppressLint
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

object CommonUtils {
    @SuppressLint("StaticFieldLeak")
    val db = Firebase.firestore
    val auth = Firebase.auth
}