package com.kaustubh.trackexpense

import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.edit
import com.google.android.material.button.MaterialButton
import com.kaustubh.trackexpense.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonLogout.setOnClickListener {
            val alertDialogBuilder = context?.let { AlertDialog.Builder(it, R.style.AlertDialogTheme) }
            val viewDialog = LayoutInflater.from(context).inflate(
                R.layout.layout_alert,
                view.findViewById(R.id.layout_alert)
            )
            alertDialogBuilder?.setView(viewDialog)
            val alertDialog = alertDialogBuilder?.create()
            viewDialog.findViewById<TextView>(R.id.text_view_message).text = "Are you sure you want\nto log out?"
            viewDialog.findViewById<MaterialButton>(R.id.button_yes).setOnClickListener {
                alertDialog?.dismiss()
                val loggedOut = activity?.getSharedPreferences("on_board_and_login", Context.MODE_PRIVATE)
                loggedOut?.edit { putBoolean("login",false) }
                startActivity(Intent(activity,UserActivity::class.java))
                activity?.finish()
            }
            alertDialog?.window?.setBackgroundDrawable(ColorDrawable(R.drawable.alert_background))
            alertDialog?.window?.setBackgroundDrawableResource(R.color.transparent)
            viewDialog.findViewById<MaterialButton>(R.id.button_no).setOnClickListener {
                alertDialog?.dismiss()
            }
            alertDialog?.show()
        }
    }
}