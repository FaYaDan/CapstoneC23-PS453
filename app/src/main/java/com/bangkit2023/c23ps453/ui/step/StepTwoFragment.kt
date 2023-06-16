package com.bangkit2023.c23ps453.ui.step

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bangkit2023.c23ps453.databinding.FragmentStepTwoBinding
import com.bangkit2023.c23ps453.ui.measuringCam.MeasuringCamActivity

class StepTwoFragment : Fragment() {

    private var _binding: FragmentStepTwoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentStepTwoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.arrow.setOnClickListener {
            val intent = Intent(requireContext(), MeasuringCamActivity::class.java)
            startActivity(intent)
        }
    }
}