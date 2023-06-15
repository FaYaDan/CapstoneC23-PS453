package com.bangkit2023.c23ps453.ui.resultsMeasuring

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bangkit2023.c23ps453.databinding.ActivityResultMeasuringBinding
import com.bangkit2023.c23ps453.ui.AppActivity
import com.bangkit2023.c23ps453.ui.MainViewModelFactory


class ResultMeasuringActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultMeasuringBinding

    companion object {
        const val TAG = "ResultMeasuringActivity"
        const val EXTRA_HEIGHT = "extra_height"
        const val EXTRA_WEIGHT = "extra_weight"
        const val EXTRA_AGE = "extra_age"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultMeasuringBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory : MainViewModelFactory = MainViewModelFactory.getInstance(this)
        val viewModel : ResultMeasuringViewModel by viewModels { factory }

        val vHeight = intent.getStringExtra(EXTRA_HEIGHT)
        val vWeight = intent.getStringExtra(EXTRA_WEIGHT)
        val vAge = intent.getStringExtra(EXTRA_AGE)

        when{
            vHeight!!.isEmpty() -> binding.tvHeight.text = "0"
            else -> binding.tvHeight.text = vHeight
        }

        when{
            vWeight!!.isEmpty() ->  binding.tvWeight.text = "0"
            else -> binding.tvWeight.text = vWeight
        }

        when{
            vAge!!.isEmpty() -> binding.tvAge.text = "0"
            else -> binding.tvAge.text = vAge
        }

        viewModel.predict(vAge, vHeight, vWeight).observe(this){ result->
            when(result){
                is com.bangkit2023.c23ps453.data.Result.Loading -> {
                    binding.progresbar.visibility = View.VISIBLE
                }
                is com.bangkit2023.c23ps453.data.Result.Success -> {
                    binding.progresbar.visibility = View.GONE
                    binding.tvHasil.text = result.data
                }
                is com.bangkit2023.c23ps453.data.Result.Error ->{
                    binding.progresbar.visibility = View.GONE
                    binding.tvHasil.text = "0"
                }
                else -> {binding.progresbar.visibility = View.GONE}
            }
        }

        binding.buttonSelesai.setOnClickListener {
            val intent = Intent(this, AppActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            this@ResultMeasuringActivity.finish()
        }
    }
}