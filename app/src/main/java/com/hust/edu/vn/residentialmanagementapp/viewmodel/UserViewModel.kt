package com.hust.edu.vn.residentialmanagementapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserViewModel : ViewModel() {
    private val _id = MutableLiveData<Int>()
    val id: LiveData<Int>
        get() = _id
    private val _userName = MutableLiveData<String>()
    val userName: LiveData<String>
        get() = _userName
    private val _userPhone = MutableLiveData<String>()
    val userPhone: LiveData<String>
        get() = _userPhone
    private val _room = MutableLiveData<Int>()
    val room: LiveData<Int>
        get() = _room
    private val _building = MutableLiveData<String>()
    val building: LiveData<String>
        get() = _building
    private val _token = MutableLiveData<String>()
    val token: LiveData<String>
        get() = _token
    fun setId(id: Int) {
        _id.value = id
    }

    fun setUserName(userName: String) {
        _userName.value = userName
    }

    fun setUserPhone(userPhone: String) {
        _userPhone.value = userPhone
    }

    fun setRoom(room: Int) {
        _room.value = room
    }

    fun setBuilding(building: String) {
        _building.value = building
    }

    fun setToken(token: String) {
        _token.value = token
    }
}