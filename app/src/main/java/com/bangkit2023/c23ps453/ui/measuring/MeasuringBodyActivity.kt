package com.bangkit2023.c23ps453.ui.measuring

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.bangkit2023.c23ps453.databinding.ActivityMeasuringBodyBinding
import com.bangkit2023.c23ps453.ui.resultsMeasuring.ResultMeasuringActivity

class MeasuringBodyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMeasuringBodyBinding

    private var vHeight: String? = null
    private var vWeight: String? = null
    private var vAge: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMeasuringBodyBinding.inflate(layoutInflater)
        setContentView(binding.root)


        vHeight = binding.etHeight.text.toString()
        vWeight = binding.etWeight.text.toString()
        vAge = binding.etAge.text.toString()

        binding.etHeight.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(valueHeight: Editable?) {
                vHeight = valueHeight.toString()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        binding.etWeight.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(valueHeight: Editable?) {
                vWeight = valueHeight.toString()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        binding.etAge.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(valueHeight: Editable?) {
                vAge = valueHeight.toString()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        binding.ivMHeight.setOnClickListener  {mines(vHeight!!, 1) }
        binding.ivPHeight.setOnClickListener { plus(vHeight!!, 1) }

        binding.ivMWeight.setOnClickListener  {mines(vWeight!!, 2) }
        binding.ivPWeight.setOnClickListener { plus(vWeight!!, 2) }

        binding.ivMAge.setOnClickListener  {mines(vAge!!, 3) }
        binding.ivPAge.setOnClickListener { plus(vAge!!, 3) }

        binding.buttonSubmit.setOnClickListener {
            val intent = Intent(this@MeasuringBodyActivity, ResultMeasuringActivity::class.java)
            intent.putExtra(ResultMeasuringActivity.EXTRA_HEIGHT, vHeight)
            intent.putExtra(ResultMeasuringActivity.EXTRA_WEIGHT, vWeight)
            intent.putExtra(ResultMeasuringActivity.EXTRA_AGE, vAge)
            startActivity(intent)
        }
    }

    private fun plus(value: String, check: Int){
        if (value.isEmpty() || value.toInt() == 0){
            val valueEt = 1.toString()
            when(check) {
                1 -> binding.etHeight.setText(valueEt)
                2 -> binding.etWeight.setText(valueEt)
                else -> binding.etAge.setText(valueEt)
            }
        } else {
            var valueEt2 = value.toInt()
            valueEt2 += 1
            when(check){
                1 -> binding.etHeight.setText(valueEt2.toString())
                2 -> binding.etWeight.setText(valueEt2.toString())
                else -> binding.etAge.setText(valueEt2.toString())
            }
        }
    }
    private fun mines(value: String, check: Int){
        if (value.isEmpty() || value.toInt() <= 0){
            val valueEt = 0.toString()
            when(check) {
                1 -> binding.etHeight.setText(valueEt)
                2 -> binding.etWeight.setText(valueEt)
                else -> binding.etAge.setText(valueEt)
            }
        } else {
            var valueEt2 = value.toInt()
            valueEt2 -= 1
            when(check){
                1 -> binding.etHeight.setText(valueEt2.toString())
                2 -> binding.etWeight.setText(valueEt2.toString())
                else -> binding.etAge.setText(valueEt2.toString())
            }
        }
    }
}