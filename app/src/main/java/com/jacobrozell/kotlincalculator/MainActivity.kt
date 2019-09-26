package com.jacobrozell.kotlincalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var result: EditText
    private val displayOperation by lazy(LazyThreadSafetyMode.NONE) { findViewById<TextView>(R.id.operation) }

    // Var to hold operands and type of calculation
    private var operand1: Double? = null
    private var operand2: Double = 0.0
    private var pendingOperation = "="


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listener = View.OnClickListener { view ->
            val button = view as Button
            newNumber.append(button.text)
        }

        val operationListener = View.OnClickListener { view ->
            val op = (view as Button).text.toString()
            val value = newNumber.text.toString()

            if (value.isNotEmpty()) {
               performOperation(value, op)
            }
            pendingOperation = op
            displayOperation.text = pendingOperation
        }

        // Add OnClickListeners
        button0.setOnClickListener(listener)
        button1.setOnClickListener(listener)
        button2.setOnClickListener(listener)
        button3.setOnClickListener(listener)
        button4.setOnClickListener(listener)
        button5.setOnClickListener(listener)
        button6.setOnClickListener(listener)
        button7.setOnClickListener(listener)
        button8.setOnClickListener(listener)
        button9.setOnClickListener(listener)
        buttondot.setOnClickListener(listener)

        buttonadd.setOnClickListener(operationListener)
        buttonequals.setOnClickListener(operationListener)
        buttonminus.setOnClickListener(operationListener)
        buttondivide.setOnClickListener(operationListener)
        buttonmultiply.setOnClickListener(operationListener)


    }

    private fun performOperation(value: String, operation: String) {
        displayOperation.text = operation
    }
}
