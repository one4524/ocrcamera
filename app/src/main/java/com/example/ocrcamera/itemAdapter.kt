package com.example.ocrcamera

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class itemAdapter(private val items: ArrayList<DayData>) : RecyclerView.Adapter<itemAdapter.ViewHolder>() {


    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = items[position]
        val listener = View.OnClickListener {

        }
        holder.apply {
            bind(listener, item)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
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

        private val view : View = v

        fun bind(listener: View.OnClickListener, item: DayData) {
            place.text = item.place
            money.text = item.money
            year.text = item.year
            month.text = item.month
            day.text = item.day
            view.setOnClickListener(listener)
        }
    }
}

