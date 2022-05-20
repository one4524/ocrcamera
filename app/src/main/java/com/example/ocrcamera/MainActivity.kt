package com.example.ocrcamera

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.example.ocrcamera.databinding.ActivityMainBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

data class Item(val id: String, val name: String, val price: Int) {
    constructor(doc: QueryDocumentSnapshot) :
            this(doc.id, doc["name"].toString(), doc["price"].toString().toIntOrNull() ?: 0)
    constructor(key: String, map: Map<*, *>) :
            this(key, map["name"].toString(), map["price"].toString().toIntOrNull() ?: 0)
}

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    private val db: FirebaseFirestore = Firebase.firestore
    val itemsCollectionRef = db.collection("items") // items는 Collection ID

    private var year : Int = 2022
    private var month : Int = 1
    private var day : Int = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addReceipt.setOnClickListener {
            val intent = Intent(this, CameraActivity::class.java)
            startActivity(intent)
        }


        binding.calendar.setOnDateChangeListener { calendarView, i, i2, i3 ->
            year = i
            month = i2
            day = i3

        }

    }

    private fun queryWhere() {
        itemsCollectionRef.whereEqualTo("year", year)
            .whereEqualTo("month", month)
            .whereEqualTo("day", day).get()
            .addOnSuccessListener {
                val items = arrayListOf<String>()
                for (doc in it) {
                    items.add("${doc["name"]} - ${doc["price"]}")
                }
            }
            .addOnFailureListener {
            }
    }
}