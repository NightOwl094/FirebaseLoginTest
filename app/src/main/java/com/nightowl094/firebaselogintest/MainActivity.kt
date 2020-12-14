package com.nightowl094.firebaselogintest

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nightowl094.firebaselogintest.databinding.ActivityMainBinding
import com.nightowl094.firebaselogintest.email_login.EmailLoginActivity
import com.nightowl094.firebaselogintest.google_login.GoogleLoginActivity

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)

        initButtons()
    }

    private fun initButtons() {
        binding.apply {
            googleLoginButton.setOnClickListener {
                startActivity(Intent(this@MainActivity, GoogleLoginActivity::class.java))
            }

            emailLoginButton.setOnClickListener {
                startActivity(Intent(this@MainActivity, EmailLoginActivity::class.java))
            }
        }
    }

}