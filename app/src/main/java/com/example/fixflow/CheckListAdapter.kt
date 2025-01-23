package com.example.fixflow

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ChecklistAdapter(
    private var checklistSteps: List<String> // List of checklist steps as Strings
) : RecyclerView.Adapter<ChecklistAdapter.ChecklistViewHolder>() {

    private var onItemClickListener: ((Int, String) -> Unit)? = null // Listener for item clicks

    /**
     * Update the adapter's data and refresh the RecyclerView
     */
    fun updateData(newSteps: List<String>) {
        checklistSteps = newSteps
        notifyDataSetChanged()  // Notify the RecyclerView that data has changed
    }

    /**
     * Set the item click listener
     */
    fun setOnItemClickListener(listener: (Int, String) -> Unit) {
        onItemClickListener = listener
    }

    /**
     * Inflate the item layout and create a ViewHolder instance
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChecklistViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_checklist_step, parent, false)
        return ChecklistViewHolder(view)
    }

    /**
     * Bind the data to the ViewHolder for the specified position
     */
    override fun onBindViewHolder(holder: ChecklistViewHolder, position: Int) {
        holder.bind(checklistSteps[position])
    }

    /**
     * Return the size of the checklist steps
     */
    override fun getItemCount(): Int = checklistSteps.size

    /**
     * ViewHolder class to hold and bind individual checklist step views
     */
    inner class ChecklistViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val tvStepDescription: TextView = itemView.findViewById(R.id.tvStepDescription)

        /**
         * Bind the checklist step data to the view
         */
        fun bind(step: String) {
            tvStepDescription.text = step
            itemView.setOnClickListener {
                onItemClickListener?.invoke(adapterPosition, step) // Call the listener when clicked
            }
        }
    }
}
