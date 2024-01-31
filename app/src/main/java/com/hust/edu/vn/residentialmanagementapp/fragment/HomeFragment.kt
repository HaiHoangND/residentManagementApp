package com.hust.edu.vn.residentialmanagementapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.hust.edu.vn.residentialmanagementapp.R
import com.hust.edu.vn.residentialmanagementapp.databinding.FragmentHomeBinding
import com.hust.edu.vn.residentialmanagementapp.viewmodel.UserViewModel

class HomeFragment : Fragment() {
    private val viewModel: UserViewModel by activityViewModels()
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
        }
        val name = viewModel.userName.value
        binding.userName.text = name
        binding.signature.text = extractInitials(name.toString())
        binding.qrCode.setOnClickListener{
            findNavController().navigate(R.id.action_homeFragment_to_qrFragment)
        }
    }

    fun extractInitials(name: String): String {
        val words = name.trim().split("\\s+".toRegex())
        val lastWord = words[words.size - 1]
        return if (words.size >= 2) {

            val secondLastWord = words[words.size - 2]

            // Lấy hai kí tự in hoa từ hai từ cuối
            val initials = "${secondLastWord.first().uppercase()}${lastWord.first().uppercase()}"

            initials
        } else {
            val initials = "${lastWord.first().uppercase()}"

            initials
        }
    }
}