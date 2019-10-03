package com.jacobrozell.kotlincalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.NumberFormatException

private const val STATE_PENDING_OP = "PendingOperation"
private const val STATE_OPERAND1 = "Operand1"
private const val STATE_STORED_OPERAND1 = "Operand1_Stored"

class MainActivity : AppCompatActivity() {

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
                operation.text = pendingOperation
            }
            pendingOperation = op
            operation.text = pendingOperation
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
        buttonneg.setOnClickListener(operationListener)
        buttonmultiply.setOnClickListener(operationListener)

        buttonneg.setOnClickListener {
            val value = newNumber.text.toString()
            if (value.isEmpty()) {
                newNumber.setText("-")
            } else {
                try {
                    var doubleValue = value.toDouble()
                    doubleValue *= -1
                    newNumber.setText(doubleValue.toString())
                } catch (e: NumberFormatException) {
                    newNumber.setText("")
                }
            }
        }
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
            outState.putBoolean(STATE_STORED_OPERAND1, true)
        }
        outState.putString(STATE_PENDING_OP, pendingOperation)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        if (savedInstanceState.getBoolean(STATE_STORED_OPERAND1, false)) {
            operand1 = savedInstanceState.getDouble(STATE_OPERAND1)
        } else {
            operand1 = null
        }

        pendingOperation = savedInstanceState.getString(STATE_PENDING_OP, "")
        operation.text = pendingOperation
    }
}
