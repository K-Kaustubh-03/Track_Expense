package com.kaustubh.trackexpense

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.google.android.material.button.MaterialButton

class OnBoardingItemAdapter(private val onBoardingItems: Array<OnBoardingItem>) :
    RecyclerView.Adapter<OnBoardingItemAdapter.OnBoardingItemViewHolder>() {

    override fun getItemCount() = onBoardingItems.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = OnBoardingItemViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_container_on_boarding, parent, false)
    )

    override fun onBindViewHolder(holder: OnBoardingItemViewHolder, position: Int) {
        holder.bind(onBoardingItems[position])
    }

    class OnBoardingItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val textTitle: TextView = itemView.findViewById(R.id.text_view_on_boarding_title)
        private val textDescription: TextView =
            itemView.findViewById(R.id.text_view_on_boarding_description)
        private val lottieAnimation: LottieAnimationView =
            itemView.findViewById(R.id.animation_on_board)
//        private val buttonOnBoarding:MaterialButton=itemView.findViewById(R.id.button_on_boarding)

        fun bind(onBoardingItem: OnBoardingItem) {
            textTitle.text = onBoardingItem.title
            textDescription.text = onBoardingItem.description
            lottieAnimation.setAnimation(onBoardingItem.lottie)
            textTitle.setTextColor(Color.parseColor(onBoardingItem.titleColor))
            textDescription.setTextColor(Color.parseColor(onBoardingItem.descriptionColor))
        }
    }
}