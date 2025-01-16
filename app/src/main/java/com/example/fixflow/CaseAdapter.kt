package com.example.fixflow

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// Adapter class for RecyclerView
class CaseAdapter(
    private var cases: List<Case>,
    private val onCaseClick: (Case) -> Unit
) : RecyclerView.Adapter<CaseAdapter.CaseViewHolder>() {

    // Method to update the adapter's data
    fun updateData(newCases: List<Case>) {
        cases = newCases
        notifyDataSetChanged() // Notify the RecyclerView to refresh its data
    }

    inner class CaseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val caseNameTextView: TextView = view.findViewById(R.id.caseName)

        fun bind(case: Case) {
            caseNameTextView.text = case.name
            itemView.setOnClickListener { onCaseClick(case) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CaseViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_case, parent, false)
        return CaseViewHolder(view)
    }

    override fun onBindViewHolder(holder: CaseViewHolder, position: Int) {
        holder.bind(cases[position])
    }

    override fun getItemCount(): Int = cases.size
}
