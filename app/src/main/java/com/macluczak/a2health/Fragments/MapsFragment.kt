package com.macluczak.a2health.Fragments

import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.graphics.Color
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.maps.android.PolyUtil
import com.macluczak.a2health.BuildConfig
import com.macluczak.a2health.R
import okhttp3.OkHttpClient
import org.json.JSONObject


class MapsFragment : Fragment() {

    private val callback = OnMapReadyCallback { googleMap ->

        val origin = LatLng(-34.0, 151.0)
        val destination = LatLng(-33.0, 151.0)

        googleMap.addMarker(MarkerOptions().position(origin).title("origin"))
        googleMap.addMarker(MarkerOptions().position(destination).title("Destination"))


        googleMap.moveCamera(CameraUpdateFactory.newLatLng(origin))


//        val key = BuildConfig.GoogleMap_ApiKey
//
//        googleMap.run {
//
//            val path: MutableList<List<LatLng>> = ArrayList()
//            val urlDirections =
//                "https://maps.googleapis.com/maps/api/directions/json?destination=${origin.latitude},${origin.longitude}&origin=${destination.latitude},${destination.longitude}&key=${key}"
//            val directionsRequest = object : StringRequest(Request.Method.GET,
//                urlDirections,
//                Response.Listener<String> { response ->
//                    val jsonResponse = JSONObject(response)
//                    // Get routes
//                    val routes = jsonResponse.getJSONArray("routes")
//                    val legs = routes.getJSONObject(0).getJSONArray("legs")
//                    val steps = legs.getJSONObject(0).getJSONArray("steps")
//                    for (i in 0 until steps.length()) {
//                        val points =
//                            steps.getJSONObject(i).getJSONObject("polyline").getString("points")
//                        path.add(PolyUtil.decode(points))
//                    }
//                    for (i in 0 until path.size) {
//                        googleMap.addPolyline(PolylineOptions().addAll(path[i]).color(Color.RED))
//                    }
//                },
//                Response.ErrorListener { _ ->
//                }) {}
//            val requestQueue = Volley.newRequestQueue(requireContext())
//            requestQueue.add(directionsRequest)
//
//        }




    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }
}