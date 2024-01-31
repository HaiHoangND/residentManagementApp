package com.hust.edu.vn.residentialmanagementapp.api

import androidx.fragment.app.activityViewModels
import com.google.gson.JsonObject
import com.hust.edu.vn.residentialmanagementapp.data.Login
import com.hust.edu.vn.residentialmanagementapp.data.QrInformation
import com.hust.edu.vn.residentialmanagementapp.data.User
import com.hust.edu.vn.residentialmanagementapp.viewmodel.UserViewModel
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @POST("authenticate")
    fun login(@Body requestBody: Login): Call<User>

    @POST("encrypt/enc")
    fun encryptQr(@Header("Authorization") authorization: String, @Body requestBody: QrInformation): Call<ResponseBody>
}