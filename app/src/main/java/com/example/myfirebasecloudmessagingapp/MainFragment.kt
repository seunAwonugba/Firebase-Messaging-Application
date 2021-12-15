package com.example.myfirebasecloudmessagingapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.myfirebasecloudmessagingapp.databinding.FragmentMainBinding
import com.example.myfirebasecloudmessagingapp.databinding.FragmentNotificationsBinding

class MainFragment : Fragment(R.layout.fragment_main) {
    private var _binding : FragmentMainBinding? = null
    private val binding get() = _binding!!


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMainBinding.bind(view)

        binding.button.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_notificationsFragment2)
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}