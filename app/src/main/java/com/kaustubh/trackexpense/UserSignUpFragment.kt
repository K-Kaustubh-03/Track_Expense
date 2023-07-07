package com.kaustubh.trackexpense

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kaustubh.trackexpense.databinding.FragmentUserSignUpBinding

class UserSignUpFragment : Fragment() {

    private lateinit var binding: FragmentUserSignUpBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }
}