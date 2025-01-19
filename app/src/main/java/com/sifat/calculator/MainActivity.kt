package com.sifat.calculator

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sifat.calculator.databinding.ActivityMainBinding
import net.objecthunter.exp4j.ExpressionBuilder
import org.mozilla.javascript.Scriptable

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var input: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val numberButtons = listOf(
            binding.num0, binding.num1, binding.num2,
            binding.num3, binding.num4, binding.num5,
            binding.num6, binding.num7, binding.num8,
            binding.num9, binding.numDot
        )
        numberButtons.forEach { button ->
            button.setOnClickListener {
                input += button.text.toString()
                updatePlaceholder()
            }
        }
        val operatorButtons = listOf(
            binding.actionAdd, binding.actionMinus,
            binding.actionMultiply, binding.actionDivide
        )
        operatorButtons.forEach { button ->
            button.setOnClickListener {
                input += button.text.toString()
                updatePlaceholder()
            }
        }
        binding.clear.setOnClickListener {
            input = ""
            updatePlaceholder()
            binding.answer.text = ""
        }
        binding.actionBack.setOnClickListener {
            if (input.isNotEmpty()) {
                input = input.dropLast(1)
                updatePlaceholder()
            }
        }
        binding.actionEquals.setOnClickListener {
            try {
                val result = evaluateExpression(input)
                binding.answer.text = result.toString()
            } catch (e: Exception) {
                binding.answer.text = "Error"
            }
        }
    }

    fun evaluateExpression(expression: String): String {
        val expression = ExpressionBuilder(expression).build()
        val result = expression.evaluate()

        return if (result % 1 == 0.0) {
            result.toInt().toString()
        } else {
            result.toString()
        }
    }

    private fun updatePlaceholder() {
        binding.placeholder.text = input
    }
}
