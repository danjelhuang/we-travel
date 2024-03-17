package com.example.wetravel.database

import com.google.firebase.firestore.FirebaseFirestore

object DatabaseManager {
    val db: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }
}
