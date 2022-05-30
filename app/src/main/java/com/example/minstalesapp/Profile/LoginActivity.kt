package com.example.minstalesapp.Profile

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.*
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.minstalesapp.R
import com.example.minstalesapp.databinding.ActivityLoginBinding
import kotlinx.android.synthetic.main.activity_login.*
import org.apache.http.conn.ConnectTimeoutException
import org.json.JSONException
import org.json.JSONObject
import org.xmlpull.v1.XmlPullParserException
import java.net.ConnectException
import java.net.MalformedURLException
import java.net.SocketException
import java.net.SocketTimeoutException


class LoginActivity: AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val queue = Volley.newRequestQueue(this)
        val url = "http://10.0.2.2:8000/api/user/login"
        val sharedPreferences = getPreferences(MODE_PRIVATE)

        loginButton.setOnClickListener {
            val params = HashMap<String,String>()
            params["username"] = binding.mailTextInput.text.toString()
            params["password"] = binding.passwordTextInput.text.toString()
            val jsonObject = JSONObject(params as Map<*, *>?)

            // Request a string response from the provided URL.
            val request = JsonObjectRequest(
                Request.Method.POST, url, jsonObject,
                { response ->
                    Log.i("(SUCCESS)Post response", response.toString())
                    val editor = sharedPreferences.edit()
                    editor.putString("user-token", response["token"].toString())
                    editor.commit()
                    val intent = Intent(this, ProfileActivity::class.java)
                    startActivity(intent)
                    finish()
                },
                { response ->
                    Log.i("(ERROR)Post response", getVolleyError(response))
                    passwordTextInput.setText("")
                    Toast.makeText(this, getVolleyError(response), Toast.LENGTH_SHORT).show()
                }
            )

            // Add the request to the RequestQueue.
            queue.add(request)
        }
    }

    fun Activity.getVolleyError(error: VolleyError): String {
        var errorMsg = ""
        if (error is NoConnectionError) {
            val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            var activeNetwork: NetworkInfo? = null
            activeNetwork = cm.activeNetworkInfo
            errorMsg = if (activeNetwork != null && activeNetwork.isConnectedOrConnecting) {
                "Server is not connected to the internet. Please try again"
            } else {
                "Your device is not connected to internet.please try again with active internet connection"
            }
        } else if (error is NetworkError || error.cause is ConnectException) {
            errorMsg = "Your device is not connected to internet.please try again with active internet connection"
        } else if (error.cause is MalformedURLException) {
            errorMsg = "That was a bad request please try again…"
        } else if (error is ParseError || error.cause is IllegalStateException || error.cause is JSONException || error.cause is XmlPullParserException) {
            errorMsg = "There was an error parsing data…"
        } else if (error.cause is OutOfMemoryError) {
            errorMsg = "Device out of memory"
        } else if (error is AuthFailureError) {
            errorMsg = "Failed to authenticate user at the server"
        } else if (error is ServerError || error.cause is ServerError) {
            errorMsg = "Internal server error occurred please try again...."
        } else if (error is TimeoutError || error.cause is SocketTimeoutException || error.cause is ConnectTimeoutException || error.cause is SocketException || (error.cause!!.message != null && error.cause!!.message!!.contains(
                "Your connection has timed out, please try again"
            ))
        ) {
            errorMsg = "Your connection has timed out, please try again"
        } else {
            errorMsg = "An unknown error occurred during the operation, please try again"
        }
        return errorMsg
    }
}