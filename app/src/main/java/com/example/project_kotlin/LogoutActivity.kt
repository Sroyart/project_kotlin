package com.example.project_kotlin

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.auth0.android.jwt.JWT

class LogoutActivity : AppCompatActivity() {
    lateinit var preferences: SharedPreferences
    private lateinit var tvUserFirstName: TextView
    private lateinit var tvUserLastName: TextView
    private lateinit var tvUserRole: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logout)

        tvUserFirstName = findViewById(R.id.tv_user_info_firstName)
        tvUserLastName = findViewById(R.id.tv_user_info_LastName)
        tvUserRole = findViewById(R.id.tv_user_role)

        val btnLogout: Button = findViewById(R.id.btn_logout)


        preferences = getSharedPreferences("JWT", Context.MODE_PRIVATE)

        val myJwt = preferences.getString("JWT", "")
        println("JWT null")
        val jwt = JWT(myJwt.toString())
        tvUserFirstName.text = jwt.getClaim("firstName").asString()
        tvUserLastName.text = jwt.getClaim("lastName").asString()
        tvUserRole.text = jwt.getClaim("appUserRole").asString()



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