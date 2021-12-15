package com.example.myfirebasecloudmessagingapp

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myfirebasecloudmessagingapp.databinding.FragmentNotificationsBinding

class NotificationsFragment : Fragment(R.layout.fragment_notifications) {
    private var _binding : FragmentNotificationsBinding? = null
    private val binding get() = _binding!!


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentNotificationsBinding.bind(view)

        val sharedPref = requireActivity().getSharedPreferences("MY_PREF", Context.MODE_PRIVATE) ?: return
        val title = sharedPref.getString("TITLE", "default title")
        val body = sharedPref.getString("BODY", "default body")

        binding.title.text = title
        binding.body.text = body

        if (title != null) {
            Log.d("TITLE_TAG", title)
        }



    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}