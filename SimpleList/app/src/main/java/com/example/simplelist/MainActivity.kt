package com.example.simplelist

import android.os.Bundle
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val editTextNumber = findViewById<EditText>(R.id.editTextNumber)
        val radioEven = findViewById<RadioButton>(R.id.radioEven)
        val radioOdd = findViewById<RadioButton>(R.id.radioOdd)
        val radioSquare = findViewById<RadioButton>(R.id.radioSquare)
        val buttonShow = findViewById<Button>(R.id.buttonShow)
        val listViewResult = findViewById<ListView>(R.id.listViewResult)
        val textViewError = findViewById<TextView>(R.id.textViewError)

        buttonShow.setOnClickListener {
            val inputText = editTextNumber.text.toString()
            textViewError.visibility = TextView.GONE

            val n = inputText.toIntOrNull()
            if(n==null||n<0){
                textViewError.text = "Vui long nhap so nguyen duong"
                textViewError.visibility = TextView.VISIBLE
                return@setOnClickListener
            }

            val resultList = mutableListOf<Int>()
            when {
                radioEven.isChecked -> {
                    for(i in 0..n step 2){
                        resultList.add(i)
                    }
                }

                radioOdd.isChecked -> {
                    for(i in 1..n step 2){
                        resultList.add(i)
                    }
                }

                radioSquare.isChecked -> {
                    var i = 0
                    while(i * i <= n){
                        resultList.add(i*i)
                        i++
                    }
                }

                else -> {
                    textViewError.text = "Vui long chon loai so"
                    textViewError.visibility = TextView.VISIBLE
                    return@setOnClickListener
                }
            }

            val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, resultList)
            listViewResult.adapter = adapter
        }
    }
}