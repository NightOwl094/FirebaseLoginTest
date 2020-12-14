package com.nightowl094.firebaselogintest.google_login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.nightowl094.firebaselogintest.R
import com.nightowl094.firebaselogintest.databinding.ActivityGoogleLoginBinding

class GoogleLoginActivity : AppCompatActivity() {

    companion object {
        const val GOOGLE_LOGIN_REQUEST_CODE = 1111
    }

    private lateinit var binding: ActivityGoogleLoginBinding

    private val auth by lazy { FirebaseAuth.getInstance() }
    private val gsOptions by lazy {
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build();
    }
    private val gsClient by lazy {
        GoogleSignIn.getClient(this, gsOptions)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityGoogleLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.googleLoginButton.setOnClickListener {
            signIn()
        }

    }

    override fun onStart() {
        super.onStart()

        auth.currentUser?.run {
            Snackbar.make(binding.root, "자동 로그인 성공", Snackbar.LENGTH_LONG).show()
        }

    }


    private fun signIn() {
        startActivityForResult(gsClient.signInIntent, GOOGLE_LOGIN_REQUEST_CODE)
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)

        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->

                if (task.isSuccessful) {
                    Snackbar.make(binding.root, "Authentication Success ${auth.currentUser}", Snackbar.LENGTH_SHORT).show()
                } else {
                    Snackbar.make(binding.root, "Authentication Failed", Snackbar.LENGTH_SHORT).show()
                }

            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == GOOGLE_LOGIN_REQUEST_CODE) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)

            try {
                task.getResult(ApiException::class.java)?.run {
                    idToken?.run {
                        firebaseAuthWithGoogle(this)
                    }
                }
            } catch (e: Exception) {
                Unit
            }
        }

    }
}