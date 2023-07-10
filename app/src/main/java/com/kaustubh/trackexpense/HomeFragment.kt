package com.kaustubh.trackexpense

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.commit
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.kaustubh.trackexpense.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
//    private lateinit var auth: FirebaseAuth
//    private val authStateListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
//        val user: FirebaseUser? = firebaseAuth.currentUser
//        if (user != null) {
//            Toast.makeText(applicationContext, "Signed In", Toast.LENGTH_SHORT).show()
//        } else {
//            Toast.makeText(applicationContext, "No User", Toast.LENGTH_SHORT).show()
//        }
//    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        auth=FirebaseAuth.getInstance()
//        auth.addAuthStateListener(authStateListener)
//        auth.removeAuthStateListener(authStateListener)

        setBarChart()
        setSpinner()
    }

    private fun setSpinner() {
        binding.spinnerMonthSelector.adapter = activity?.let {
            ArrayAdapter(
                it, android.R.layout.simple_spinner_dropdown_item,
                arrayOf("Curr. Month", "Prev. Month", "Curr. Year", "Prev. Year")
            )
        }
    }

    private fun setBarChart() {
        val dayExpense = ArrayList<BarEntry>()
        dayExpense.add(BarEntry(0f, 654f))
        dayExpense.add(BarEntry(1f, 745f))
        dayExpense.add(BarEntry(2f, 363f))
        dayExpense.add(BarEntry(3f, 111f))
        dayExpense.add(BarEntry(4f, 362f))
        dayExpense.add(BarEntry(5f, 232f))
        dayExpense.add(BarEntry(6f, 987f))

        val barDataSet = BarDataSet(dayExpense, "Expense")
        val barChart = binding.barChartPastExpense

        barChart.xAxis.valueFormatter =
            IndexAxisValueFormatter(arrayOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"))
        barChart.xAxis.position=XAxis.XAxisPosition.BOTTOM
        barChart.axisLeft.isEnabled=false
        barChart.axisRight.isEnabled=false
        barChart.xAxis.setDrawGridLines(false)
        barDataSet.color = Color.parseColor("#80CBC4")
        barDataSet.valueTextColor = Color.BLACK
        barDataSet.valueTextSize = 16f

        barChart.setFitBars(true)
        barChart.data = BarData(barDataSet)
        barChart.description.isEnabled = false
        barChart.legend.isEnabled = false
        barChart.animateY(750)
        barChart.setTouchEnabled(false)
    }
}