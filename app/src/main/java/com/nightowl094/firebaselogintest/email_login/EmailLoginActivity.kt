package com.nightowl094.firebaselogintest.email_login

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.nightowl094.firebaselogintest.databinding.ActivityEmailLoginBinding

class EmailLoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEmailLoginBinding

    private val auth by lazy { FirebaseAuth.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEmailLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            signInButton.setOnClickListener {
                val email = emailForm.text?.toString()
                val password = passwordForm.text?.toString()

                email?.let { _email ->
                    password?.let { _password ->
                        loginUser(_email, _password)
                    }
                }
            }

            signUpButton.setOnClickListener {
                val email = emailForm.text?.toString()
                val password = passwordForm.text?.toString()

                email?.let { _email ->
                    password?.let { _password ->
                        createUser(_email, _password)
                    }
                }

            }
        }
    }

    private fun createUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                try {
                    if (task.isSuccessful) {
                        Snackbar.make(binding.root, "Sign up success", Snackbar.LENGTH_LONG).show()
                    } else {
                        Log.d("ttt", "EmailLoginActivity createUser: ${task.exception}")
                        Snackbar.make(binding.root, "Sign up failed ${task.exception}", Snackbar.LENGTH_LONG).show()
                    }
                } catch (e: FirebaseAuthUserCollisionException) {
                    Snackbar.make(binding.root, "is duplicated email address", Snackbar.LENGTH_LONG).show()
                }

            }
    }

    private fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Snackbar.make(binding.root, "Sign in success", Snackbar.LENGTH_LONG).show()
                } else {
                    Log.d("ttt", "EmailLoginActivity loginUser: ${task.exception}")
                    Snackbar.make(binding.root, "Sign in failed ${task.exception}", Snackbar.LENGTH_LONG).show()
                }
            }
    }


}