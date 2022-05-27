package com.macluczak.a2health.Adapters

import org.json.JSONObject

data class Track(
    val id: String,
    val title: String,

    val distance: String,
    val duration: String,

    val startAdress: String,
    val stopAdress: String,

    val start_lat: String,
    val start_long: String,

    val stop_lat: String,
    val stop_long: String,

    val waypoints: String

){


}
