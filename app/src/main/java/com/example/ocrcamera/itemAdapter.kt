package com.example.ocrcamera

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class itemAdapter(private val items: ArrayList<DayData>) : RecyclerView.Adapter<itemAdapter.ViewHolder>() {

    lateinit var c : Context

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = items[position]
        val listener = View.OnClickListener {
        }

        holder.apply {
            bind(c, listener, item)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        c = parent.context
        val inflatedView = LayoutInflater.from(parent.context).inflate(R.layout.recycleview_item, parent, false)
        return ViewHolder(inflatedView)
    }

    // 각 항목에 필요한 기능을 구현
    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        private val place : TextView = v.findViewById(R.id.place)
        private val money : TextView = v.findViewById(R.id.money)
        private val year : TextView = v.findViewById(R.id.year)
        private val month : TextView = v.findViewById(R.id.month)
        private val day : TextView = v.findViewById(R.id.day)
        private val modify : Button = v.findViewById(R.id.modify)
        private val delete : Button = v.findViewById(R.id.delete)

        private val view : View = v

        fun bind(c : Context, listener: View.OnClickListener, item: DayData) {

            val db: FirebaseFirestore = Firebase.firestore
            val itemsCollectionRef = db.collection("items") // items는 Collection ID

            place.text = item.place
            money.text = item.money
            year.text = item.year
            month.text = item.month
            day.text = item.day
            view.setOnClickListener(listener)
            modify.setOnClickListener {
                val intent = Intent(c, ModifyActivity::class.java)
                intent.putExtra("docId", item.docid)
                intent.putExtra("place", item.place)
                intent.putExtra("money", item.money)
                intent.putExtra("year", item.year)
                intent.putExtra("month", item.month)
                intent.putExtra("day", item.day)
                c.startActivity(intent)
            }
            delete.setOnClickListener {
                itemsCollectionRef.document(item.docid).delete()
                    .addOnSuccessListener { }
            }
        }
    }

    override fun getItemCount(): Int = items.size
}

