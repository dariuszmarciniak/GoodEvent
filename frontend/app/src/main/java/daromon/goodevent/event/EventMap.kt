package daromon.goodevent.event

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import daromon.goodevent.R

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.FragmentActivity

class EventMap : AppCompatActivity() {


    lateinit var mapFragment : SupportMapFragment
    lateinit var googleMap: GoogleMap


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_map)


        val longitude = intent.getDoubleExtra("lon",51.77685)
        val latitude = intent.getDoubleExtra("lat",19.52968)


        mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(OnMapReadyCallback {
            googleMap = it


            val location1 = LatLng(latitude,longitude)
            googleMap.addMarker(MarkerOptions().position(location1).title("Event location"))
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location1,15f))


        })
    }


}