package com.kaustubh.trackexpense

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.commit
import com.kaustubh.trackexpense.databinding.FragmentActionBarBinding

class ActionBarFragment : Fragment() {

    private lateinit var binding: FragmentActionBarBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentActionBarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.textViewTitle.text = setTitle()
        binding.cardViewNotification.setOnClickListener {
            startActivity(Intent(activity,NotificationsActivity::class.java))
        }
    }

    private fun setTitle() = when (parentFragment) {
        is HomeFragment -> "Home"
        is AnalysisFragment -> "Analysis"
        is TransactionsFragment -> "Transactions"
        is SettingsFragment -> "Settings"
        else -> ""
    }
}