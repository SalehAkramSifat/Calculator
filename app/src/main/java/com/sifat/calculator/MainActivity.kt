package com.sifat.calculator

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.sifat.calculator.databinding.ActivityMainBinding
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var input: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.menu)

        binding.navView.setNavigationItemSelectedListener { menuItem ->
            onNavigationItemSelected(menuItem)
            true
        }

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
                val result = evaluateExpression(input) // Call evaluateExpression here
                binding.answer.text = result
            } catch (e: Exception) {
                binding.answer.text = "Error"
            }
        }

    }

    private fun onNavigationItemSelected(item: MenuItem) {
        when(item.itemId){
            R.id.home -> {
                Toast.makeText(this, "It's Damo", Toast.LENGTH_SHORT).show()
            }

        }
        binding.drawerLayout.closeDrawer(binding.navView)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                binding.drawerLayout.openDrawer(binding.navView)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun evaluateExpression(expression: String): String {
        // Replace ÷ with / and × with * for calculation
        val sanitizedExpression = expression.replace("÷", "/").replace("×", "*")
        val builtExpression = ExpressionBuilder(sanitizedExpression).build()
        val result = builtExpression.evaluate()

        // Format the result for output
        return if (result % 1 == 0.0) {
            result.toInt().toString() // Return as integer if no decimal part exists
        } else {
            result.toString() // Return full number with decimals
        }
    }

    private fun updatePlaceholder() {
        binding.placeholder.text = input
    }
}
