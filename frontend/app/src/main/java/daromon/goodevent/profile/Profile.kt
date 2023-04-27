package daromon.goodevent.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import daromon.goodevent.HomePage
import daromon.goodevent.R
import daromon.goodevent.model.EventData

class Profile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        initializeComponents()
    }

    private fun initializeComponents() {
        val favoritesButton = findViewById<Button>(R.id.favoritesButton)
        val settingsButton = findViewById<Button>(R.id.settingsButton)
        val backProfileButton = findViewById<Button>(R.id.backProfileButton)

        backProfileButton.setOnClickListener {
            val username = intent.getStringExtra("username")
            val token = intent.getStringExtra("token")
            val userId = intent.getStringExtra("userId")
            val intent = Intent(applicationContext, HomePage::class.java)
            intent.putExtra("username", username)
            intent.putExtra("userId",userId)
            intent.putExtra("token", token)
            startActivity(intent)
        }

        favoritesButton.setOnClickListener {
            val username = intent.getStringExtra("username")
            val token = intent.getStringExtra("token")
            val userId = intent.getStringExtra("userId")
            val intent = Intent(applicationContext, MyEvents::class.java)
            intent.putExtra("username", username)
            intent.putExtra("userId",userId)
            intent.putExtra("token", token)
            startActivity(intent)
        }

        settingsButton.setOnClickListener {
            val username = intent.getStringExtra("username")
            val token = intent.getStringExtra("token")
            val userId = intent.getStringExtra("userId")
            val intent = Intent(applicationContext, Settings::class.java)
            intent.putExtra("username", username)
            intent.putExtra("userId",userId)
            intent.putExtra("token", token)
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
        val username = intent.getStringExtra("username")
        val token = intent.getStringExtra("token")
        val userId = intent.getStringExtra("userId")
        val intent = Intent(applicationContext, HomePage::class.java)
        intent.putExtra("username", username)
        intent.putExtra("userId",userId)
        intent.putExtra("token", token)
        startActivity(intent)
    }
}
