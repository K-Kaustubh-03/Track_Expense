package com.kaustubh.trackexpense

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.Window
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.edit
import androidx.fragment.app.commit
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.navigation.NavigationBarView.OnItemSelectedListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.kaustubh.trackexpense.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), OnItemSelectedListener {

    private lateinit var binding: ActivityMainBinding

    private var flag = false

    private lateinit var bottomSheetDialog: BottomSheetDialog

    private val isFirstTime: SharedPreferences by lazy {
        getSharedPreferences("on_board_and_login", Context.MODE_PRIVATE)
    }

    private lateinit var auth: FirebaseAuth
//    private val authStateListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
//        val user: FirebaseUser? = firebaseAuth.currentUser
//        if (user == null) {
//            finish()
//            startActivity(Intent(this, UserActivity::class.java))
//        }
//    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        val alertDialogBuilder = AlertDialog.Builder(this, R.style.AlertDialogTheme)
        val view = LayoutInflater.from(this).inflate(
            R.layout.layout_alert,
            findViewById(R.id.layout_alert)
        )
        alertDialogBuilder.setView(view)
        val alertDialog = alertDialogBuilder.create()
        view.findViewById<TextView>(R.id.text_view_message).text = "Are you sure you want to exit?"
        view.findViewById<MaterialButton>(R.id.button_yes).setOnClickListener {
            finish()
        }
        alertDialog.window?.setBackgroundDrawable(ColorDrawable(R.drawable.alert_background))
        alertDialog.window?.setBackgroundDrawableResource(R.color.transparent)
        view.findViewById<MaterialButton>(R.id.button_no).setOnClickListener {
            alertDialog.dismiss()
        }
        alertDialog.show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // disabling night mode for the entire app
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        if (!isFirstTime.contains("onBoard")) {
            isFirstTime.edit { putBoolean("onBoard", false) }
            startActivity(Intent(this, OnBoardingActivity::class.java))
            finish()
        } else if (!isFirstTime.getBoolean("onBoard", false)) {
            startActivity(Intent(this, OnBoardingActivity::class.java))
            finish()
        } else {
//            auth = FirebaseAuth.getInstance()
//            auth.addAuthStateListener(authStateListener)
//            auth.removeAuthStateListener(authStateListener)
            auth = FirebaseAuth.getInstance()
            if(auth.currentUser==null){
                finish()
                startActivity(Intent(this, UserActivity::class.java))
            }
        }
//        else if (!isFirstTime.getBoolean("login",false)){
//            startActivity(Intent(this,UserActivity::class.java))
//            finish()
//        }

        binding.bottomNav.setOnItemSelectedListener(this)

        binding.floatingButtonAdd.setOnClickListener { setFAB() }
    }

    private fun setFAB() {
        bottomSheetDialog = BottomSheetDialog(this, R.style.BottomSheetDialogTheme)
        val bottomSheetView = LayoutInflater.from(this).inflate(
            R.layout.layout_bottom_sheet_quick_add_transaction,
            findViewById<ConstraintLayout>(R.id.linear_layout_quick_add_buttons)
        )
        bottomSheetView.findViewById<MaterialCardView>(R.id.card_view_quick_add_income)
            .setOnClickListener {
                setIncomeFab()
            }
        bottomSheetView.findViewById<MaterialCardView>(R.id.card_view_quick_add_expense)
            .setOnClickListener {
                setExpenseFab()
            }
        bottomSheetDialog.setContentView(bottomSheetView)
        bottomSheetDialog.show()
    }

    private fun finalPage() {
        val bottomSheetDialogFinal = BottomSheetDialog(this, R.style.BottomSheetDialogTheme)
        val bottomSheetViewFinal = LayoutInflater.from(this).inflate(
            R.layout.layout_add_final_page,
            findViewById<ConstraintLayout>(R.id.layout_quick_add_final_page)
        )
        bottomSheetDialogFinal.setContentView(bottomSheetViewFinal)
        bottomSheetDialogFinal.show()
    }

    private fun setIncomeFab() {
        val bottomSheetDialogIncome = BottomSheetDialog(this, R.style.BottomSheetDialogTheme)
        val bottomSheetViewIncome = LayoutInflater.from(this).inflate(
            R.layout.layout_bottom_sheet_quick_add_income,
            findViewById<ConstraintLayout>(R.id.layout_quick_add_income)
        )
        bottomSheetDialogIncome.setContentView(bottomSheetViewIncome)
        bottomSheetDialogIncome.show()
        bottomSheetViewIncome.findViewById<MaterialButton>(R.id.button_quick_add_income)
            .setOnClickListener {
                bottomSheetDialogIncome.dismiss()
                saveQuickData()
            }
    }

    private fun setExpenseFab() {
        val bottomSheetDialogExpense = BottomSheetDialog(this, R.style.BottomSheetDialogTheme)
        val bottomSheetViewExpense = LayoutInflater.from(this).inflate(
            R.layout.layout_bottom_sheet_quick_add_expense,
            findViewById<ConstraintLayout>(R.id.layout_quick_add_expense)
        )
        bottomSheetDialogExpense.setContentView(bottomSheetViewExpense)
        bottomSheetDialogExpense.show()
        bottomSheetViewExpense.findViewById<MaterialButton>(R.id.button_quick_add_expense)
            .setOnClickListener {
                bottomSheetDialogExpense.dismiss()
                saveQuickData()
            }
    }

    // update this function
    private fun saveQuickData() {
        flag = true
        finalPage()
        bottomSheetDialog.dismiss()
    }

    override fun onNavigationItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.nav_home -> onHomeClicked()
        R.id.nav_analysis -> onAnalysisClicked()
        R.id.nav_transactions -> onTransactionsClicked()
        R.id.nav_settings -> onSettingsClicked()
        else -> false
    }

    private fun onHomeClicked(): Boolean {
        supportFragmentManager.commit {
            replace(R.id.frame_content, HomeFragment())
        }
        //onNavigationItemSelected(item=MenuItem)
        return true
    }

    private fun onAnalysisClicked(): Boolean {
        supportFragmentManager.commit {
            replace(R.id.frame_content, AnalysisFragment())
        }
        return true
    }

    private fun onTransactionsClicked(): Boolean {
        supportFragmentManager.commit {
            replace(R.id.frame_content, TransactionsFragment())
        }
        return true
    }

    private fun onSettingsClicked(): Boolean {
        supportFragmentManager.commit {
            replace(R.id.frame_content, SettingsFragment())
        }
        return true
    }
}