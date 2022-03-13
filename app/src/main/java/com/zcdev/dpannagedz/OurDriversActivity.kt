package com.zcdev.dpannagedz

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.zcdev.dpannagedz.data.Models.Places
import com.zcdev.dpannagedz.databinding.ActivityOurDriversBinding

class OurDriversActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityOurDriversBinding
    var currentlocation: Location? = null
    var currentMarker: Marker? = null
    var driverLocation=ArrayList<Places>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOurDriversBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        supportActionBar!!.hide()

        permissionCheck()
        //add places
        addPlaces()

    }

    private fun addPlaces() {
        var p1=Places(1,R.drawable.logo,"Mustapha Stamboli",35.414427,0.1268365)
        var p2=Places(1,R.drawable.logo2,"Menage",35.3992096,0.1441682)
        var p3=Places(1,R.drawable.logo1,"Food",35.3989479,0.1393044)

        driverLocation.add(p1)
        driverLocation.add(p2)
        driverLocation.add(p3)
    }

    private fun permissionCheck() {
        var ACSESSLOCATIONREQUESTCODE = 0
        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions(
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                    ACSESSLOCATIONREQUESTCODE
                )
                return
            }
        }
        // contunu becouse u have permission
        Getcurruentlocation();
    }
    private fun Getcurruentlocation() {
        Toast.makeText(this.baseContext, "User enable the location service", Toast.LENGTH_LONG)
            .show()
        var locationOnmap = MapLocationListner(this.baseContext)
        var locationmanager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locationmanager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 400, 0f, locationOnmap)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
       /* val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))*/
             for (i in 0..driverLocation.size-1)
       {val place = LatLng(driverLocation[i].placeLocation!!.latitude, driverLocation[i].placeLocation!!.longitude)
           mMap.addMarker(MarkerOptions()
               .position(place)
               .title(driverLocation[i].name)
               .snippet("Number Phone "+"0792930900")
               .icon(BitmapDescriptorFactory.fromBitmap(scaleimage(resources,R.drawable.logo,100))))
           mMap.moveCamera(CameraUpdateFactory.newLatLng(place))
           mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(place,15f))
           googleMap.setOnMarkerClickListener ( object :GoogleMap.OnMarkerClickListener{
               override fun onMarkerClick(p0: Marker): Boolean {
                   Toast.makeText(applicationContext, "show custom dialog", Toast.LENGTH_SHORT).show()
                   var view: View
                   view=layoutInflater.inflate(R.layout.info_drivers,null)
                  /* view.editMaher.text=item[i].craft
                   view.editName.text=item[i].userName
                   view.phoneNumber.text=item[i].phonenumber.toString()
                   view.wilayaName.text=item[i].state.toString()*/
                   /*     Picasso.with(this@MapsOrderActivity)
                            .load(objs[i].getParseFile("image").url)
                            .into(imageEmployee)*/
                   showAlertDialog(view)
                    return true
               }

           })
       }

    }

    private fun showAlertDialog(view: View) {
        //var view:View
        // view=layoutInflater.inflate(R.layout.info_ofworker,null)
        var dialog= AlertDialog.Builder(this)
            .setView(view)
        view.setBackgroundResource(R.color.black)
        dialog.create()
        dialog.show()
    }



    inner class MapLocationListner : LocationListener {
        var context: Context? = null

        constructor(context: Context) : super() {
            currentlocation = Location("Start")
            currentlocation!!.latitude = 0.0
            currentlocation!!.longitude = 0.0
            this.context = context
        }

        override fun onLocationChanged(p0: Location) {
        updatelocation(p0)
        }
        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {

        }
    }


    private fun updatelocation(p0: Location?) {
        Toast.makeText(
            this.baseContext,
            "Location hase been Change" + p0!!.longitude + " " + p0!!.latitude,
            Toast.LENGTH_LONG
        ).show()
        mMap.clear()
        val loc = LatLng(p0!!.latitude, p0!!.longitude)
        if (currentMarker != null) {
            currentMarker!!.remove()
        }
        currentMarker = mMap.addMarker(
            MarkerOptions()
                .position(loc)
                .title("Please I need help ")
                .snippet("this is my current location")
                .icon(
                    BitmapDescriptorFactory.fromBitmap(
                        scaleimage(
                            resources,
                            R.drawable.logo,
                            100
                        )
                    )
                )
        )
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc,15f))


        currentlocation = p0

    }

    fun scaleimage(res: Resources, id: Int, sidesize: Int): Bitmap {
        var b: Bitmap? = null
        val o = BitmapFactory.Options()
        o.inJustDecodeBounds = true
        b = BitmapFactory.decodeResource(res, id, o)
        var scale = 1
        var sc = 0.0f
        if (o.outHeight > o.outWidth) {
            sc = (o.outHeight / sidesize).toFloat()
            scale = Math.round(sc)
        } else {
            sc = (o.outWidth / sidesize).toFloat()
            scale = Math.round(sc)
        }

        val o2 = BitmapFactory.Options()
        o2.inSampleSize = scale
        b = BitmapFactory.decodeResource(res, id, o2)
        return b

    }



    /*
    * show multiMarkers
    *         for(i in 0..listOfPlaces.size-1)
         {
          var place=listOfPlaces[i]
            val loc = LatLng(place.pLoc!!.latitude,place.pLoc!!.longitude)
        mMap.addMarker(
            MarkerOptions().position(loc).title(place.pName).icon(
                BitmapDescriptorFactory.fromBitmap(scaleimage(resources, R.drawable.uberxl, 80))
            )
        )
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc,4f))

         }
    *
    * */


    fun doCall(view: View){
        startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+"0792930900")))
    }
}