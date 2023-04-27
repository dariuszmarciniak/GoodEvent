package daromon.goodevent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.content.Intent
import android.widget.EditText
import daromon.goodevent.model.UserModel
import daromon.goodevent.retrofit.RetrofitService
import daromon.goodevent.retrofit.UserAPI
import android.widget.Toast
import daromon.goodevent.retrofit.AuthResponse

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.String


class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initializeComponents()
    }


    private fun initializeComponents() {
        val loginButton = findViewById<Button>(R.id.loginButton)
        val loginEditText = findViewById<EditText>(R.id.loginEditText)
        val passwordEditText = findViewById<EditText>(R.id.passwordEditText)
        val retrofitService = RetrofitService()
        val userAPI = retrofitService.getRetrofit()?.create(UserAPI::class.java)
        loginButton.setOnClickListener {
            val username = String.valueOf(loginEditText.text)
            val password = String.valueOf(passwordEditText.text)
            val user = UserModel()
            user.username = username
            user.password = password

            userAPI?.login(user)?.enqueue(object : Callback<AuthResponse> {
                override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                    if(response.isSuccessful)
                    {
                        val token = response.body()?.getAccessToken()
                        val user = response.body()?.getUsername()
                        val userId = response.body()?.getUserId()
                        val intent = Intent(applicationContext, HomePage::class.java)
                        intent.putExtra("username", user)
                        intent.putExtra("token", token.toString())
                        intent.putExtra("userId",userId.toString())
                        startActivity(intent)
                    }
                    else
                        Toast.makeText(baseContext, "Try again!", Toast.LENGTH_SHORT)
                            .show()
                }

                override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                    Toast.makeText(baseContext, "Failed to connect to the server!", Toast.LENGTH_SHORT)
                        .show()
                }
            })


        }
        val registerButton = findViewById<Button>(R.id.registerButton)
        registerButton.setOnClickListener {
            val intent = Intent(applicationContext, Register::class.java)
            startActivity(intent)
        }
    }
}


