package com.example.fixflow

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ChecklistAdapter(
    private var checklistSteps: List<ChecklistStep>
) : RecyclerView.Adapter<ChecklistAdapter.ChecklistViewHolder>() {

    // Method to update the adapter's data
    fun updateData(newSteps: List<ChecklistStep>) {
        checklistSteps = newSteps
        notifyDataSetChanged() // Notify the RecyclerView to refresh its data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChecklistViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_checklist_step, parent, false)
        return ChecklistViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChecklistViewHolder, position: Int) {
        holder.bind(checklistSteps[position])
    }

    override fun getItemCount() = checklistSteps.size

    class ChecklistViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val tvStepDescription: TextView = itemView.findViewById(R.id.tvStepDescription)

        // Bind the step data to the TextView
        fun bind(step: ChecklistStep) {
            tvStepDescription.text = step.description
        }
    }
}
