package com.bangkit2023.c23ps453.ui.resultMeasuringCam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bangkit2023.c23ps453.databinding.ActivityResultMeasuringCamBinding
import com.bangkit2023.c23ps453.ui.AppActivity

class ResultMeasuringCamActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultMeasuringCamBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultMeasuringCamBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonSelesai.setOnClickListener {
            val intent = Intent(this@ResultMeasuringCamActivity, AppActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            this@ResultMeasuringCamActivity.finish()
        }

    }
}