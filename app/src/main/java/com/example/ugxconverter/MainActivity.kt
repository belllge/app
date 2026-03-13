
package com.example.ugxconverter

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import android.content.SharedPreferences
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class MainActivity : AppCompatActivity() {

    lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        prefs = getSharedPreferences("rates", MODE_PRIVATE)

        val ugxInput = findViewById<EditText>(R.id.ugxInput)
        val usdRate = findViewById<EditText>(R.id.usdRate)
        val etbRate = findViewById<EditText>(R.id.etbRate)
        val eurRate = findViewById<EditText>(R.id.eurRate)

        val usdResult = findViewById<TextView>(R.id.usdResult)
        val etbResult = findViewById<TextView>(R.id.etbResult)
        val eurResult = findViewById<TextView>(R.id.eurResult)

        val convertBtn = findViewById<Button>(R.id.convertBtn)
        val liveBtn = findViewById<Button>(R.id.liveBtn)

        usdRate.setText(prefs.getString("usd",""))
        etbRate.setText(prefs.getString("etb",""))
        eurRate.setText(prefs.getString("eur",""))

        convertBtn.setOnClickListener {

            val ugx = ugxInput.text.toString().toDoubleOrNull() ?: 0.0
            val usdR = usdRate.text.toString().toDoubleOrNull() ?: 0.0
            val etbR = etbRate.text.toString().toDoubleOrNull() ?: 0.0
            val eurR = eurRate.text.toString().toDoubleOrNull() ?: 0.0

            val usd = ugx * usdR
            val etb = ugx * etbR
            val eur = ugx * eurR

            usdResult.text = "USD: $usd"
            etbResult.text = "ETB: $etb"
            eurResult.text = "EUR: $eur"

            val editor = prefs.edit()
            editor.putString("usd", usdRate.text.toString())
            editor.putString("etb", etbRate.text.toString())
            editor.putString("eur", eurRate.text.toString())
            editor.apply()
        }

        liveBtn.setOnClickListener {
            fetchLiveRates()
        }
    }

    private fun fetchLiveRates(){

        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://api.exchangerate.host/latest?base=UGX&symbols=USD,ETB,EUR")
            .build()

        client.newCall(request).enqueue(object: Callback{

            override fun onFailure(call: Call, e: IOException) {}

            override fun onResponse(call: Call, response: Response) {

                val body = response.body?.string()
                val json = JSONObject(body)

                val rates = json.getJSONObject("rates")

                val usd = rates.getDouble("USD")
                val etb = rates.getDouble("ETB")
                val eur = rates.getDouble("EUR")

                runOnUiThread{

                    findViewById<EditText>(R.id.usdRate).setText(usd.toString())
                    findViewById<EditText>(R.id.etbRate).setText(etb.toString())
                    findViewById<EditText>(R.id.eurRate).setText(eur.toString())

                    Toast.makeText(applicationContext,"Live rates updated",Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}
