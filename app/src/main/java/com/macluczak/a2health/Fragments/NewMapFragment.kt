package com.macluczak.a2health.Fragments

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.maps.android.PolyUtil
import com.macluczak.a2health.BuildConfig
import com.macluczak.a2health.R
import org.json.JSONObject
import org.json.JSONStringer
import kotlin.properties.Delegates

class NewMapFragment : Fragment() {
    lateinit var origin: Marker
    lateinit var destination: Marker
    lateinit var imglat: String
    lateinit var imglong: String
    var counter by Delegates.notNull<Int>()
    lateinit var markersList: ArrayList<String>
    lateinit var distance: String
    lateinit var duration: String
    lateinit var startAdress: String
    lateinit var endAdress: String
    lateinit var waypoints: JSONObject

    @SuppressLint("PotentialBehaviorOverride")
    private val callback = OnMapReadyCallback { googleMap ->
        counter = 0
        markersList = arrayListOf()

//                  W pełni świadomie umieszczam klucz API wewnątrz kodu
//                  aby ułatwić ocenę kodu bez dodatkowych komplikacji.

//                    val key = BuildConfig.GoogleMap_ApiKey
        val key = "AIzaSyAoBB1iGSVERv8KEkADCH1YQdVe5pEBGIw"

//                  Klucze API domyślnie są pzechowywane w pliku "local.properties",
//                  tym samym za pomocą pliku ".gitignore" nie są wysyłane do zdalnego repozytorium
//                  w celu zapewnienia bezpieczeństwa przed kradzieżą klucza


        googleMap.setOnMapClickListener { click ->


//            val centerLatLang: LatLng =
//                googleMap.projection.visibleRegion.latLngBounds.center

            if (markersList.size < 2 && !markersList.contains("origin")) {

                origin = googleMap.addMarker(MarkerOptions().position(click)
                    .title("origin").draggable(true)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)))!!
                markersList.add(0, "origin")
                counter = counter.inc()

                if (markersList.size == 2) {

                    googleMap.run {

                        val path: MutableList<List<LatLng>> = ArrayList()
                        val urlDirections =
                            "https://maps.googleapis.com/maps/api/directions/json?destination=${destination.position.latitude},${destination.position.longitude}&origin=${origin.position.latitude},${origin.position.longitude}&key=${key}"
                        val directionsRequest = object : StringRequest(Request.Method.GET,
                            urlDirections,
                            Response.Listener<String> { response ->
                                val jsonResponse = JSONObject(response)
                                if (jsonResponse.has("status") && jsonResponse.getString("status")
                                        .equals("OK")
                                ) {
                                    val routes = jsonResponse.getJSONArray("routes")
                                    val legs = routes.getJSONObject(0).getJSONArray("legs")
                                    val steps = legs.getJSONObject(0).getJSONArray("steps")
                                    waypoints = legs.getJSONObject(0)
                                    distance = legs.getJSONObject(0).getJSONObject("distance")
                                        .getString("text")
                                    duration = legs.getJSONObject(0).getJSONObject("duration")
                                        .getString("text")
                                    startAdress = legs.getJSONObject(0).getString("start_address")
                                    endAdress = legs.getJSONObject(0).getString("end_address")

                                    if (routes.length() > 0 && legs.length() > 0 && steps.length() > 0) {
                                        for (i in 0 until steps.length()) {
                                            val points =
                                                steps.getJSONObject(i).getJSONObject("polyline")
                                                    .getString("points")
                                            path.add(PolyUtil.decode(points))
                                        }
                                        for (i in 0 until path.size) {
                                            googleMap.addPolyline(PolylineOptions().addAll(path[i])
                                                .color(Color.RED))
                                        }
                                        imglat = path[1][1].latitude.toString()
                                        imglong = path[1][1].longitude.toString()
                                    }
                                } else {
                                    origin.remove()
                                    markersList.remove(origin.title)
                                    Toast.makeText(activity, "Road not found!", Toast.LENGTH_SHORT)
                                        .show()

                                }
                            },
                            Response.ErrorListener { _ ->
                            }) {}
                        val requestQueue = Volley.newRequestQueue(requireContext())
                        requestQueue.add(directionsRequest)

                    }
                    val b = LatLngBounds.Builder()
                    b.apply {
                        include(origin.position)
                        include(destination.position)
                    }
                    val bounds = b.build()
                    val padding = 200
                    var cu = CameraUpdateFactory.newLatLngBounds(bounds, padding)
                    googleMap.animateCamera(cu)
                }

            } else if (markersList.size < 2 && !markersList.contains("destination")) {


                destination = googleMap.addMarker(MarkerOptions().position(click)
                    .title("destination").draggable(true)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)))!!

                markersList.add(1, "destination")
                counter = counter.inc()




                if (markersList.size == 2) {

                    googleMap.run {

                        val path: MutableList<List<LatLng>> = ArrayList()
                        val urlDirections =
                            "https://maps.googleapis.com/maps/api/directions/json?destination=${destination.position.latitude},${destination.position.longitude}&origin=${origin.position.latitude},${origin.position.longitude}&key=${key}"
                        val directionsRequest = object : StringRequest(Request.Method.GET,
                            urlDirections,
                            Response.Listener<String> { response ->
                                val jsonResponse = JSONObject(response)
                                if (jsonResponse.has("status") && jsonResponse.getString("status")
                                        .equals("OK")
                                ) {
                                    val routes = jsonResponse.getJSONArray("routes")
                                    val legs = routes.getJSONObject(0).getJSONArray("legs")
                                    val steps = legs.getJSONObject(0).getJSONArray("steps")
                                    waypoints = legs.getJSONObject(0)
                                    distance = legs.getJSONObject(0).getJSONObject("distance")
                                        .getString("text")
                                    duration = legs.getJSONObject(0).getJSONObject("duration")
                                        .getString("text")
                                    startAdress = legs.getJSONObject(0).getString("start_address")
                                    endAdress = legs.getJSONObject(0).getString("end_address")
                                    if (routes.length() > 0 && legs.length() > 0 && steps.length() > 0) {
                                        for (i in 0 until steps.length()) {
                                            val points =
                                                steps.getJSONObject(i).getJSONObject("polyline")
                                                    .getString("points")
                                            path.add(PolyUtil.decode(points))
                                        }
                                        for (i in 0 until path.size) {
                                            googleMap.addPolyline(PolylineOptions().addAll(path[i])
                                                .color(Color.RED))
                                        }
                                        imglat = path[1][1].latitude.toString()
                                        imglong = path[1][1].longitude.toString()

                                    }
                                } else {
                                    destination.remove()
                                    markersList.remove(destination.title)
                                    Toast.makeText(activity, "Road not found!", Toast.LENGTH_SHORT)
                                        .show()

                                }
                            },
                            Response.ErrorListener { _ ->
                            }) {}
                        val requestQueue = Volley.newRequestQueue(requireContext())
                        requestQueue.add(directionsRequest)

                    }
                    val b = LatLngBounds.Builder()
                    b.apply {
                        include(origin.position)
                        include(destination.position)
                    }
                    val bounds = b.build()
                    val padding = 200
                    var cu = CameraUpdateFactory.newLatLngBounds(bounds, padding)
                    googleMap.animateCamera(cu)
                }

            }


        }
        googleMap.setOnMarkerClickListener { marker ->
            marker.remove()
            markersList.remove(marker.title)
            googleMap.clear()
            if (markersList.contains("origin")) {
                googleMap.addMarker(MarkerOptions().position(origin.position)
                    .title("origin").draggable(true)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)))!!
            } else if (markersList.contains("destination")) {
                googleMap.addMarker(MarkerOptions().position(destination.position)
                    .title("destination").draggable(true)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)))!!
            }


            true

        }


    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_new_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }
}