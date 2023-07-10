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
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.edit
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.ktx.Firebase
import com.kaustubh.trackexpense.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding
//        private lateinit var auth: FirebaseAuth
//    private val authStateListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
//        val user: FirebaseUser? = firebaseAuth.currentUser
//        if (user != null) {
//            Toast.makeText(context, "Signed In", Toast.LENGTH_SHORT).show()
//        } else {
//            Toast.makeText(context, "No User", Toast.LENGTH_SHORT).show()
//        }
//    }

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
//
//        auth=FirebaseAuth.getInstance()
//        auth.addAuthStateListener(authStateListener)
//        auth.removeAuthStateListener(authStateListener)

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

                val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build()

                val googleSignInClient = context?.let { GoogleSignIn.getClient(it, gso) }!!

                FirebaseAuth.getInstance().signOut()
                googleSignInClient.signOut()

//                auth=FirebaseAuth.getInstance()
//                auth.addAuthStateListener(authStateListener)
//                auth.removeAuthStateListener(authStateListener)

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
        val accountDetails=activity?.getSharedPreferences("account_details",Context.MODE_PRIVATE)
        Glide.with(this)
            .load(accountDetails?.getString("photo",""))
            .apply(RequestOptions.circleCropTransform())
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(binding.imageViewPicture)
        binding.textViewAccountName.text=accountDetails?.getString("name","")
    }
}