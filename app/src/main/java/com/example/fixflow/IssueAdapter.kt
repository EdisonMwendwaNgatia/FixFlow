package com.example.fixflow

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fixflow.R

class IssueAdapter(
    private var issues: List<Issue>,
    private val onItemClick: (Issue) -> Unit
) : RecyclerView.Adapter<IssueAdapter.IssueViewHolder>() {

    // Method to update the adapter's data
    fun updateData(newIssues: List<Issue>) {
        issues = newIssues
        notifyDataSetChanged() // Notify the RecyclerView to refresh its data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IssueViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_issue, parent, false)
        return IssueViewHolder(view)
    }

    override fun onBindViewHolder(holder: IssueViewHolder, position: Int) {
        holder.bind(issues[position], onItemClick)
    }

    override fun getItemCount() = issues.size

    class IssueViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvIssueTitle: TextView = itemView.findViewById(R.id.tvIssueTitle)

        fun bind(issue: Issue, onItemClick: (Issue) -> Unit) {
            tvIssueTitle.text = issue.title
            itemView.setOnClickListener { onItemClick(issue) }
        }
    }
}
