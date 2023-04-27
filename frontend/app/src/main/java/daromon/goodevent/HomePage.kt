package daromon.goodevent

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.cardview.widget.CardView
import daromon.goodevent.event.AddEvent
import daromon.goodevent.event.AllEvents
import daromon.goodevent.model.EventData
import daromon.goodevent.profile.Profile


class HomePage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)

        initializeComponents()
    }

    private fun initializeComponents() {
        val addEventCardView = findViewById<CardView>(R.id.addEventCardView)
        val allEventsCardView = findViewById<CardView>(R.id.allEventsCardView)
        val logoutCardView = findViewById<CardView>(R.id.logoutCardView)
        val profileCardView = findViewById<CardView>(R.id.profileCardView)
        logoutCardView.setOnClickListener {
            val intent = Intent(applicationContext, Login::class.java)
            startActivity(intent)
        }

        addEventCardView.setOnClickListener {
            val username = intent.getStringExtra("username")
            val token = intent.getStringExtra("token")
            val userId = intent.getStringExtra("userId")
            val intent = Intent(applicationContext, AddEvent::class.java)
            intent.putExtra("username", username)
            intent.putExtra("userId",userId)
            intent.putExtra("token", token)

            startActivity(intent)
        }
        allEventsCardView.setOnClickListener {
            val username = intent.getStringExtra("username")
            val token = intent.getStringExtra("token")
            val userId = intent.getStringExtra("userId")
            val intent = Intent(applicationContext, AllEvents::class.java)
            intent.putExtra("username", username)
            intent.putExtra("userId",userId)
            intent.putExtra("token", token)
            startActivity(intent)
        }

        profileCardView.setOnClickListener {
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

}