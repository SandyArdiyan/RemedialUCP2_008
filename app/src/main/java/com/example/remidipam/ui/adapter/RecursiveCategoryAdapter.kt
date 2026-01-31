package com.example.remidipam.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.remidipam.R
import com.example.remidipam.entity.Category

class RecursiveCategoryAdapter(
    private var allCategories: List<Category>
) : RecyclerView.Adapter<RecursiveCategoryAdapter.CategoryViewHolder>() {


    private var displayList: List<Pair<Category, Int>> = ArrayList()

    init {
        updateList(allCategories)
    }


    fun updateList(newCategories: List<Category>) {
        allCategories = newCategories
        val result = mutableListOf<Pair<Category, Int>>()


        val roots = allCategories.filter { it.parentId == null }

        roots.forEach { root ->
            addNodeRecursive(root, 0, result)
        }

        displayList = result
        notifyDataSetChanged()
    }


    private fun addNodeRecursive(
        current: Category,
        depth: Int,
        result: MutableList<Pair<Category, Int>>
    ) {
        result.add(Pair(current, depth))

        val children = allCategories.filter { it.parentId == current.id }

        children.forEach { child ->
            addNodeRecursive(child, depth + 1, result)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_category, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val (category, depth) = displayList[position]
        holder.bind(category, depth)
    }

    override fun getItemCount(): Int = displayList.size

    class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvName: TextView = itemView.findViewById(R.id.tvCategoryName)


        private val indentView: View? = itemView.findViewById(R.id.indentView)

        fun bind(category: Category, depth: Int) {
            tvName.text = category.name

            val density = itemView.context.resources.displayMetrics.density
            val paddingStart = (depth * 32 * density).toInt()

            itemView.setPadding(paddingStart, 0, 0, 0)
        }
    }
}