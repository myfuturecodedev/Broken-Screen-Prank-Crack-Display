package com.futurecode.crackdisplayprank.ui.afterlogin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.futurecode.crackdisplayprank.R
import com.futurecode.crackdisplayprank.base.BaseFragment
import com.futurecode.crackdisplayprank.databinding.FragmentBrokenEffectBinding

class BrokenEffectFragment : BaseFragment<FragmentBrokenEffectBinding>(FragmentBrokenEffectBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


    override fun onResume() {
        super.onResume()
        requireActivity().window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
    }

    override fun onPause() {
        super.onPause()

        requireActivity().window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_VISIBLE
    }

}