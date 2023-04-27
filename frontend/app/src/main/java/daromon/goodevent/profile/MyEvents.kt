package daromon.goodevent.profile

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import daromon.goodevent.HomePage
import daromon.goodevent.R
import daromon.goodevent.event.AllEvents
import daromon.goodevent.event.Event
import daromon.goodevent.event.EventAdapter
import daromon.goodevent.model.EventData
import daromon.goodevent.retrofit.AuthRequest
import daromon.goodevent.retrofit.EventAPI
import daromon.goodevent.retrofit.RetrofitService
import daromon.goodevent.retrofit.UserAPI
import retrofit2.Call
import retrofit2.Response
import java.util.*
import java.util.logging.Level
import java.util.logging.Logger

class MyEvents : AppCompatActivity() {

    lateinit var eventMutableList: MutableList<EventData>
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_myevents)
        recyclerView = findViewById<RecyclerView>(R.id.myEvents)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val username = intent.getStringExtra("username")
        val token = intent.getStringExtra("token")
        val userId = intent.getStringExtra("userId")

        val retrofitService = RetrofitService()
        val userAPI = retrofitService.getRetrofit()?.create(UserAPI::class.java)

        if (token != null) {

            userAPI?.getFavourites("Bearer $token", userId!!.toLong())
                ?.enqueue(object : retrofit2.Callback<MutableList<EventData>> {
                    override fun onResponse(
                        call: Call<MutableList<EventData>>,
                        response: Response<MutableList<EventData>>
                    ) {
                        if (response.isSuccessful) {
                            recyclerView.apply {
                                layoutManager =
                                    LinearLayoutManager(this@MyEvents)

                                eventMutableList = response.body()!!


                                var adapter =
                                    EventAdapter(eventMutableList)
                                recyclerView.adapter = adapter


                                adapter.setOnItemClickListener(object :
                                    EventAdapter.onItemClickListener {

                                    override fun onItemClick(position: Int) {

                                        val username = intent.getStringExtra("username")
                                        val token = intent.getStringExtra("token")
                                        val userId = intent.getStringExtra("userId")
                                        val intent = Intent(
                                            this@MyEvents,
                                            Profile::class.java
                                        )

                                        intent.putExtra("username", username)
                                        intent.putExtra("userId",userId)
                                        intent.putExtra("token", token)

                                        intent.putExtra(
                                            "id",
                                            eventMutableList[position].id
                                        )
                                        intent.putExtra(
                                            "title",
                                            eventMutableList[position].title
                                        )
                                        intent.putExtra(
                                            "place",
                                            eventMutableList[position].place
                                        )
                                        intent.putExtra(
                                            "date",
                                            eventMutableList[position].date
                                        )
                                        intent.putExtra(
                                            "creator",
                                            eventMutableList[position].creator
                                        )
                                        intent.putExtra(
                                            "image",
                                            eventMutableList[position].image
                                        )
                                        intent.putExtra(
                                            "description",
                                            eventMutableList[position].description
                                        )
                                        intent.putExtra(
                                            "favStatus",
                                            eventMutableList[position].favouritesStatus
                                        )
                                        startActivity(intent)
                                    }
                                })
                            }
                        }

                    }

                    override fun onFailure(
                        call: Call<MutableList<EventData>>,
                        t: Throwable
                    ) {
                        Toast.makeText(
                            baseContext,
                            "Error1!",
                            Toast.LENGTH_SHORT
                        ).show()
                        Logger.getLogger(AllEvents::class.java.name)
                            .log(Level.SEVERE, "Error occurred", t)
                    }
                })
        }


        val backButton = findViewById<Button>(R.id.backButtonMyEvents)
        backButton.setOnClickListener {
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
