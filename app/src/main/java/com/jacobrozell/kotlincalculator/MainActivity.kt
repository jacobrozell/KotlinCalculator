package com.jacobrozell.kotlincalculator

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.NumberFormatException

private const val STATE_PENDING_OP = "PendingOperation"
private const val STATE_OPERAND1 = "Operand1"

class MainActivity : AppCompatActivity() {

    private val displayOperation by lazy(LazyThreadSafetyMode.NONE) { findViewById<TextView>(R.id.operation) }
    private var operand1: Double? = null
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

            try {
                val value = newNumber.text.toString().toDouble()
                performOperation(value, op)
            } catch (e: NumberFormatException) {
                pendingOperation = op
                displayOperation.text = pendingOperation
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

        // Add OperationListener
        buttonadd.setOnClickListener(operationListener)
        buttonequals.setOnClickListener(operationListener)
        buttonminus.setOnClickListener(operationListener)
        buttondivide.setOnClickListener(operationListener)
        buttonmultiply.setOnClickListener(operationListener)
    }

    private fun setOperandTextTo(text: String) {
        result.setText(text)
        newNumber.setText("")
    }

    private fun performOperation(value: Double, operation: String) {
        if (operand1 == null) {
            operand1 = value
            setOperandTextTo(operand1.toString())
            return
        }

        if (pendingOperation == "=") {
            pendingOperation = operation
        }

        when (pendingOperation) {
            "=" -> operand1 = value
            "/" -> if (value == 0.0) {
                Toast.makeText(applicationContext, "Cannot divide by 0", Toast.LENGTH_LONG).show()
            } else {
                operand1 = operand1!! / value
            }
            "*" -> operand1 = operand1!! * value
            "-" -> operand1 = operand1!! - value
            "+" -> operand1 = operand1!! + value
        }

        setOperandTextTo(operand1.toString())
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (operand1 != null) {
            outState.putDouble(STATE_OPERAND1, operand1!!)
        }
        outState.putString(STATE_PENDING_OP, pendingOperation)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

    }
}
