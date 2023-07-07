package com.kaustubh.trackexpense

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TransactionAdapter(private val transactions: Array<Transaction>) :
    RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>() {

    override fun getItemCount() = transactions.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = TransactionViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_transaction_layout, parent, false)
    )

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        holder.bind(transactions[position])
    }

    class TransactionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val transAmount: TextView = itemView.findViewById(R.id.text_view_transaction_value)
        private val transImage: ImageView = itemView.findViewById(R.id.image_view_transaction_image)
        private val transReason: TextView =
            itemView.findViewById(R.id.text_view_transaction_reason_title)
        private val transReasonValue: TextView =
            itemView.findViewById(R.id.text_view_transaction_reason_value)
        private val transMode: ImageView = itemView.findViewById(R.id.image_view_transaction_mode)
        private val transTime: TextView = itemView.findViewById(R.id.text_view_transaction_time)
        private val transDate: TextView = itemView.findViewById(R.id.text_view_transaction_date)
        private val transFor: TextView =
            itemView.findViewById(R.id.text_view_transaction_for_reason_value)
        private val transSpecial: ImageView =
            itemView.findViewById(R.id.image_view_transaction_special_image)

        fun bind(transaction: Transaction) {
            transTime.text = transaction.time
            transDate.text = transaction.date
            transFor.text = transaction.forReason
            if (transaction.type == "Income") {
                transAmount.text = "+$" + transaction.amount.toString()
                transAmount.setTextColor(Color.parseColor("#4CAF50"))
                transImage.setImageResource(R.drawable.arrow_down_left)
                transReason.text = "From :"
                transReasonValue.text = transaction.from
            } else {
                transAmount.text = "-$" + transaction.amount.toString()
                transAmount.setTextColor(Color.parseColor("#F44336"))
                transImage.setImageResource(R.drawable.arrow_up_right)
                transReason.text = "To :"
                transReasonValue.text = transaction.to
            }
            when (transaction.mode) {
                "Cash" -> transMode.setImageResource(R.drawable.money_bill_wave)
                "UPI" -> transMode.setImageResource(R.drawable.upi)
                "Mobile" -> transMode.setImageResource(R.drawable.mobile_button)
                "Card" -> transMode.setImageResource(R.drawable.credit_card)
            }
            when (transaction.distinct) {
                "Savings" -> transSpecial.setImageResource(R.drawable.piggy_bank)
                "Loan" -> transSpecial.setImageResource(R.drawable.wallet)
                else -> {}
            }
        }
    }
}