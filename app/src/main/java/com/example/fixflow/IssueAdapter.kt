package com.example.fixflow

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fixflow.R

class IssueAdapter(
    private var issues: List<Issue>, // List of issues to display
    private val onItemClick: (Issue) -> Unit // Lambda for item click handling
) : RecyclerView.Adapter<IssueAdapter.IssueViewHolder>() {

    // Method to update the adapter's data
    fun updateData(newIssues: List<Issue>) {
        issues = newIssues
        notifyDataSetChanged() // Notify RecyclerView that the data has changed
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IssueViewHolder {
        // Inflate the layout for each item in the list
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_issue, parent, false)
        return IssueViewHolder(view)
    }

    override fun onBindViewHolder(holder: IssueViewHolder, position: Int) {
        // Bind the issue data to the view holder
        holder.bind(issues[position], onItemClick)
    }

    override fun getItemCount(): Int {
        return issues.size
    }

    // ViewHolder to hold the views of each issue item
    class IssueViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Views in the item layout
        private val tvIssueTitle: TextView = itemView.findViewById(R.id.tvIssueTitle)

        // Bind data to views
        fun bind(issue: Issue, onItemClick: (Issue) -> Unit) {
            tvIssueTitle.text = issue.name // Set the issue name
            itemView.setOnClickListener {
                onItemClick(issue) // Trigger the click event when an issue item is clicked
            }
        }
    }
}
