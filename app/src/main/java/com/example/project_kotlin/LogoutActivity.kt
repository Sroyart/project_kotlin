package com.example.project_kotlin

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class LogoutActivity : AppCompatActivity() {
    lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logout)

        val btnLogout: Button = findViewById(R.id.btn_logout)


        preferences = getSharedPreferences("JWT", Context.MODE_PRIVATE)

        btnLogout.setOnClickListener {
            println("Deconnection")
            val editor: SharedPreferences.Editor = preferences.edit()
            editor.clear()
            editor.apply()

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }


    }
}