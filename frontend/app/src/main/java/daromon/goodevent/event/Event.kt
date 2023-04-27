package daromon.goodevent.event

import android.content.Intent
import android.graphics.BitmapFactory
import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.view.View
import android.widget.*
import daromon.goodevent.R
import java.util.*
import androidx.fragment.app.FragmentActivity
import daromon.goodevent.HomePage
import daromon.goodevent.model.EventData
import daromon.goodevent.model.UserModel
import daromon.goodevent.retrofit.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.logging.Level
import java.util.logging.Logger

class Event : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event)


        val eventTitle = findViewById<TextView>(R.id.eventTitle)
        val eventPlace = findViewById<TextView>(R.id.eventPlace)
        val eventDate = findViewById<TextView>(R.id.eventDate)
        val eventCreator = findViewById<TextView>(R.id.eventCreator)
        val eventImage = findViewById<ImageView>(R.id.eventImage)
        val backButtonEvent = findViewById<Button>(R.id.backButtonEvent)
        val showLocationButton = findViewById<Button>(R.id.mapButton)
        val descriptionEvent = findViewById<TextView>(R.id.description)
        val favouritesButtonEvent = findViewById<Button>(R.id.favouritesButtonEvent)
        val deleteButtonAdmin = findViewById<Button>(R.id.deleteButtonAdmin)


        val eventId = intent.getStringExtra("id")
        val username = intent.getStringExtra("username")
        val token = intent.getStringExtra("token")
        val userId = intent.getStringExtra("userId")

        if(username == "admin")
        {
            deleteButtonAdmin.visibility = View.VISIBLE
        }
        eventTitle.text = intent.getStringExtra("title")
        eventPlace.text = intent.getStringExtra("place")
        eventDate.text = intent.getStringExtra("date")
        eventCreator.text = intent.getStringExtra("creator")
        descriptionEvent.text = intent.getStringExtra("description")
        var eventFavouritesStatus = intent.getStringExtra("favStatus")
        val imageBytes = Base64.decode(intent.getStringExtra("image"), Base64.DEFAULT)
        val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
        eventImage.setImageBitmap(decodedImage)


        val retrofitService = RetrofitService()
        val userAPI = retrofitService.getRetrofit()?.create(UserAPI::class.java)

        if (eventFavouritesStatus == "1") {
            favouritesButtonEvent.setBackgroundResource(R.drawable.fav_red)
        } else favouritesButtonEvent.setBackgroundResource(R.drawable.fav_black)


        favouritesButtonEvent.setOnClickListener {
            if (eventFavouritesStatus == "0") {

                userAPI?.saveToFavourites("Bearer $token", userId!!.toLong(), eventId!!.toLong())
                    ?.enqueue(object : Callback<Boolean> {
                        override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                            if (response.isSuccessful) {
                                Toast.makeText(
                                    baseContext,
                                    "Added to favourites!",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            } else
                                Toast.makeText(baseContext, "Try again!", Toast.LENGTH_SHORT)
                                    .show()
                        }

                        override fun onFailure(call: Call<Boolean>, t: Throwable) {
                            Toast.makeText(
                                baseContext,
                                "Failed to connect to the server!",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                    })



                favouritesButtonEvent.setBackgroundResource(R.drawable.fav_red)
                eventFavouritesStatus = "1"

            } else if (eventFavouritesStatus == "1") {

                userAPI?.deleteFromFavourites("Bearer $token", userId!!.toLong(), eventId!!.toLong())
                    ?.enqueue(object : Callback<Boolean> {
                        override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                            if (response.isSuccessful) {

                                Toast.makeText(
                                    baseContext,
                                    "Deleted from favourites!",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            } else
                                Toast.makeText(baseContext, "Try again!", Toast.LENGTH_SHORT)
                                    .show()
                        }

                        override fun onFailure(call: Call<Boolean>, t: Throwable) {
                            Toast.makeText(
                                baseContext,
                                "Failed to connect to the server!",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                    })

                favouritesButtonEvent.setBackgroundResource(R.drawable.fav_black)
                eventFavouritesStatus = "0"
            }
        }

        val eventAPI = retrofitService.getRetrofit()?.create(EventAPI::class.java)

        deleteButtonAdmin.setOnClickListener {
            if (token != null) {
                eventAPI?.deleteEvent("Bearer $token", eventId!!.toLong())
                    ?.enqueue(object : retrofit2.Callback<Boolean> {
                        override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                            if(response.body()==true) {
                                Toast.makeText(baseContext, "Event Deleted", Toast.LENGTH_SHORT)
                                    .show()

                                val intent = Intent(applicationContext, AllEvents::class.java)
                                intent.putExtra("username", username)
                                intent.putExtra("userId",userId)
                                intent.putExtra("token", token)
                                startActivity(intent)

                            } else {
                                Toast.makeText(baseContext, "Bad", Toast.LENGTH_SHORT)
                                    .show()
                                val intent = Intent(applicationContext, AllEvents::class.java)
                                startActivity(intent)
                            }

                        }

                        override fun onFailure(call: Call<Boolean>, t: Throwable) {
                            Toast.makeText(baseContext, "Save failed!", Toast.LENGTH_SHORT).show()
                            Logger.getLogger(AddEvent::class.java.name)
                                .log(Level.SEVERE, "Error occurred", t)
                        }
                    })
            }

        }


        backButtonEvent.setOnClickListener {
            val username = intent.getStringExtra("username")
            val token = intent.getStringExtra("token")
            val userId = intent.getStringExtra("userId")
            val intent = Intent(applicationContext, AllEvents::class.java)
            intent.putExtra("username", username)
            intent.putExtra("userId",userId)
            intent.putExtra("token", token)
            startActivity(intent)
        }

        showLocationButton.setOnClickListener {

            var str = (eventPlace.text as String?)?.removeWhitespaces()
            val geocoder = Geocoder(this, Locale.getDefault())

            val addresses = geocoder.getFromLocationName(str, 2)
            val address = addresses[0]
            val username = intent.getStringExtra("username")
            val token = intent.getStringExtra("token")
            val userId = intent.getStringExtra("userId")
            val intent = Intent(applicationContext, EventMap::class.java)
            intent.putExtra("username", username)
            intent.putExtra("userId",userId)
            intent.putExtra("token", token)

            intent.putExtra("lon", address.longitude)
            intent.putExtra("lat", address.latitude)
            startActivity(intent)
        }


    }

    fun String.removeWhitespaces() = replace(" ", "")
    fun removeNumber(str: String, number: Int): String {
        return str.replaceFirst(Regex("(^|\\s)$number(\\s|$)"), " ")
    }

    override fun onBackPressed() {
        val username = intent.getStringExtra("username")
        val token = intent.getStringExtra("token")
        val userId = intent.getStringExtra("userId")
        val intent = Intent(applicationContext, AllEvents::class.java)
        intent.putExtra("username", username)
        intent.putExtra("userId",userId)
        intent.putExtra("token", token)
        startActivity(intent)
    }
}