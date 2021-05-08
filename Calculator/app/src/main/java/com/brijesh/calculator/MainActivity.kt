package com.brijesh.calculator

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import net.objecthunter.exp4j.*
import org.w3c.dom.Text
import java.lang.Integer.parseInt
import java.math.BigDecimal


class MainActivity : AppCompatActivity() {
    var input=""
    var inputCopy ="a"
    var dotUsed: Boolean = false
    var result: Double = 0.0
    var resultString = "0"
    var r : BigDecimal? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val te:TextView = findViewById(R.id.tv2)
        val tr:TextView = findViewById(R.id.tv3)
        te.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if(input == "-"||input ==""||isLastOp()||input ==tr.text){
                    return
                }
                resultOfExpression(te)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })




    }
     fun write(v: View){
        when (v.id){
            R.id.btn1 -> {
                if (input.length > 46) {
                    return
                }
                input += "1"
                findViewById<TextView>(R.id.tv2).text = input
            }

            R.id.btn2 -> {
                if (input.length > 46) {
                    return
                }
                input += "2"
                findViewById<TextView>(R.id.tv2).text = input
            }

            R.id.btn3 -> {
                if (input.length > 46) {
                    return
                }
                input += "3"
                findViewById<TextView>(R.id.tv2).text = input
            }

            R.id.btn4 -> {
                if (input.length > 46) {
                    return
                }
                input += "4"
                findViewById<TextView>(R.id.tv2).text = input
            }

            R.id.btn5 -> {
                if (input.length > 46) {
                    return
                }
                input += "5"
                findViewById<TextView>(R.id.tv2).text = input
            }

            R.id.btn6 -> {
                if (input.length > 46) {
                    return
                }
                input += "6"
                findViewById<TextView>(R.id.tv2).text = input
            }

            R.id.btn7 -> {
                if (input.length > 46) {
                    return
                }
                input += "7"
                findViewById<TextView>(R.id.tv2).text = input
            }

            R.id.btn8 -> {
                if (input.length > 46) {
                    return
                }
                input += "8"
                findViewById<TextView>(R.id.tv2).text = input
            }

            R.id.btn9 -> {
                if (input.length > 46) {
                    return
                }
                input += "9"
                findViewById<TextView>(R.id.tv2).text = input
            }

            R.id.btn0 -> {
                if (input.length > 46 || input == "0" || input == "00") {
                    return
                }
                input += "0"
                findViewById<TextView>(R.id.tv2).text = input
            }

            R.id.btn00 -> {
                if (input.length > 46 || input == "0" || input == "00") {
                    return
                }
                input += "00"
                findViewById<TextView>(R.id.tv2).text = input
            }

            R.id.btnc -> {
                input = ""
                inputCopy="a"
                resultString = "0"
                findViewById<TextView>(R.id.tv2).text = ""
                findViewById<TextView>(R.id.tv3).text = "0"
                dotUsed = false
            }

            R.id.btndot -> {
                if (input.length > 46 || dotUsed || isLastOp()) {
                    return
                }
                if (input == "") {
                    input = "0"
                }
                input += "."
                findViewById<TextView>(R.id.tv2).text = input
                dotUsed = true
            }

            R.id.btndivide -> {
                if (input.length > 46 || input == "") {
                    return
                }
                if(input.equals("-"))
                {
                    input = input.dropLast(1)
                    findViewById<TextView>(R.id.tv2).text = input
                    return
                }
                if (isLastOp()) {
                    if(input.endsWith("-")){
                        input = input.dropLast(1)
                        findViewById<TextView>(R.id.tv2).text = input
                        return}
                    input = input.dropLast(1)

                }
                input += "/"
                findViewById<TextView>(R.id.tv2).text = input
                dotUsed = false
            }

            R.id.btnmultiply -> {
                if (input.length > 46 || input == "") {
                    return
                }
                if(input.equals("-"))
                {
                    input = input.dropLast(1)
                    findViewById<TextView>(R.id.tv2).text = input
                    return
                }
                if (isLastOp()) {
                    if(input.endsWith("-")){
                        input = input.dropLast(1)
                        findViewById<TextView>(R.id.tv2).text = input
                        return}
                    input = input.dropLast(1)
                }
                input += "*"
                findViewById<TextView>(R.id.tv2).text = input
                dotUsed = false
            }

            R.id.btnplus -> {
                if (input.length > 46 || input == "") {
                    return
                }
                if(input =="-"){
                    input = input.dropLast(1)
                    findViewById<TextView>(R.id.tv2).text = input
                    return}
                if (input.length!=1 &&input[input.lastIndex] =='-'&&(input[input.lastIndex-1] =='*'|| input[input.lastIndex-1] =='/')) {
                    input = input.dropLast(1)
                    findViewById<TextView>(R.id.tv2).text = input
                    return
                }
                if (isLastOp()) {
                    input = input.dropLast(1)
                }
                input += "+"
                findViewById<TextView>(R.id.tv2).text = input
                dotUsed = false
            }

            R.id.btnminus -> {
                if (input.length > 46 ||input.endsWith("-")) {
                    return
                }
                if (input.endsWith("+")) {
                    input = input.dropLast(1)
                }
                input += "-"
                findViewById<TextView>(R.id.tv2).text = input
                dotUsed = false
            }

            R.id.btnpercent -> {
                if (input.length > 46 || input == "") {
                    return
                }
                if(input == "-")
                {
                    input = input.dropLast(1)
                    findViewById<TextView>(R.id.tv2).text = input
                    return
                }
                if (isLastOp()) {
                    if(input.endsWith("-")){
                        input = input.dropLast(1)
                        findViewById<TextView>(R.id.tv2).text = input
                        return}
                    input = input.dropLast(1)
                }
                input += "*100"
                findViewById<TextView>(R.id.tv2).text = input
                dotUsed = false
            }

            R.id.btnx -> {
                if (input.endsWith(".")) {
                    dotUsed = false
                }
                if (input.endsWith("+") || input.endsWith("*") || input.endsWith("-") || input.endsWith("/")) {
                    for (i in input.lastIndex - 1 downTo 0) {
                        if (input[i] == '+' || input[i] == '-' || input[i] == '*' || input[i] == '/') {
                            dotUsed = false
                        }
                        if (input[i] == '.') {
                            dotUsed = true
                        }

                    }
                }
                input = input.dropLast(1)
                findViewById<TextView>(R.id.tv2).text = input
            }


        }


    }
    private fun isLastOp():Boolean{
        return input.endsWith(".")||input.endsWith("+")||input.endsWith("*")||input.endsWith("-")||input.endsWith("/")
    }

    fun resultOfExpression(v: View) {
        if(input == inputCopy){
            inputCopy =  resultString
            findViewById<TextView>(R.id.tv2).text = inputCopy
            input = resultString
            return
        }


        if(isLastOp()){
           input = input.dropLast(1)
        }
        try {
             result = ExpressionBuilder(input)
                    .build()
                     .evaluate()

            r = BigDecimal(result.toString())

            resultString  = if(r!!.toPlainString() .endsWith(".0")){
                r!!.toPlainString().dropLast(2)
            }else if(r!!.toPlainString().length>16 && r!!.toPlainString().contains(".")){
                r!!.toPlainString().subSequence(0,16) as String
            }
            else{
                r!!.toPlainString()
            }
            findViewById<TextView>(R.id.tv3).text = resultString
            inputCopy = input

        } catch ( e: Exception ) {
            Toast.makeText(this, "Enter Correct Expression", Toast.LENGTH_SHORT)
                    .show()
        }
    }

    }
