package com.example.ocrcamera

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.example.ocrcamera.databinding.ActivityMainBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    private val db: FirebaseFirestore = Firebase.firestore
    private val itemsCollectionRef = db.collection("items") // items Collection ID

    private var year : Int = 2022
    private var month : Int = 1
    private var day : Int = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val today = Calendar.getInstance()
        year = today.get(Calendar.YEAR)
        month = today.get(Calendar.MONTH) + 1
        day = today.get(Calendar.DATE)

        Log.d("year-month-day", "${year} - ${month} - ${day}")

        binding.addReceipt.setOnClickListener {
            val intent = Intent(this, CameraActivity::class.java)
            startActivity(intent)
        }

        binding.calendar.setOnDateChangeListener { _, i, i2, i3 ->
            year = i
            month = i2 + 1
            day = i3

            oneDayQuery()

            Log.d("year", month.toString())

        }

        monthQuery()
        todayQuery()
        oneDayQuery()

    }

    override fun onResume() {
        super.onResume()

        monthQuery()
        todayQuery()
        oneDayQuery()
    }

    private fun oneDayQuery() {
        itemsCollectionRef.whereEqualTo("year", year.toString())
            .whereEqualTo("month", month.toString())
            .whereEqualTo("day", day.toString())
            .get()
            .addOnSuccessListener {
                val items = ArrayList<DayData>()
                for (doc in it) {
                        items.add(DayData(doc["id"] as String,
                            doc["place"] as String,
                            doc["money"] as String, doc["year"] as String,
                            doc["month"] as String, doc["day"] as String,
                            doc.id
                        ))
                }

                val adapter = itemAdapter(items)
                binding.recyclerView.adapter = adapter

                for(i in items){
                    Log.d("items", i.toString())
                }
            }
            .addOnFailureListener {
                Log.d("false", year.toString())
            }
    }

    private fun todayQuery() {
        var todayMoney = 0
        itemsCollectionRef.whereEqualTo("year", year.toString())
            .whereEqualTo("month", month.toString())
            .whereEqualTo("day", day.toString())
            .get()
            .addOnSuccessListener {
                for (doc in it) {
                    todayMoney += (doc["money"].toString()).toInt()
                }

                runOnUiThread {
                    binding.todayMoney.text = todayMoney.toString()
                }

            }
            .addOnFailureListener {
                Log.d("false", day.toString())
            }
    }

    private fun monthQuery() {
        var monthMoney = 0
        itemsCollectionRef.whereEqualTo("year", year.toString())
            .whereEqualTo("month", month.toString())
            .get()
            .addOnSuccessListener {
                for (doc in it) {
                    monthMoney += (doc["money"].toString()).toInt()
                }

                runOnUiThread {
                    binding.monthMoney.text = monthMoney.toString()
                }

            }
            .addOnFailureListener {
                Log.d("false", day.toString())
            }
    }
}