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
                val result = evaluateExpression(input)
                binding.answer.text = result.toString()
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
