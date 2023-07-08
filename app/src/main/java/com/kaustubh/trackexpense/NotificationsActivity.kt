package com.kaustubh.trackexpense

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.kaustubh.trackexpense.databinding.ActivityMainBinding
import com.kaustubh.trackexpense.databinding.ActivityNotificationsBinding

class NotificationsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNotificationsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.navigationBarColor= Color.parseColor("#FFFFFF")

        binding.imageViewBackButton.setOnClickListener {
            finish()
        }

        binding.animationComingSoon.setMinAndMaxProgress(0.0f,0.9f)
//        binding.animationComingSoon.playAnimation()
    }
}