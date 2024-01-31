package com.hust.edu.vn.residentialmanagementapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.hust.edu.vn.residentialmanagementapp.R
import com.hust.edu.vn.residentialmanagementapp.databinding.FragmentHomeBinding
import com.hust.edu.vn.residentialmanagementapp.databinding.FragmentQrBinding
import com.hust.edu.vn.residentialmanagementapp.viewmodel.UserViewModel

class QrFragment : Fragment() {
    private lateinit var binding: FragmentQrBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQrBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.myQr.setOnClickListener{
            findNavController().navigate(R.id.action_qrFragment_to_myQrFragment)
        }
        binding.visitorQr.setOnClickListener{
            findNavController().navigate(R.id.action_qrFragment_to_visitorQrFragment)
        }
    }
}