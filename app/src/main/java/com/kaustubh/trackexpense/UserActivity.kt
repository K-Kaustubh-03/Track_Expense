package com.kaustubh.trackexpense

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.commit
import com.kaustubh.trackexpense.databinding.ActivityUserBinding

class UserActivity : AppCompatActivity() {

    private lateinit var binding:ActivityUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityUserBinding.inflate(layoutInflater)
        setContentView (binding.root)

        setupUI()
    }

    private fun setupUI() {
        binding.buttonSign.isEnabled = false
        binding.buttonSign.setTextColor(Color.parseColor("#C8E6C9"))
        binding.buttonSign.setBackgroundColor(Color.parseColor("#1B5E20"))

        binding.buttonSign.setOnClickListener {
            binding.buttonLogin.isEnabled = true
            binding.buttonLogin.setTextColor(Color.parseColor("#1B5E20"))
            binding.buttonLogin.setBackgroundColor(Color.parseColor("#C8E6C9"))
            binding.buttonSign.isEnabled = false
            binding.buttonSign.setTextColor(Color.parseColor("#C8E6C9"))
            binding.buttonSign.setBackgroundColor(Color.parseColor("#1B5E20"))
            supportFragmentManager.commit {
                replace(R.id.frame_login_or_sign, UserSignUpFragment())
            }
        }
        binding.buttonLogin.setOnClickListener {
            binding.buttonSign.isEnabled = true
            binding.buttonSign.setTextColor(Color.parseColor("#1B5E20"))
            binding.buttonSign.setBackgroundColor(Color.parseColor("#C8E6C9"))
            binding.buttonLogin.isEnabled = false
            binding.buttonLogin.setTextColor(Color.parseColor("#C8E6C9"))
            binding.buttonLogin.setBackgroundColor(Color.parseColor("#1B5E20"))
            supportFragmentManager.commit {
                replace(R.id.frame_login_or_sign, UserLoginFragment())
            }
        }
    }
}