package com.example.fixflow

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AdminCaseAdapter(
    private val cases: List<Case>,
    private val onItemClick: (Case) -> Unit
) : RecyclerView.Adapter<AdminCaseAdapter.CaseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CaseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_case, parent, false)
        return CaseViewHolder(view)
    }

    override fun onBindViewHolder(holder: CaseViewHolder, position: Int) {
        holder.bind(cases[position], onItemClick)
    }

    override fun getItemCount() = cases.size

    class CaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        // Access the TextView with findViewById
        private val caseName: TextView = itemView.findViewById(R.id.caseName)

        // Binding the case data to the TextView
        fun bind(case: Case, onItemClick: (Case) -> Unit) {
            caseName.text = case.name   // Use the caseName reference to set text
            itemView.setOnClickListener { onItemClick(case) }
        }
    }
}
