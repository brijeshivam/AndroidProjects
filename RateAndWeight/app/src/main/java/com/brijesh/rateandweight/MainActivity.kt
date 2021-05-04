package com.brijesh.rateandweight

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import java.lang.Double.parseDouble
import java.lang.Integer.parseInt
import java.lang.Math.ceil

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val w = findViewById<EditText>(R.id.weight)
        w.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) findViewById<EditText>(R.id.rupees).setText("")
        }
        val r = findViewById<EditText>(R.id.rupees)
        r.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) findViewById<EditText>(R.id.weight).setText("")
        }
        val rat = findViewById<EditText>(R.id.rate)
        rat.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                findViewById<EditText>(R.id.weight).setText("")
                findViewById<EditText>(R.id.rupees).setText("")}
        }


    }
    @SuppressLint("SetTextI18n")

        fun calculate(v: View) {
        try {
            if (findViewById<EditText>(R.id.weight).getText().toString() == "") {
                val result = ceil(rupeeGiven())
                findViewById<TextView>(R.id.resulttv).setText(
                    "Result :" + result.toString().dropLast(2) + "g"
                )
            } else {
                val result = ceil(weightGiven())
                findViewById<TextView>(R.id.resulttv).setText(
                    "Result :" + result.toString().dropLast(2) + "Rs"
                )
            }
        }catch (e: Exception){}

        }

    fun weightGiven(): Double {
        val weight = parseDouble(findViewById<EditText>(R.id.weight).getText().toString())
        val rate =  parseDouble(findViewById<EditText>(R.id.rate).getText().toString())
        return ((rate/1000)*weight)
    }
    fun rupeeGiven():Double{
        val rupees = parseDouble(findViewById<EditText>(R.id.rupees).getText().toString())
        val rate =  parseDouble(findViewById<EditText>(R.id.rate).getText().toString())
        return (1000/rate)*rupees
    }


}