package com.kaustubh.trackexpense

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.edit
import com.kaustubh.trackexpense.databinding.FragmentUserLoginBinding

class UserLoginFragment : Fragment() {

    private lateinit var binding: FragmentUserLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val isLoggedIn = activity?.getSharedPreferences("on_board_and_login", Context.MODE_PRIVATE)
        if (!isLoggedIn?.contains("login")!!) {
            isLoggedIn.edit { putBoolean("login", false) }
        } else if (isLoggedIn.getBoolean("login", false)) {
            startActivity(Intent(context, MainActivity::class.java))
            activity?.finish()
        }

        binding.buttonLoginContinue.setOnClickListener {
            isLoggedIn.edit {
                putBoolean("login", true)
                putBoolean("onBoard", true)
            }
            startActivity(Intent(context, MainActivity::class.java))
            activity?.finish()
        }
        binding.buttonLoginGoogle.setOnClickListener {
            isLoggedIn.edit {
                putBoolean("login", true)
                putBoolean("onBoard", true)
            }
            startActivity(Intent(context, MainActivity::class.java))
            activity?.finish()
        }
    }
}