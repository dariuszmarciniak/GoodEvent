package daromon.goodevent.event


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import daromon.goodevent.R
import java.lang.String
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.util.Base64
import android.widget.*
import daromon.goodevent.HomePage

import daromon.goodevent.databinding.ActivityAddEventBinding
import daromon.goodevent.model.EventData
import daromon.goodevent.model.EventModel
import daromon.goodevent.model.UserModel
import daromon.goodevent.retrofit.EventAPI
import daromon.goodevent.retrofit.RetrofitService
import daromon.goodevent.retrofit.UserAPI
import retrofit2.Call
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*
import java.util.logging.Level
import java.util.logging.Logger


class AddEvent : AppCompatActivity() {

    private lateinit var dateButton: TextView
    private lateinit var binding: ActivityAddEventBinding

    companion object {
        val IMAGE_REQUEST_CODE = 1_000;
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddEventBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_add_event)
        setContentView(binding.root)
        initializeComponents()
    }

    private fun initializeComponents() {
        val retrofitService = RetrofitService()
        val eventAPI = retrofitService.getRetrofit()?.create(EventAPI::class.java)
        val userAPI = retrofitService.getRetrofit()?.create(UserAPI::class.java)
        val backButton = findViewById<Button>(R.id.backButtonAddEvent)
        val titleEditText = findViewById<EditText>(R.id.titleEditText)
        val placeEditText = findViewById<EditText>(R.id.placeEditText)
        val descriptionEventEditText = findViewById<EditText>(R.id.descriptionEvent)
        dateButton = findViewById<Button>(R.id.eventDate)
        val saveEventButton = findViewById<Button>(R.id.eventSaveButton)
        binding.eventImage.setImageResource(R.drawable.event)

        val myCalendar = Calendar.getInstance()
        val datePicker = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateLabel(myCalendar)
        }

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


        dateButton.setOnClickListener {
            DatePickerDialog(
                this,
                datePicker,
                myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        binding.eventImage.setOnClickListener {
            pickImageFromGallery()

        }
        saveEventButton.setOnClickListener {
            val title = String.valueOf(titleEditText.text)
            val place = String.valueOf(placeEditText.text)
            val description = String.valueOf(descriptionEventEditText.text)


            val username = intent.getStringExtra("username")
            val token = intent.getStringExtra("token")
            val userId = intent.getStringExtra("userId")
            val user1 = UserModel(userId!!.toLong(), username)
            val event = EventModel()
            event.setTitle(title)
            event.setPlace(place)
            val bitmap = convertImageToBitmap()
            event.setImage(convertBitmapToBase64(bitmap))
            event.setDate(dateButton.text as kotlin.String)
            event.setDescription(description)
            event.setFavouritesStatus("0")
            event.setCreator(user1)

            if (token != null) {
                eventAPI?.save("Bearer $token", event)
                    ?.enqueue(object : retrofit2.Callback<Boolean> {
                        override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                            if (response.body() == true) {
                                Toast.makeText(baseContext, "Event Saved", Toast.LENGTH_SHORT)
                                    .show()
                            } else {
                                Toast.makeText(baseContext, "Bad", Toast.LENGTH_SHORT)
                                    .show()
                                val intent = Intent(applicationContext, AddEvent::class.java)
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


            val intent = Intent(applicationContext, HomePage::class.java)
            intent.putExtra("username", username)
            intent.putExtra("userId", userId)
            intent.putExtra("token", token)
            startActivity(intent)
        }
    }


    private fun convertImageToBitmap(): Bitmap {
        val drawable = binding.eventImage.drawable as BitmapDrawable
        return drawable.bitmap
    }

    private fun convertBitmapToBase64(bitmap: Bitmap): kotlin.String {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream)
        val image = stream.toByteArray()
        return Base64.encodeToString(image, Base64.DEFAULT)
    }

    private fun updateLabel(myCalendar: Calendar) {
        val myFormat = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.GERMAN)
        dateButton.text = sdf.format(myCalendar.time)
    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_REQUEST_CODE && resultCode == RESULT_OK) {
            binding.eventImage.setImageURI(data?.data)
        }
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