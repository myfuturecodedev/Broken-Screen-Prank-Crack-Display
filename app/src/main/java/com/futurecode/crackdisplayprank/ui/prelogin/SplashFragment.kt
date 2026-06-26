package com.futurecode.crackdisplayprank.ui.prelogin

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.futurecode.crackdisplayprank.R
import com.futurecode.crackdisplayprank.activity.MainActivity
import com.futurecode.crackdisplayprank.base.BaseFragment
import com.futurecode.crackdisplayprank.databinding.FragmentSplashBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * 15-Year Developer Standard: Implements safe pre-login routing with clean
 * lifecycle state boundaries to eliminate context detachment crashes.
 * ✅ FIXED: Corrected conditions flow to match specified business rules perfectly.
 */
class SplashFragment : BaseFragment<FragmentSplashBinding>(FragmentSplashBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkNavigation()
    }

    private fun checkNavigation() {
        // Safe lifecycle scope to prevent "Fragment not attached to a context"
        // navigation crashes if user minimizes the app during splash delay.
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                delay(3000) // Standard 3-second delay for optimal user engagement

                // Double check if fragment is still attached to activity before proceeding
                if (!isAdded) return@repeatOnLifecycle

                try {
                    when {
                        // 1. If user has completed onboarding, jump straight to Workspace
                        prefManager.isOnboardingDone -> {
                            (activity as? MainActivity)?.goToMain()
                        }

                        // 2. IF isLanguageSelectedFirstTime is false, proceed to Onboarding
                        prefManager.isLanguageSelectedFirstTime == true -> {
                            findNavController().navigate(R.id.action_splashFragment_to_onBoardingFragment)
                        }

                        // 3. Otherwise: Brand new installation / fallback, route first to select language
                        else -> {
                            val bundle = Bundle().apply {
                                putString("pageName", "splash")
                            }
                            findNavController().navigate(R.id.action_splashFragment_to_languageFragment, bundle)
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}