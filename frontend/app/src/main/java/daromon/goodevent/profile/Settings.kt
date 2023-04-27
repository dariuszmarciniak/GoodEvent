package daromon.goodevent.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import daromon.goodevent.Login
import daromon.goodevent.R
import daromon.goodevent.Register
import daromon.goodevent.model.EventData
import daromon.goodevent.model.UserModel
import daromon.goodevent.retrofit.RetrofitService
import daromon.goodevent.retrofit.UserAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.String
import java.util.logging.Level
import java.util.logging.Logger

class Settings : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        initializeComponents()
    }

    private fun initializeComponents() {
        val backButtonSettings = findViewById<Button>(R.id.backButtonSettings)
        val confirmButton = findViewById<Button>(R.id.confirmButton)
        val passwordEditText = findViewById<EditText>(R.id.changePassword)
        val changePasswordConfirmEditText = findViewById<EditText>(R.id.changePasswordConfirm)
        val retrofitService = RetrofitService()
        val userAPI = retrofitService.getRetrofit()?.create(UserAPI::class.java)

        backButtonSettings.setOnClickListener {
            val username = intent.getStringExtra("username")
            val token = intent.getStringExtra("token")
            val userId = intent.getStringExtra("userId")
            val intent = Intent(applicationContext, Profile::class.java)
            intent.putExtra("username", username)
            intent.putExtra("userId",userId)
            intent.putExtra("token", token)
            startActivity(intent)
        }


        confirmButton.setOnClickListener {
            val changePassword = String.valueOf(passwordEditText.text)
            val changePasswordConfirm = String.valueOf(changePasswordConfirmEditText.text)
            if (changePassword != changePasswordConfirm) {
                Toast.makeText(baseContext, "Passwords must be the same!", Toast.LENGTH_SHORT)
                    .show();
            }
            else
            {
                val username = intent.getStringExtra("username")
                val token = intent.getStringExtra("token")
                val userId = intent.getStringExtra("userId")
                val user = UserModel()
                user.username = username
                user.password = changePassword
                userAPI?.updatePassword("Bearer $token", user)
                    ?.enqueue(object : Callback<Boolean> {
                        override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                            Toast.makeText(baseContext, "Password changed", Toast.LENGTH_SHORT).show()
                            val intent = Intent(applicationContext, Profile::class.java)
                            intent.putExtra("username", username)
                            intent.putExtra("userId",userId)
                            intent.putExtra("token", token)
                            startActivity(intent)
                        }

                        override fun onFailure(call: Call<Boolean>, t: Throwable) {
                            Toast.makeText(baseContext, "Error!", Toast.LENGTH_SHORT).show()
                            Logger.getLogger(Register::class.java.name)
                                .log(Level.SEVERE, "Error occurred", t)
                        }
                    })
            }
        }
    }

    override fun onBackPressed() {
        val username = intent.getStringExtra("username")
        val token = intent.getStringExtra("token")
        val userId = intent.getStringExtra("userId")
        val intent = Intent(applicationContext, Profile::class.java)
        intent.putExtra("username", username)
        intent.putExtra("userId",userId)
        intent.putExtra("token", token)
        startActivity(intent)
    }


}

