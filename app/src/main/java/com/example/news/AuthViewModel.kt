package com.example.news

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class AuthViewModel : ViewModel() {
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    var user by mutableStateOf(firebaseAuth.currentUser)
        private set

    var error by mutableStateOf<String?>(null)
        private set

    fun signIn(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    user = firebaseAuth.currentUser
                    error = null
                } else {
                    error = task.exception?.localizedMessage
                }
            }
    }

    fun signUp(email: String, password: String) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    user = firebaseAuth.currentUser
                    error = null
                } else {
                    error = task.exception?.localizedMessage
                }
            }
    }

    fun signOut() {
        firebaseAuth.signOut()
        user = null
    }

    companion object
}
