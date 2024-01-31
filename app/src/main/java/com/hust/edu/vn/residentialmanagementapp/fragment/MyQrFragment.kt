package com.hust.edu.vn.residentialmanagementapp.fragment

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.activityViewModels
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.QRCodeWriter
import com.hust.edu.vn.residentialmanagementapp.api.ApiClient
import com.hust.edu.vn.residentialmanagementapp.data.QrInformation
import com.hust.edu.vn.residentialmanagementapp.databinding.FragmentMyQrBinding
import com.hust.edu.vn.residentialmanagementapp.viewmodel.UserViewModel
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyQrFragment : Fragment() {
    private val viewModel: UserViewModel by activityViewModels()
    private lateinit var binding: FragmentMyQrBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMyQrBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
        }
        val userName = viewModel.userName
        binding.name.text = userName.value
        val userPhone = viewModel.userPhone.value.toString()
        val destination = viewModel.building.value.toString()
        val visitorRequest = false
        val task = ""
        val visitorName = ""
        val qrInformationBody = QrInformation(
            userName.value.toString(),
            userPhone,
            destination,
            visitorRequest,
            task,
            visitorName,
        )

        encrypt(qrInformationBody) { encryptCode ->
            // Xử lý mã encryptCode ở đây
            Log.d("LoginFragment", "Encrypted Code: $encryptCode")
            val qrCodeBitmap = getQrCodeBitmap(encryptCode)
            binding.qrCode.setImageBitmap(qrCodeBitmap)
        }
    }

    private fun encrypt(qrInformation: QrInformation, callback: (String) -> Unit) {
        val token = viewModel.token.value
        val authorizationHeader = "Bearer $token"
        val call: Call<ResponseBody> =
            ApiClient.apiService.encryptQr(authorizationHeader, qrInformation)

        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    val encryptCode = responseBody?.string()
                    Log.d("LoginFragment", "Successful code: $encryptCode")

                    // Chuyển chuỗi JSON thành đối tượng JSONObject
                    val jsonObject = JSONObject(encryptCode)

                    // Lấy giá trị trường "data"
                    val dataValue = jsonObject.optString("data", "")

                    // Gọi hàm callback với giá trị "data"
                    callback.invoke(dataValue)
                } else {
                    // Xử lý lỗi nếu cần
                    Log.d("LoginFragment", "Encryption failed. Code: ${response.code()}")
                    callback.invoke("") // Trả về chuỗi rỗng nếu có lỗi
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                // Xử lý lỗi nếu cần
                Log.d("LoginFragment", "Encryption failed. Error: ${t.message}")
                callback.invoke("") // Trả về chuỗi rỗng nếu có lỗi
            }
        })
    }

    fun getQrCodeBitmap(encryptCode: String): Bitmap {
        val size = 350 //pixels
        val qrCodeContent = encryptCode
        val hints = hashMapOf<EncodeHintType, Int>().also {
            it[EncodeHintType.MARGIN] = 1
        } // Make the QR code buffer border narrower
        val bits = QRCodeWriter().encode(qrCodeContent, BarcodeFormat.QR_CODE, size, size, hints)
        return Bitmap.createBitmap(size, size, Bitmap.Config.RGB_565).also {
            for (x in 0 until size) {
                for (y in 0 until size) {
                    it.setPixel(x, y, if (bits[x, y]) Color.BLACK else Color.WHITE)
                }
            }
        }
    }
}
