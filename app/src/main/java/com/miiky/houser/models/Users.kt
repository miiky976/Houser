package com.miiky.houser.models

data class User(
    var email: String,
    var username: String,
    var pass: String,
    var name: String,
    var lastname: String,
)

data class UserModel(
    var profile: User
)
