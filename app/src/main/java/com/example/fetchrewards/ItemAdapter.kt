package com.example.fetchrewards

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ItemAdapter(private val items: List<Item>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val sortedItems: List<Item>

    init {
        // Sort by listId first, and then by the numeric part of the name
        sortedItems = items.sortedWith(compareBy({ it.listId }, { extractNumber(it.name) }))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = sortedItems[position]
        val viewHolder = holder as ViewHolder
        // if the current item is the first item in the list
        // or if the previous item has a different list ID.
        // display the list ID as a header
        if (position == 0 || sortedItems[position - 1].listId != item.listId) {
            viewHolder.listId.text = "List ${item.listId}"
            viewHolder.listId.visibility = View.VISIBLE
        } else {
            //Otherwise, hide the list ID text view.
            viewHolder.listId.visibility = View.GONE
        }

        viewHolder.itemName.text = item.name
    }

    override fun getItemCount(): Int {
        return sortedItems.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val listId: TextView = itemView.findViewById(R.id.list_id)
        val itemName: TextView = itemView.findViewById(R.id.item_name)
    }

    private fun extractNumber(name: String?): Int {
        // Extracts the number from the "Item" string
        return name?.substringAfter("Item")?.trim()?.toIntOrNull() ?: 0
    }
}