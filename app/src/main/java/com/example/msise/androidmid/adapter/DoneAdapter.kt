package com.example.msise.androidmid.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.msise.androidmid.R
import com.example.msise.androidmid.model.TodoData

class DoneAdapter(
        private val todoList: List<TodoData>
) : RecyclerView.Adapter<DoneAdapter.DoneViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoneViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.row_article, parent, false)
        return DoneViewHolder(itemView)
    }

    override fun getItemCount(): Int = todoList.size

    override fun onBindViewHolder(holder: DoneViewHolder, position: Int) {
        val currentTodo: TodoData = todoList[position]
        holder.title.text = currentTodo.title
    }

    class DoneViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView

        init {
            title = itemView.findViewById(R.id.row_article_title)
        }

    }
}