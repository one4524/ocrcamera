package com.example.ocrcamera

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.DatePicker
import com.example.ocrcamera.databinding.ActivityModifyBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ModifyActivity : AppCompatActivity() {
    private lateinit var binding : ActivityModifyBinding
    private val db: FirebaseFirestore = Firebase.firestore
    private val itemsCollectionRef = db.collection("items") // items는 Collection ID


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityModifyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val docId = intent.getStringExtra("docId")
        val place = intent.getStringExtra("place")
        val money = intent.getStringExtra("money")
        val year = intent.getStringExtra("year")?.toInt()
        val month = intent.getStringExtra("month")?.toInt()
        val day = intent.getStringExtra("day")?.toInt()

        binding.name.setText(place)
        binding.money.setText(money)
        binding.dataPicker.init(year!!,(month!!-1), day!!, DatePicker.OnDateChangedListener { datePicker, i, i2, i3 ->  })



        binding.save.setOnClickListener {
            if (docId != null) {
                itemsCollectionRef.document(docId).update("place",binding.name.text.toString())
                    .addOnSuccessListener { }
                itemsCollectionRef.document(docId).update("money",binding.money.text.toString())
                    .addOnSuccessListener { }
                itemsCollectionRef.document(docId).update("year",binding.dataPicker.year.toString())
                    .addOnSuccessListener { }
                itemsCollectionRef.document(docId).update("month",(binding.dataPicker.month+1).toString())
                    .addOnSuccessListener { }
                itemsCollectionRef.document(docId).update("day",binding.dataPicker.dayOfMonth.toString())
                    .addOnSuccessListener { }
            }
            finish()

        }
    }
}