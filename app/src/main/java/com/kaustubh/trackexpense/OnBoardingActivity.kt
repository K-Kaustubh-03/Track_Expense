package com.kaustubh.trackexpense

import android.content.Intent
import android.graphics.Color
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.viewpager2.widget.ViewPager2
import com.kaustubh.trackexpense.databinding.ActivityOnBoardingBinding

class OnBoardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnBoardingBinding

    private lateinit var onBoardingItems: Array<OnBoardingItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnBoardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
    }

    private fun setupUI() {

        window.navigationBarColor=Color.parseColor("#FFFFFF")

        val myAdapter = setOnBoardingItems()
        val myViewPager = binding.viewPagerOnBoarding
        val myButton = binding.buttonOnBoarding

        myViewPager.adapter = myAdapter

        setOnBoardingIndicators(myAdapter)
        setOnBoardingCurrentIndicator(0)
        myViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setOnBoardingCurrentIndicator(position)

                myButton.text = if (myViewPager.currentItem + 1 < myAdapter.itemCount) {
                    "Next"
                } else {
                    "Let's Go!"
                }
                myButton.setBackgroundColor(Color.parseColor(onBoardingItems[position].buttonBackground))
                myButton.setTextColor(Color.parseColor(onBoardingItems[position].buttonTextColor))
            }
        })
        myButton.setOnClickListener {
            if (myViewPager.currentItem + 1 < myAdapter.itemCount) {
                myViewPager.currentItem += 1
            } else {
                startActivity(Intent(this,UserActivity::class.java))
                finish()
            }
        }
    }

    private fun setOnBoardingItems(): OnBoardingItemAdapter {
        onBoardingItems = arrayOf(
            OnBoardingItem(
                R.raw.on_boarding_8,
                "Welcome!",
                "Start tracking your expenses and achieving your\nfinancial goals today! Welcome aboard!",
                "#01579B", "#29B6F6",
                "#B3E5FC", "#01579B"
            ),
            OnBoardingItem(
                R.raw.on_boarding_1,
                "Manage Your Finances",
                "Our powerful tools and intuitive interface make\nit effortless to stay on top of your money.",
                "#1B5E20", "#66BB6A",
                "#C8E6C9", "#1B5E20"
            ),
            OnBoardingItem(
                R.raw.on_boarding_10,
                "Get Smart Analysis",
                "Gain valuable insights into your spending patterns\nand make decisions about your finances.",
                "#E65100", "#FFA726",
                "#FFE0B2", "#E65100"
            ),
            OnBoardingItem(
                R.raw.on_boarding_11,
                "Control Your Life",
                "Take control of your financial future and live\nlife on your terms with Track Expense.",
                "#004D40", "#26A69A",
                "#B2DFDB", "#004D40"
            )
        )
        return OnBoardingItemAdapter(onBoardingItems)
    }

    private fun setOnBoardingIndicators(myAdapter: OnBoardingItemAdapter) {
        val layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins(8, 0, 8, 0)
        val indicators = arrayOfNulls<ImageView>(myAdapter.itemCount)
        for (i in indicators.indices) {
            indicators[i] = ImageView(applicationContext)
            indicators[i]?.apply {
                this.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.on_boarding_indicator_inactive
                    )
                )
                this.layoutParams = layoutParams
            }
            binding.linearLayoutIndicators.addView(indicators[i])
        }
    }

    private fun setOnBoardingCurrentIndicator(index: Int) {
        for (i in 0 until binding.linearLayoutIndicators.childCount) {
            val imageView = binding.linearLayoutIndicators[i] as ImageView
            if (i == index) {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.on_boarding_indicator_active
                    )
                )
                imageView.drawable.setTint(Color.parseColor(onBoardingItems[index].buttonTextColor))
            } else {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.on_boarding_indicator_inactive
                    )
                )
                imageView.drawable.setTint(Color.parseColor(onBoardingItems[index].buttonBackground))
            }
        }
    }
}