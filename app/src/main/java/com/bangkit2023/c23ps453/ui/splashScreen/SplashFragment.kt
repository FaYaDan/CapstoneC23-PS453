package com.bangkit2023.c23ps453.ui.splashScreen

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bangkit2023.c23ps453.R
import com.bangkit2023.c23ps453.databinding.FragmentSplashBinding

class SplashFragment : Fragment() {


    private var _binding : FragmentSplashBinding? = null
    private val binding get() = _binding!!

    companion object {
        private const val DURATION: Long = 1500
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSplashBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Handler(Looper.getMainLooper()).postDelayed({
            findNavController().navigate(R.id.action_splashScreenFragment_to_appActivity)
            requireActivity().finish()
        }, DURATION)
    }
}