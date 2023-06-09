package com.bangkit2023.c23ps453.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bangkit2023.c23ps453.databinding.ActivityAppBinding
import com.bangkit2023.c23ps453.databinding.ActivityMainBinding
import com.bangkit2023.c23ps453.ui.measuring.MeasuringBodyActivity
import com.bangkit2023.c23ps453.ui.step.StepActivity

class AppActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAppBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAppBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonManual.setOnClickListener {
            val intent = Intent(this@AppActivity, MeasuringBodyActivity::class.java)
            startActivity(intent)
        }
        binding.buttonCam.setOnClickListener {
            val intent = Intent(this@AppActivity, StepActivity::class.java)
            startActivity(intent)
        }
    }
}