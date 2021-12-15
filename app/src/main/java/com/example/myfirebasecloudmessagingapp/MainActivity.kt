package com.example.myfirebasecloudmessagingapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.myfirebasecloudmessagingapp.databinding.ActivityMainBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    private var bundle : Bundle? = null
    private var topic = "car_care"
    private var type = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        FirebaseMessaging.getInstance().subscribeToTopic(topic)


        //Retrieve the current registration token
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("TASK FAILED", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            // Log and toast
            Log.d("TOKEN TAG", token)
        })
//
//        try {
//            bundle = intent.extras
//            if (bundle != null) {
//                type = bundle!!.getInt("type")
//                when (type) {
//                    0 -> {
//                        binding.titleIdMA.text = bundle!!.getString("TITLE")
//                        binding.bodyIdMA.text = bundle!!.getString("BODY")
//                    }
//                    1 -> {
//                        binding.titleIdMA.text = bundle!!.getString("TITLE")
//                        binding.bodyIdMA.text = bundle!!.getString("BODY")
//                        bundle!!.getString("BODY")?.let { Log.d("NOTIFICATION TAG", it) }
//                    }
//                }
//            }
//        } catch (e: Exception) {
//            Log.d("TAG", "bundle is null")
//        }
    }

}