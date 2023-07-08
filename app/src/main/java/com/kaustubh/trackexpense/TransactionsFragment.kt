package com.kaustubh.trackexpense

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.marginTop
import com.airbnb.lottie.LottieAnimationView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import com.kaustubh.trackexpense.databinding.FragmentTransactionsBinding
import com.kaustubh.trackexpense.databinding.ItemTransactionLayoutBinding
import com.kaustubh.trackexpense.databinding.LayoutAddFinalPageBinding
import com.kaustubh.trackexpense.databinding.LayoutTransactionsAddIncomeBinding

class TransactionsFragment : Fragment() {

    private lateinit var binding: FragmentTransactionsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTransactionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val transactions = arrayOf(
            Transaction(
                2400.23,
                "Income",
                "Jonty",
                "",
                "Rolls",
                "Cash",
                "23:11",
                "24/12/2021",
                "No"
            ),
            Transaction(
                400.23,
                "Income",
                "Jonty",
                "",
                "Clothes",
                "UPI",
                "11:11",
                "22/01/2023",
                "Loan"
            ),
            Transaction(
                240.23,
                "Expense",
                "",
                "Lakshya",
                "Shoes",
                "Card",
                "03:10",
                "18/11/2024",
                "Savings"
            ),
            Transaction(
                2.23,
                "Income",
                "Jonty",
                "",
                "Rolls",
                "Mobile",
                "23:11",
                "24/12/2021",
                "No"
            ),
            Transaction(
                2400.23,
                "Income",
                "Jonty",
                "",
                "Rolls",
                "Cash",
                "23:11",
                "24/12/2021",
                "No"
            ),
            Transaction(
                400.23,
                "Income",
                "Jonty",
                "",
                "Clothes",
                "UPI",
                "11:11",
                "22/01/2023",
                "Loan"
            ),
            Transaction(
                240.23,
                "Expense",
                "",
                "Lakshya",
                "Shoes",
                "Card",
                "03:10",
                "18/11/2024",
                "Savings"
            ),
            Transaction(
                2.23,
                "Income",
                "Jonty",
                "",
                "Rolls",
                "Mobile",
                "23:11",
                "24/12/2021",
                "No"
            ),
            Transaction(
                2400.23,
                "Income",
                "Jonty",
                "",
                "Rolls",
                "Cash",
                "23:11",
                "24/12/2021",
                "No"
            ),
            Transaction(
                400.23,
                "Income",
                "Jonty",
                "",
                "Clothes",
                "UPI",
                "11:11",
                "22/01/2023",
                "Loan"
            )
        )
        binding.recyclerViewTransactions.adapter = TransactionAdapter(transactions)

        binding.cardViewAddIncome.setOnClickListener {
            setAddIncome()
        }
        binding.cardViewAddExpense.setOnClickListener {
            setAddExpense()
        }
    }

    private fun finalPage(isDelete: Boolean) {
        val bottomSheetDialogFinal =
            activity?.let { BottomSheetDialog(it, R.style.BottomSheetDialogTheme) }
        val bottomSheetViewFinal = LayoutInflater.from(activity).inflate(
            R.layout.layout_add_final_page,
            view?.findViewById(R.id.layout_quick_add_final_page)
        )
        bottomSheetDialogFinal?.setContentView(bottomSheetViewFinal)
        if (isDelete) {
            bottomSheetViewFinal.findViewById<LottieAnimationView>(R.id.animation_done)
                .setAnimation(R.raw.delete)
        }
        bottomSheetDialogFinal?.show()
    }

    private fun setAddIncome() {
        val bottomSheetDialogIncome =
            context?.let { BottomSheetDialog(it, R.style.BottomSheetDialogTheme) }
        val bottomSheetViewIncome = LayoutInflater.from(activity).inflate(
            R.layout.layout_transactions_add_income,
            view?.findViewById(R.id.layout_add_income)
        )
        bottomSheetDialogIncome?.setContentView(bottomSheetViewIncome)
        bottomSheetDialogIncome?.show()
        bottomSheetViewIncome.findViewById<Button>(R.id.button_add_income)
            ?.setOnClickListener {
                bottomSheetDialogIncome?.dismiss()
                saveData()
            }
        bottomSheetViewIncome.findViewById<MaterialButton>(R.id.button_delete_income).isEnabled =
            false
        bottomSheetViewIncome.findViewById<MaterialButton>(R.id.button_delete_income)
            .setBackgroundColor(
                Color.parseColor("#AAAAAA")
            )
    }

    private fun setAddExpense() {
        val bottomSheetDialogExpense =
            context?.let { BottomSheetDialog(it, R.style.BottomSheetDialogTheme) }
        val bottomSheetViewExpense = LayoutInflater.from(activity).inflate(
            R.layout.layout_transactions_add_expense,
            view?.findViewById(R.id.layout_add_expense)
        )
        bottomSheetDialogExpense?.setContentView(bottomSheetViewExpense)
        bottomSheetDialogExpense?.show()
        bottomSheetViewExpense.findViewById<MaterialButton>(R.id.button_add_expense)
            .setOnClickListener {
                bottomSheetDialogExpense?.dismiss()
                saveData()
            }
        bottomSheetViewExpense.findViewById<MaterialButton>(R.id.button_delete_expense)
            .setOnClickListener {
                val alertDialogBuilder = context?.let { AlertDialog.Builder(it, R.style.AlertDialogTheme) }
                val view = LayoutInflater.from(context).inflate(
                    R.layout.layout_alert,
                    view?.findViewById(R.id.layout_alert)
                )
                alertDialogBuilder?.setView(view)
                val alertDialog = alertDialogBuilder?.create()
                view.findViewById<TextView>(R.id.text_view_message).text = "Are you sure you want to\nDelete the Transaction?"
                view.findViewById<MaterialButton>(R.id.button_yes).setOnClickListener {
                    alertDialog?.dismiss()
                    bottomSheetDialogExpense?.dismiss()
                    finalPage(true)
                }
                alertDialog?.window?.setBackgroundDrawable(ColorDrawable(R.drawable.alert_background))
                alertDialog?.window?.setBackgroundDrawableResource(R.color.transparent)
                view.findViewById<MaterialButton>(R.id.button_no).setOnClickListener {
                    alertDialog?.dismiss()
                }
                alertDialog?.show()
            }
    }

    private fun saveData() {
        finalPage(false)
    }

}