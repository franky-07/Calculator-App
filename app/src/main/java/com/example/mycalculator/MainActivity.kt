package com.example.mycalculator

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.mycalculator.databinding.ActivityMainBinding
import kotlin.ArithmeticException

class MainActivity : AppCompatActivity() {
    private var lastNum = false
    var lastDot = false
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun onDigit(view: View) {
        binding.tvInput.append((view as Button).text)
        lastNum = true
    }

    fun onClear(view: View) {
        binding.tvInput.text = ""
        lastDot = false
        lastNum = false
    }

    fun onDot(view: View) {
        if (lastNum && !lastDot) {
            binding.tvInput.append((view as Button).text)
            lastNum = false
            lastDot = true
        }
    }

    @SuppressLint("SetTextI18n")
    fun onEqual(view: View) {
        if (lastNum) {
            var tvValue = binding.tvInput.text.toString()
            var prefix = ""
            try {
                if (tvValue.startsWith("-")) {
                    prefix = "-"
                    tvValue = tvValue.substring(1)
                }
                if (tvValue.contains("-")) {
                    var splitValue = tvValue.split("-")
                    var one = splitValue[0]
                    var two = splitValue[1]
                    if (!prefix.isEmpty()) {
                        one = prefix + one
                    }
                    binding.tvInput.text = remove0((one.toDouble() - two.toDouble()).toString())
                } else if (tvValue.contains("*")) {
                    var splitValue = tvValue.split("*")
                    var one = splitValue[0]
                    var two = splitValue[1]
                    if (!prefix.isEmpty()) {
                        one = prefix + one
                    }
                    binding.tvInput.text = remove0((one.toDouble() * two.toDouble()).toString())
                } else if (tvValue.contains("/")) {
                    var splitValue = tvValue.split("/")
                    var one = splitValue[0]
                    var two = splitValue[1]
                    if (!prefix.isEmpty()) {
                        one = prefix + one
                    }
                    binding.tvInput.text = remove0((one.toDouble() / two.toDouble()).toString())
                } else if (tvValue.contains("+")) {
                    var splitValue = tvValue.split("+")
                    var one = splitValue[0]
                    var two = splitValue[1]
                    if (!prefix.isEmpty()) {
                        one = prefix + one
                    }
                    binding.tvInput.text = remove0((one.toDouble() + two.toDouble()).toString())
                }

            } catch (e: ArithmeticException) {
                e.printStackTrace()
            }
        }
    }

    private fun remove0(result: String): String {
        var value = result
        if (value.contains(".0")) {
            value = result.substring(0, result.length - 2).toString()
            return value
        }
        return value
    }

    fun onOperator(view: View) {
        if (lastNum && !isOperatorAdded(binding.tvInput.text.toString())) {
            binding.tvInput.append((view as Button).text)
            lastNum = false
            lastDot = false
        }
    }

    private fun isOperatorAdded(value: String): Boolean {
        return if (value.startsWith("-")) {
            false
        } else {
            value.contains("+") || value.contains("*")
                    || value.contains("/") || value.contains("-")
        }
    }
}