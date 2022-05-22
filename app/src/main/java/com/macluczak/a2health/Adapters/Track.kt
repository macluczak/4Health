package com.macluczak.a2health.Adapters

data class Track(
    val id: String,
    val title: String,
    val distance: Int,
    val city: String,
    var fav: Boolean = false
){


}
