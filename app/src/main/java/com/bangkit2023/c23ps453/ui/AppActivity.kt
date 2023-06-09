package com.bangkit2023.c23ps453.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import com.bangkit2023.c23ps453.databinding.ActivityAppBinding
import com.bangkit2023.c23ps453.ui.authentication.login.LoginActivity
import com.bangkit2023.c23ps453.ui.measuring.MeasuringBodyActivity
import com.bangkit2023.c23ps453.ui.step.StepActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

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
        binding.ivLogout.setOnClickListener {
            Firebase.auth.signOut()
            val intent = Intent(this, LoginActivity::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val intent = Intent(Intent.ACTION_MAIN)
                intent.addCategory(Intent.CATEGORY_HOME)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            }
        }
        this.onBackPressedDispatcher.addCallback(this,callback)
    }
}