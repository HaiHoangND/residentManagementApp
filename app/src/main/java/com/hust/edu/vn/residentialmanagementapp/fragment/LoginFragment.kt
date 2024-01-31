package com.hust.edu.vn.residentialmanagementapp.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.hust.edu.vn.residentialmanagementapp.R
import com.hust.edu.vn.residentialmanagementapp.api.ApiClient
import com.hust.edu.vn.residentialmanagementapp.data.Login
import com.hust.edu.vn.residentialmanagementapp.data.User
import com.hust.edu.vn.residentialmanagementapp.databinding.FragmentLoginBinding
import com.hust.edu.vn.residentialmanagementapp.viewmodel.UserViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginFragment : Fragment() {
    private val viewModel: UserViewModel by activityViewModels()
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonLogin.setOnClickListener {
            val phone = binding.editTextPhone.text.toString()
            val password = binding.editTextPassword.text.toString()

            // Gọi API đăng nhập
            login(phone, password)
        }
    }

    private fun login(phone: String, password: String) {
        val loginRequestBody = Login(phone, password)
        val call: Call<User> = ApiClient.apiService.login(loginRequestBody)

        call.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    val user = response.body()
                    if (user?.role == "MEMBER") {
                        // Lưu thông tin vào UserViewModel
                        viewModel.setId(user?.id ?: 0)
                        viewModel.setUserName(user?.userName ?: "")
                        viewModel.setToken(user?.token ?: "")
                        viewModel.setBuilding(user?.building ?: "")
                        viewModel.setRoom(user?.roomNumber ?: 0)
                        viewModel.setUserPhone(user?.phone ?: "")
                        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                    } else {
                        Log.e("LoginFragment", "Không phải cư dân")
                    }

                } else {
                    // Xử lý lỗi nếu cần
                    Log.e("LoginFragment", "Login failed. Code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                // Xử lý lỗi nếu cần
                Log.e("LoginFragment", "Login failed. Error: ${t.message}")
            }
        })
    }

}