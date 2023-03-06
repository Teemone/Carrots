package com.example.carrots.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.carrots.R

class VegetableListAdapter(private val items: ArrayList<Vegetable>):
    RecyclerView.Adapter<VegetableListAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivImage: ImageView = itemView.findViewById(R.id.iv_vegetable)
        val tvName: TextView = itemView.findViewById(R.id.tv_name)
        val btnAdd: ImageButton = itemView.findViewById(R.id.btn_add)
    }

    /**
     * Creates new views using R.layout.recycler_view_item as its template
     */

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_view_item, parent, false))
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.ivImage.setImageResource(items[position].image)
        holder.tvName.text = items[position].name

        holder.btnAdd.setOnClickListener {
            val action = HomeFragmentDirections
                .actionHomeFragmentToOrderSpecificationsFragment(id = position, items[position].name)

            holder.itemView
                .findNavController()
                .navigate(action)
        }
    }
}