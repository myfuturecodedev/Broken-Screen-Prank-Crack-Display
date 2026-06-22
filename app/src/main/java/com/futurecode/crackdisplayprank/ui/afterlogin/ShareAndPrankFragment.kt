package com.futurecode.crackdisplayprank.ui.afterlogin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.futurecode.crackdisplayprank.R
import com.futurecode.crackdisplayprank.base.BaseFragment
import com.futurecode.crackdisplayprank.databinding.FragmentShareAndPrankBinding

class ShareAndPrankFragment : BaseFragment<FragmentShareAndPrankBinding>(FragmentShareAndPrankBinding::inflate) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnCopyLink.setOnClickListener {

        }
    }

}