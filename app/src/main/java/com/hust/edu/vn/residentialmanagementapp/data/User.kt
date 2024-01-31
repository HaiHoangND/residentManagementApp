package com.hust.edu.vn.residentialmanagementapp.data

import com.google.gson.annotations.SerializedName

data class User(
    var id: Int,
    var userName: String,
    @SerializedName("access_token") var token: String,
    var role: String,
    var phone: String,
    var roomNumber: Int,
    var building: String,
    var gender: String
)
