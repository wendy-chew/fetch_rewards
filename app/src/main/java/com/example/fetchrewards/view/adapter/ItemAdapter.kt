package com.example.fetchrewards.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fetchrewards.R
import com.example.fetchrewards.controller.ItemController
import com.example.fetchrewards.model.Item

class ItemAdapter(private val itemController: ItemController) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: List<Item> = emptyList()

    init {
        itemController.fetchItems { fetchedItems ->
            items = fetchedItems
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        val viewHolder = holder as ViewHolder

        // if the current item is the first item in the list
        // or if the previous item has a different list ID.
        // display the list ID as a header
        if (position == 0 || items[position - 1].listId != item.listId) {
            viewHolder.listId.text = "List ${item.listId}"
            viewHolder.listId.visibility = View.VISIBLE
        } else {
            //Otherwise, hide the list ID text view.
            viewHolder.listId.visibility = View.GONE
        }

        viewHolder.itemName.text = item.name
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val listId: TextView = itemView.findViewById(R.id.list_id)
        val itemName: TextView = itemView.findViewById(R.id.item_name)
    }
}