package com.futurecode.crackdisplayprank.ui.afterlogin

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.futurecode.crackdisplayprank.base.BaseFragment
import com.futurecode.crackdisplayprank.databinding.FragmentHowToUseBinding

class HowToUseFragment : BaseFragment<FragmentHowToUseBinding>(FragmentHowToUseBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.topBar.ivHelp.visibility= View.GONE
        binding.topBar.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }

    }
}