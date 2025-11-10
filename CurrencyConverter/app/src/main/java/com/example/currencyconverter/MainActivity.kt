package com.example.currencyconverter

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import retrofit2.Call
import retrofit2.Callback
import retrofit2.GsonConverterFactory
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class MainActivity : AppCompatActivity() {
    private lateinit var amountInput: EditText
    private lateinit var currencyFrom: Spinner
    private lateinit var currencyTo: Spinner
    private lateinit var result: TextView
    private lateinit var rates: Map<String, Double>


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        amountInput = findViewById(R.id.amountInput)
        currencyFrom = findViewById(R.id.currencyFrom)
        currencyTo = findViewById(R.id.currencyTo)
        result = findViewById(R.id.result)
        val convertButton: Button = findViewById(R.id.convertButton)

        loadCurrencies()

        convertButton.setOnClickListener {convertCurrency()}
    }

    private fun loadCurrencies(){
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.exchangerate-api.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(CurrencyApi::class.java)
        val call = api.getExchangeRates()

        call.enqueue(object : Callback<CurrencyResponse> {
            override fun onResponse(
                call: Call<CurrencyResponse>,
                response: retrofit2.Response<CurrencyResponse>
            ) {
                if(response.isSuccessful && response.body() != null){
                    rates = response.body()!!.rates
                    val currencyList = rates.keys.toList()
                    val adapter = ArrayAdapter(this@MainActivity, android.R.layout.simple_spinner_item, currencyList)
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    currencyFrom.adapter = adapter
                    currencyTo.adapter = adapter
                }
            }

            override fun onFailure(call: Call<CurrencyResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun convertCurrency(){
        val amount = amountInput.text.toString().toDoubleOrNull() ?: return
        val from = currencyFrom.selectedItem.toString()
        val to = currencyTo.selectedItem.toString()

        val fromRate = rates[from] ?: return
        val toRate = rates[to] ?: return
        val convertedAmount = (amount / fromRate) * toRate

        result.text = String.format("%.2f %s = %.2f %s", amount, from, convertedAmount, to)
    }
}