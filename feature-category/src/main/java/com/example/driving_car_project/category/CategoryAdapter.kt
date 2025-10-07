package com.example.driving_car_project.category

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.driving_car_project.feature.category.R
import com.example.driving_car_project.model.QuestionType

class CategoryAdapter(
    private var categories: List<QuestionType>,
    private val onItemClick: (QuestionType) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>(){

    inner class CategoryViewHolder(
        itemView: View
    ): RecyclerView.ViewHolder(itemView) {
        private val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        private val tvQuantity: TextView = itemView.findViewById(R.id.tvQuantity)
        private val ivIcon: ImageView = itemView.findViewById(R.id.ivIcon)

        fun bind(item: QuestionType){
            tvTitle.text = item.title
            tvQuantity.text = "${item.quantity} câu"

            val iconRes = when (item.id) {
                2001 -> R.drawable.ic_critical
                2002 -> R.drawable.ic_rules
                2003 -> R.drawable.ic_truck
                2004 -> R.drawable.ic_culture
                2005 -> R.drawable.ic_steering
                2006 -> R.drawable.ic_settings
                2007 -> R.drawable.ic_traffic
                2008 -> R.drawable.ic_map
                else -> R.drawable.ic_default_category
            }

            ivIcon.setImageResource(iconRes)

            itemView.setOnClickListener{
                onItemClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_category, parent, false)
        return CategoryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(categories[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newList: List<QuestionType>) {
        categories = newList
        notifyDataSetChanged()
    }

}