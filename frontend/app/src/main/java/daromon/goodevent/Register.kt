package daromon.goodevent

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import daromon.goodevent.model.UserModel
import daromon.goodevent.retrofit.RetrofitService
import daromon.goodevent.retrofit.UserAPI
import retrofit2.Call
import retrofit2.Response

import java.lang.String
import java.util.logging.Level
import java.util.logging.Logger



class Register : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        initializeComponents()
    }


    private fun initializeComponents() {
        val usernameEditText = findViewById<EditText>(R.id.loginEditText)
        val passwordEditText = findViewById<EditText>(R.id.passwordEditText)
        val passwordConfirmedEditText = findViewById<EditText>(R.id.passwordConfirmEditText)

        val retrofitService = RetrofitService()
        val userAPI = retrofitService.getRetrofit()?.create(UserAPI::class.java)

        val registerButton = findViewById<Button>(R.id.registerButton)
        registerButton.setOnClickListener {
            val username = String.valueOf(usernameEditText.text)
            val password = String.valueOf(passwordEditText.text)
            if(!isValidUsername(username))
            {
                Toast.makeText(baseContext,
                    "The username should contain at least 3 alphanumeric characters",
                    Toast.LENGTH_SHORT)
                    .show();
            }
            if (!isValidPassword(password))
            {
                Toast.makeText(baseContext,
                    "The password should contain eight characters, including: one number, one lowercase letter, one uppercase letter, one special character and cannot contain spaces",
                    Toast.LENGTH_SHORT)
                    .show();
            }
            else
            {
                val passwordConfirmed = String.valueOf(passwordConfirmedEditText.text)

                if (password != passwordConfirmed) {
                    Toast.makeText(baseContext, "Passwords must be the same!", Toast.LENGTH_SHORT)
                        .show();
                } else {
                    val user = UserModel()
                    user.username = username
                    user.password = password
                    userAPI?.save(user)
                        ?.enqueue(object : retrofit2.Callback<Boolean> {
                            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                                if(response.body()==false)
                                {
                                    Toast.makeText(baseContext, "Login is taken!", Toast.LENGTH_SHORT)
                                        .show()
                                }
                                else
                                {
                                    Toast.makeText(baseContext, "The account has been created!", Toast.LENGTH_SHORT)
                                        .show()
                                    val intent = Intent(applicationContext, Login::class.java)
                                    startActivity(intent)
                                }

                            }
                            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                                Toast.makeText(baseContext, "Save failed!", Toast.LENGTH_SHORT).show()
                                Logger.getLogger(Register::class.java.name)
                                    .log(Level.SEVERE, "Error occurred", t)
                            }
                        })
                }
            }

        }


        val backButton = findViewById<Button>(R.id.backButton)
        backButton.setOnClickListener {
            val intent = Intent(applicationContext, Login::class.java)
            startActivity(intent)
        }
    }

    fun isValidPassword(password: kotlin.String): Boolean {
         val passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,25}\$"
       // val passwordRegex = "^[a-zA-Z0-9]{3,25}$"
        return password.matches(passwordRegex.toRegex())
    }

    fun isValidUsername(username: kotlin.String): Boolean {
        val usernameRegex = "^[a-zA-Z0-9]{3,25}$"
        return username.matches(usernameRegex.toRegex())
    }
}