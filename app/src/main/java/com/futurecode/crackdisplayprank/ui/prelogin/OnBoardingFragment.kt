package com.futurecode.crackdisplayprank.ui.prelogin

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.viewpager2.widget.ViewPager2
import com.futurecode.crackdisplayprank.R
import com.futurecode.crackdisplayprank.activity.MainActivity
import com.futurecode.crackdisplayprank.adapter.OnboardingAdapter
import com.futurecode.crackdisplayprank.base.BaseFragment
import com.futurecode.crackdisplayprank.databinding.FragmentOnBoardingBinding
import com.futurecode.crackdisplayprank.model.OnboardingModel

class OnBoardingFragment : BaseFragment<FragmentOnBoardingBinding>(FragmentOnBoardingBinding::inflate) {

    private lateinit var adapter: OnboardingAdapter
    private val onboardingPages = ArrayList<OnboardingModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initData()
        setupViewPager()
        setupListeners()
    }

    private fun initData() {
        onboardingPages.clear()

        // Page 1 Setup
        onboardingPages.add(
            OnboardingModel(
                titleLine1 = "Prank People",
                titleLine2 = "Around You",
                description = "Create realistic broken screen effects and surprise your friends with hilarious reactions.",
                illustrationResId = R.drawable.onboard_back, // Place your PNG vectors here
                crackBgResId = R.drawable.onboard_one // Background glass spider web lines
            )
        )

        // Page 2 Setup
        onboardingPages.add(
            OnboardingModel(
                titleLine1 = "Realistic Broken",
                titleLine2 = "Screen Effect",
                description = "Enjoy sharp crack visuals and glass damage effects that look real enough to prank your friends.",
                illustrationResId = R.drawable.onboard_two,
                crackBgResId = R.drawable.onboard_two
            )
        )

        // Page 3 Setup
        onboardingPages.add(
            OnboardingModel(
                titleLine1 = "Everyone Thinks Your",
                titleLine2 = "Phone Is Broken",
                description = "Make your screen look cracked for real and watch people react like your phone is actually damaged.",
                illustrationResId = R.drawable.onboard_three,
                crackBgResId = R.drawable.onboard_three
            )
        )
    }

    private fun setupViewPager() {
        adapter = OnboardingAdapter(onboardingPages)
        binding.viewPagerOnboarding.adapter = adapter

        // Prevent manual scroll bounce lag
        binding.viewPagerOnboarding.offscreenPageLimit = 3

        setupIndicators(onboardingPages.size)
        updateIndicators(0) // Initial active state dot

        binding.viewPagerOnboarding.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                updateIndicators(position)

                // If on final item screen, we can dynamically change "Next" button layout context if needed
                if (position == onboardingPages.size - 1) {
                    binding.btnNext.text = "Next "
                } else {
                    binding.btnNext.text = "Next  "
                }
            }
        })
    }

    private fun setupListeners() {
        binding.btnSkip.setOnClickListener {
            completeOnboarding()
        }

        binding.btnNext.setOnClickListener {
            val currentPos = binding.viewPagerOnboarding.currentItem
            if (currentPos < onboardingPages.size - 1) {
                binding.viewPagerOnboarding.setCurrentItem(currentPos + 1, true)
            } else {
                completeOnboarding()
            }
        }
    }

    private fun setupIndicators(count: Int) {
        binding.layoutIndicators.removeAllViews()
        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        ).apply {
            setMargins(8, 0, 8, 0)
        }

        for (i in 0 until count) {
            val indicatorView = ImageView(requireContext())
            indicatorView.layoutParams = params
            binding.layoutIndicators.addView(indicatorView)
        }
    }

    private fun updateIndicators(activeIndex: Int) {
        val count = binding.layoutIndicators.childCount
        for (i in 0 until count) {
            val view = binding.layoutIndicators.getChildAt(i) as ImageView
            if (i == activeIndex) {
                view.setImageResource(R.drawable.ic_indicator_active)
            } else {
                view.setImageResource(R.drawable.ic_indicator_inactive)
            }
        }
    }

    private fun completeOnboarding() {
        // Mark onboarding complete in preference state manager
        prefManager.isLanguageSelectedFirstTime = true // Route safely to Choose Language Selection
        (activity as? MainActivity)?.goToMain()

    }
}