package com.bangkit2023.c23ps453.ui.authentication.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.bangkit2023.c23ps453.databinding.ActivityRegisterBinding
import com.bangkit2023.c23ps453.ui.MainViewModelFactory
import com.bangkit2023.c23ps453.ui.authentication.login.LoginActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory : MainViewModelFactory = MainViewModelFactory.getInstance(this)
        val viewModel : RegisterViewModel by viewModels { factory }

        binding.tvLogin.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }

        binding.buttonRegister.setOnClickListener{
            val email = binding.edRegisterEmail.text.toString()
            val pass = binding.edRegisterPassword.text.toString()

            if (binding.edRegisterName.text.isEmpty()){
                binding.edRegisterName.error = "Nama Tidak Boleh Kosong"
            }
            else if (email.isEmpty()) {
                binding.edRegisterEmail.error = "Masukan Email!"
                binding.edRegisterEmail.requestFocus()
            }
            else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.edRegisterEmail.error = "Email Tidak Valid!!!!"
                binding.edRegisterEmail.requestFocus()
            }
            else if (pass.isEmpty()){
                binding.edRegisterPassword.error = "Masukan Password!"
                binding.edRegisterPassword.requestFocus()
            }
            else if (pass.length <= 8){
                binding.edRegisterPassword.error = "Password Kurang dari 8 karakter!"
                binding.edRegisterPassword.requestFocus()
            }
            else {
                viewModel.register(
                    binding.edRegisterName.text.toString(),
                    binding.edRegisterEmail.text.toString(),
                    binding.edRegisterPassword.text.toString()
                ).observe(this){ result ->
                    when(result){
                        is com.bangkit2023.c23ps453.data.Result.Loading -> {
                            binding.progressbar.visibility = View.VISIBLE
                        }
                        is com.bangkit2023.c23ps453.data.Result.Success -> {
                            binding.progressbar.visibility = View.GONE
                            Firebase.auth.signOut()
                            Toast.makeText(this, "Register Berhasil", Toast.LENGTH_LONG).show()
                            val intent = Intent(this, LoginActivity::class.java)
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                            finish()
                        }
                        is com.bangkit2023.c23ps453.data.Result.Error ->{
                            binding.progressbar.visibility = View.GONE
                            Toast.makeText(this, "Register Gagal", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }
}