package com.sifat.calculator

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener
import com.sifat.calculator.databinding.ActivityMainBinding
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var input:String = ""

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

        val numberButton = listOf(
            binding.num0, binding.num1,
            binding.num2, binding.num3,
            binding.num4, binding.num5,
            binding.num6, binding.num7,
            binding.num8, binding.num9,
            binding.numDot)

        numberButton.forEach{ button ->
            button.setOnClickListener {
                input += button.text.toString()
                updatePlaceholder()
            }
        }

        val operatorButton = listOf(
            binding.actionDivide, binding.actionMultiply,
            binding.actionMinus, binding.actionAdd, binding.actionEquals
        )
        operatorButton.forEach { button->
            button.setOnClickListener {
                input += button.text.toString()
                updatePlaceholder()
            }
        }
        binding.clear.setOnClickListener{
            input = ""
            updatePlaceholder()
            binding.answer.text = ""
        }
        binding.actionBack.setOnClickListener{
            if (input.isNotEmpty()){
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

    fun evaluateExpression(expression: String):String {
        val sanitizedExpression = expression.replace("÷", "/").replace("×", "*")
        val buildExpression = ExpressionBuilder(sanitizedExpression).build()
        val result = buildExpression.evaluate()

        return if (result % 1 ==0.0){
            result.toString()
        } else{
            result.toString()
        }
    }

    private fun updatePlaceholder() {

    }


    private fun onNavigationItemSelected(menuItem: MenuItem) {
        when (menuItem.itemId) {
            R.id.home -> {
                Toast.makeText(this, "It's Damo", Toast.LENGTH_SHORT).show()
            }
        }
        binding.drawerLayout.closeDrawer(binding.navView)
    }

    override fun onOptionsItemSelected(item: MenuItem):Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                binding.drawerLayout.openDrawer(binding.navView)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}