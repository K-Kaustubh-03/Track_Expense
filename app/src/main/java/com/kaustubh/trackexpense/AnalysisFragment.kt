package com.kaustubh.trackexpense

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.airbnb.lottie.parser.ColorParser
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.kaustubh.trackexpense.databinding.FragmentAnalysisBinding

class AnalysisFragment : Fragment() {

    private lateinit var binding: FragmentAnalysisBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAnalysisBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setPieChart()
    }

    private fun setPieChart() {
        val expenseDistribution = ArrayList<PieEntry>()
        expenseDistribution.add(PieEntry(80f, "Clothing"))
        expenseDistribution.add(PieEntry(150f, "Food"))
        expenseDistribution.add(PieEntry(100f, "Travel"))
        expenseDistribution.add(PieEntry(200f, "Gadgets"))
        expenseDistribution.add(PieEntry(300f, "Education"))
        expenseDistribution.add(PieEntry(70f,"Healthcare"))
        expenseDistribution.add(PieEntry(125f, "Others"))

        val pieChart = binding.pieChartPastExpenseBreakdown
        val pieDataSet = PieDataSet(expenseDistribution, "Monthly Expense")

        pieDataSet.colors = arrayListOf(
            Color.parseColor("#C8E6C9"),
            Color.parseColor("#FFCDD2"),
            Color.parseColor("#B3E5FC"),
            Color.parseColor("#FFE0B2"),
            Color.parseColor("#80CBC4"),
            Color.parseColor("#FFCCBC"),
            Color.parseColor("#C5CAE9")
        )
        pieDataSet.valueTextColor = Color.BLACK
        pieDataSet.valueTextSize = 16f

        pieChart.data = PieData(pieDataSet)
        pieChart.description.isEnabled = false
        pieChart.legend.isEnabled = false
        pieChart.centerText = "Monthly\nExpense"
        pieChart.animateXY(750, 750)
    }
}