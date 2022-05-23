package com.example.ocrcamera

import android.content.ClipData
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.DatePicker
import com.example.ocrcamera.databinding.ActivityGoodsDetailBinding
import com.example.ocrcamera.databinding.ActivityMainBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*


class GoodsDetailActivity : AppCompatActivity() {
    private lateinit var detailBinding: ActivityGoodsDetailBinding

    private val db: FirebaseFirestore = Firebase.firestore
    private val itemsCollectionRef = db.collection("items") // items는 Collection ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailBinding = ActivityGoodsDetailBinding.inflate(layoutInflater)
        setContentView(detailBinding.root)

        detailBinding.detailOut.setOnClickListener { finish() }

        val arrayList = intent.getStringArrayListExtra("data")

        val today = Calendar.getInstance()
        val year = today.get(Calendar.YEAR)
        val month = today.get(Calendar.MONTH) + 1
        val day = today.get(Calendar.DATE)

        val y = arrayList!![3]

        if (y == "0000"){
            detailBinding.dataPicker.init(year,month, day, DatePicker.OnDateChangedListener { datePicker, i, i2, i3 ->  })
        }
        else{
            detailBinding.dataPicker.init(arrayList[3].toInt(),arrayList[4].toInt() -1, arrayList[5].toInt(), DatePicker.OnDateChangedListener { datePicker, i, i2, i3 ->  })
        }




        detailBinding.name.setText(arrayList[1])
        detailBinding.money.setText(arrayList[2])

        detailBinding.save.setOnClickListener {

            val itemMap = hashMapOf<String, String>(
                "id" to arrayList[0],
                "place" to detailBinding.name.text.toString(),
                "money" to detailBinding.money.text.toString(),
                "year" to detailBinding.dataPicker.year.toString(),
                "month" to (detailBinding.dataPicker.month+1).toString(),
                "day" to detailBinding.dataPicker.dayOfMonth.toString()
            )


            val autoID = true
            val itemID = "a"

            if (autoID) { // Document의 id 자동 생성
                itemsCollectionRef.add(itemMap)
                    .addOnSuccessListener {
                        finish()
                    }.addOnFailureListener { }
            } else { // Document의 ID를 itemID의 값으로 지정
                itemsCollectionRef.document(itemID).set(itemMap)
                    .addOnSuccessListener {
                        finish()
                    }.addOnFailureListener { }
                // itemID에 해당되는 Document가 존재하면 내용을 업데이트
            }

        }

    }

}
