package daromon.goodevent.event

import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ActionMode
import android.view.Menu
import android.widget.Button

import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import daromon.goodevent.HomePage
import daromon.goodevent.R
import daromon.goodevent.model.EventData
import daromon.goodevent.model.UserModel
import daromon.goodevent.retrofit.EventAPI
import daromon.goodevent.retrofit.RetrofitService
import daromon.goodevent.retrofit.UserAPI
import retrofit2.Call
import retrofit2.Response
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.logging.Level
import java.util.logging.Logger
import android.content.Intent as Intent


class AllEvents : AppCompatActivity() {

    lateinit var eventMutableList: MutableList<EventData>
    lateinit var searchEventMutableList: MutableList<EventData>
    lateinit var recyclerView: RecyclerView

    @SuppressLint("NotifyDataSetChanged")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_events)
        recyclerView = findViewById<RecyclerView>(R.id.eventsList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        searchEventMutableList = mutableListOf()
        val username = intent.getStringExtra("username")
        val token = intent.getStringExtra("token")
        val userId = intent.getStringExtra("userId")
        val retrofitService = RetrofitService()
        val eventAPI = retrofitService.getRetrofit()?.create(EventAPI::class.java)
        val sortDateButton = findViewById<Button>(R.id.sortDateButton)
        var isAscendingSort = true
        if (token != null) {

            eventAPI?.allEvents("Bearer $token", userId!!.toLong())
                ?.enqueue(object : retrofit2.Callback<MutableList<EventData>> {
                    @RequiresApi(Build.VERSION_CODES.O)
                    override fun onResponse(
                        call: Call<MutableList<EventData>>,
                        response: Response<MutableList<EventData>>
                    ) {
                        if (response.isSuccessful) {
                            recyclerView.apply {
                                layoutManager =
                                    LinearLayoutManager(this@AllEvents)

                                eventMutableList = response.body()!!


                                searchEventMutableList.addAll(eventMutableList)
                                var adapter =
                                    EventAdapter(searchEventMutableList)
                                recyclerView.adapter = adapter


                                adapter.setOnItemClickListener(object :
                                    EventAdapter.onItemClickListener {

                                    override fun onItemClick(position: Int) {

                                        val username = intent.getStringExtra("username")
                                        val token = intent.getStringExtra("token")
                                        val userId = intent.getStringExtra("userId")
                                        val intent = Intent(
                                            this@AllEvents,
                                            Event::class.java
                                        )
                                        val user1 = searchEventMutableList[position].creator
                                        val cc = user1!!.username
                                        intent.putExtra("username", username)
                                        intent.putExtra("userId", userId)
                                        intent.putExtra("token", token)

                                        intent.putExtra(
                                            "id",
                                            searchEventMutableList[position].id
                                        )
                                        intent.putExtra(
                                            "title",
                                            searchEventMutableList[position].title
                                        )
                                        intent.putExtra(
                                            "place",
                                            searchEventMutableList[position].place
                                        )
                                        intent.putExtra(
                                            "date",
                                            searchEventMutableList[position].date
                                        )
                                        intent.putExtra(
                                            "creator",
                                            cc
                                        )
                                        intent.putExtra(
                                            "image",
                                            searchEventMutableList[position].image
                                        )
                                        intent.putExtra(
                                            "description",
                                            searchEventMutableList[position].description
                                        )
                                        intent.putExtra(
                                            "favStatus",
                                            searchEventMutableList[position].favouritesStatus
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


        val backButton = findViewById<Button>(R.id.backButtonAllEvents)
        backButton.setOnClickListener {
            val username = intent.getStringExtra("username")
            val token = intent.getStringExtra("token")
            val userId = intent.getStringExtra("userId")
            val intent = Intent(applicationContext, HomePage::class.java)
            intent.putExtra("username", username)
            intent.putExtra("userId", userId)
            intent.putExtra("token", token)
            startActivity(intent)
        }

        sortDateButton.setOnClickListener {
            isAscendingSort = !isAscendingSort
            val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
            val dataComparator = Comparator<EventData> { event1, event2 ->
                val date1 = LocalDate.parse(event1.date, formatter)
                val date2 = LocalDate.parse(event2.date, formatter)
                date1.compareTo(date2)
            }
            searchEventMutableList.sortWith(dataComparator)
            if (isAscendingSort) {
                searchEventMutableList.reverse()
            }
            recyclerView.adapter!!.notifyDataSetChanged()
        }

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_item, menu)
        val item = menu?.findItem(R.id.search_action)
        val searchView = item?.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                TODO("Not yet implemented")
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun onQueryTextChange(newText: String?): Boolean {
                searchEventMutableList.clear()
                val searchText = newText!!.lowercase(Locale.getDefault())
                if (searchText.isNotEmpty()) {
                    eventMutableList.forEach {
                        if (it.place!!.lowercase(Locale.getDefault()).contains(searchText)) {
                            searchEventMutableList.add(it)
                        }
                        if (it.title!!.lowercase(Locale.getDefault()).contains(searchText)) {
                            searchEventMutableList.add(it)
                        }
                    }
                    recyclerView.adapter!!.notifyDataSetChanged()
                } else {
                    searchEventMutableList.clear()
                    searchEventMutableList.addAll(eventMutableList)
                    recyclerView.adapter!!.notifyDataSetChanged()
                }
                return false
            }

        })
        return super.onCreateOptionsMenu(menu)
    }

    fun stringToIntArray(str: String?): IntArray? {
        return str?.split(" ")
            ?.filter { it.isNotBlank() }
            ?.map { it.toInt() }
            ?.toIntArray()
    }

    override fun onBackPressed() {
        val username = intent.getStringExtra("username")
        val token = intent.getStringExtra("token")
        val userId = intent.getStringExtra("userId")
        val intent = Intent(applicationContext, HomePage::class.java)
        intent.putExtra("username", username)
        intent.putExtra("userId", userId)
        intent.putExtra("token", token)
        startActivity(intent)
    }
}



