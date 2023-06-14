package com.bangkit2023.c23ps453.ui.authentication.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import com.bangkit2023.c23ps453.databinding.ActivityLoginBinding
import com.bangkit2023.c23ps453.ui.AppActivity
import com.bangkit2023.c23ps453.ui.MainViewModelFactory
import com.bangkit2023.c23ps453.ui.authentication.register.RegisterActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory : MainViewModelFactory = MainViewModelFactory.getInstance(this)
        val viewModel : LoginViewModel by viewModels { factory }

        binding.tvRegister.setOnClickListener{
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        binding.btLogin.setOnClickListener{
            if (!Patterns.EMAIL_ADDRESS.matcher(binding.edLoginEmail.text).matches()) {
                binding.edLoginEmail.error = "Email Tidak Valid!!!!"
                binding.edLoginEmail.requestFocus()
            } else {
                viewModel.login(
                    binding.edLoginEmail.text.toString(),
                    binding.edLoginPassword.text.toString()
                ).observe(this){result ->
                    when(result){
                        is com.bangkit2023.c23ps453.data.Result.Loading -> {
                            binding.progresbarLogin.visibility = View.VISIBLE
                        }
                        is com.bangkit2023.c23ps453.data.Result.Success -> {
                            binding.progresbarLogin.visibility = View.GONE
                            val intent = Intent(this, AppActivity::class.java)
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                            finish()
                            Toast.makeText(this, "Login Berhasil", Toast.LENGTH_LONG).show()
                        }
                        is com.bangkit2023.c23ps453.data.Result.Error -> {
                            binding.progresbarLogin.visibility = View.GONE
                            Toast.makeText(this, "Login Gagal", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
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