package com.miiky.houser.models

data class Houses (
    var ID: Int,
    var title: String,
    var subtitle: String,
    var location: String,
    var rooms: Int,
    var floors: Int,
    var legal: String,
    var img: String,
    var userid: Int
)