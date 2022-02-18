package com.example.minstalesapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject

class LoginActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val queue = Volley.newRequestQueue(this)
        val url = "http://10.0.2.2:8000/api/users/login"

        loginButton.setOnClickListener {
            val params = HashMap<String,String>()
            params["username"] = mailTextInput.text.toString()
            params["password"] = passwordTextInput.text.toString()
            val jsonObject = JSONObject(params as Map<*, *>?)

            // Request a string response from the provided URL.
            val request = JsonObjectRequest(
                Request.Method.POST, url, jsonObject,
                { response ->
                    Log.i("(SUCCESS)Post response", response.toString())
                    finish()
                },
                { response ->
                    Log.i("(ERROR)Post response", response.toString())
                    passwordTextInput.setText("")
                    Toast.makeText(this, response.toString(), Toast.LENGTH_SHORT).show()
                }
            )

            // Add the request to the RequestQueue.
            queue.add(request)
        }
    }
}