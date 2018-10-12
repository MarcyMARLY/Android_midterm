package com.example.msise.androidmid.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.msise.androidmid.R
import com.example.msise.androidmid.TodoClickListener
import com.example.msise.androidmid.model.TodoData

class TodoAdapter(
        private val todoList: List<TodoData>,
        private val listener: TodoClickListener? = null
) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.row_article, parent, false)
        return TodoViewHolder(itemView, listener)
    }

    override fun getItemCount(): Int = todoList.size

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val currentTodo: TodoData = todoList[position]
        holder.title.text = currentTodo.title
    }

    class TodoViewHolder(itemView: View, listener: TodoClickListener?) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var title: TextView
        var mListener: TodoClickListener? = null

        init {
            title = itemView.findViewById(R.id.row_article_title)
            mListener = listener
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View?) {
            if (view != null) {
                mListener!!.onClick(view, adapterPosition)
            }
        }
    }
}