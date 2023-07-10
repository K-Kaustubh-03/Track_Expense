package com.kaustubh.trackexpense

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.kaustubh.trackexpense.databinding.ActivityUserBinding


class UserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserBinding

    private val isLoggedIn by lazy {
        getSharedPreferences("on_board_and_login", Context.MODE_PRIVATE)
    }

    private val accountDetails by lazy {
        getSharedPreferences("account_details", Context.MODE_PRIVATE)
    }

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private val RC_SIGN_IN = 20

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        setupUI()
        setupGoogleSignIn()
    }

    private fun setupGoogleSignIn() {

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        FirebaseAuth.getInstance().signOut()
        googleSignInClient.signOut()

        binding.buttonGoogle.setOnClickListener {
            startActivityForResult(googleSignInClient.signInIntent, RC_SIGN_IN)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuth(account.idToken)
            } catch (e: Exception) {
                Toast.makeText(this, "Login Cancelled", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun firebaseAuth(idToken: String?) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val user = auth.currentUser

//                    val accountDetails =
//                        getSharedPreferences("account_details", Context.MODE_PRIVATE)
                    accountDetails?.edit { putString("name", user?.displayName) }
                    accountDetails?.edit { putString("id", user?.uid) }
                    accountDetails?.edit { putString("photo", user?.photoUrl.toString()) }

//                    Toast.makeText(activity, "${user?.displayName}", Toast.LENGTH_SHORT).show()
//                    val isLoggedIn =
//                        getSharedPreferences("on_board_and_login", Context.MODE_PRIVATE)
                    isLoggedIn?.edit { putBoolean("onBoard", true) }
                    finish()
                    startActivity(Intent(this, MainActivity::class.java))
                } else {
                    Toast.makeText(this, "Something went Wrong!", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun setupUI() {
        var email: String
        var password: String
        window.navigationBarColor = Color.parseColor("#FFFFFF")
        binding.buttonSign.isEnabled = false
        binding.buttonSign.setTextColor(Color.parseColor("#C8E6C9"))
        binding.buttonSign.setBackgroundColor(Color.parseColor("#1B5E20"))
        binding.buttonContinue.setOnClickListener {
            email = binding.editTextEmailAddress.text.toString()
            password = binding.editTextPassword.text.toString()
            if (email != "" && password != "") {
                checkSignInMethods(true, email, password)
            } else {
                Toast.makeText(this, "Insufficient Data", Toast.LENGTH_SHORT).show()
            }
        }

        binding.buttonSign.setOnClickListener {
            binding.buttonLogin.isEnabled = true
            binding.buttonLogin.setTextColor(Color.parseColor("#1B5E20"))
            binding.buttonLogin.setBackgroundColor(Color.parseColor("#C8E6C9"))
            binding.buttonSign.isEnabled = false
            binding.buttonSign.setTextColor(Color.parseColor("#C8E6C9"))
            binding.buttonSign.setBackgroundColor(Color.parseColor("#1B5E20"))
            binding.buttonContinue.setOnClickListener {
                email = binding.editTextEmailAddress.text.toString()
                password = binding.editTextPassword.text.toString()
                if (email != "" && password != "") {
                    checkSignInMethods(true, email, password)
                } else {
                    Toast.makeText(this, "Insufficient Data", Toast.LENGTH_SHORT).show()
                }
            }
        }
        binding.buttonLogin.setOnClickListener {
            binding.buttonSign.isEnabled = true
            binding.buttonSign.setTextColor(Color.parseColor("#1B5E20"))
            binding.buttonSign.setBackgroundColor(Color.parseColor("#C8E6C9"))
            binding.buttonLogin.isEnabled = false
            binding.buttonLogin.setTextColor(Color.parseColor("#C8E6C9"))
            binding.buttonLogin.setBackgroundColor(Color.parseColor("#1B5E20"))
            binding.buttonContinue.setOnClickListener {
                email = binding.editTextEmailAddress.text.toString()
                password = binding.editTextPassword.text.toString()
                if (email != "" && password != "") {
                    checkSignInMethods(false, email, password)
                } else {
                    Toast.makeText(this, "Insufficient Data", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun checkSignInMethods(ifSignIn: Boolean, email: String, password: String) {
        var checkSignIn = ""
        auth.fetchSignInMethodsForEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val signInMethods = task.result?.signInMethods
                    if (signInMethods.isNullOrEmpty()) {
                        checkSignIn = "no_account"
                    } else {
                        for (signInMethod in signInMethods) {
                            checkSignIn = when (signInMethod) {
                                "password" -> {
                                    "password"
                                }

                                "google.com" -> {
                                    "google.com"
                                }

                                else -> {
                                    "error"
                                }
                            }
                        }
                    }
                } else {
                    checkSignIn = "unable_to_check"
                }
                onContinueClicked(ifSignIn, email, password, checkSignIn)
            }
    }

    private fun onContinueClicked(
        ifSignIn: Boolean,
        email: String,
        password: String,
        checkSignIn: String
    ) {
        when (checkSignIn) {
            "no_account" -> if (ifSignIn) {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(this, "Account Creation Successful", Toast.LENGTH_LONG)
                                .show()
                            val user = auth.currentUser
//                        val accountDetails =
//                            getSharedPreferences("account_details", Context.MODE_PRIVATE)
                            accountDetails?.edit { putString("name", email) }
                            accountDetails?.edit { putString("id", user?.uid) }
                            accountDetails?.edit { putString("photo", user?.photoUrl.toString()) }
//                        val isLoggedIn =
//                            getSharedPreferences("on_board_and_login", Context.MODE_PRIVATE)
                            isLoggedIn?.edit { putBoolean("onBoard", true) }
                            finish()
                            startActivity(Intent(this, MainActivity::class.java))
                        } else {
                            Toast.makeText(baseContext, "Something went wrong", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
            } else {
                Toast.makeText(this, "Create an Account First", Toast.LENGTH_SHORT).show()
            }

            "password" -> if (ifSignIn) {
                Toast.makeText(this, "Account Already Exists", Toast.LENGTH_SHORT).show()
            } else {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "Login Successful", Toast.LENGTH_LONG)
                                .show()
                            val user = auth.currentUser
//                        val accountDetails =
//                            getSharedPreferences("account_details", Context.MODE_PRIVATE)
                            accountDetails?.edit { putString("name", email) }
                            accountDetails?.edit { putString("id", user?.uid) }
                            accountDetails?.edit { putString("photo", user?.photoUrl.toString()) }
//                        val isLoggedIn =
//                            getSharedPreferences("on_board_and_login", Context.MODE_PRIVATE)
                            isLoggedIn?.edit { putBoolean("onBoard", true) }
                            finish()
                            startActivity(Intent(this, MainActivity::class.java))
                        } else {
                            Toast.makeText(
                                baseContext,
                                "Authentication Failed",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

            }

            "google.com" -> Toast.makeText(
                this,
                "Google Account Already linked",
                Toast.LENGTH_SHORT
            ).show()

            else -> Toast.makeText(this, "Account Check Error", Toast.LENGTH_SHORT).show()
        }
    }
}