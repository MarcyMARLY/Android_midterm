package com.example.msise.androidmid.fragment

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.msise.androidmid.DbWorkerThread

import com.example.msise.androidmid.R
import com.example.msise.androidmid.TodoClickListener
import com.example.msise.androidmid.TodoFormActivity
import com.example.msise.androidmid.adapter.DoneAdapter
import com.example.msise.androidmid.adapter.TodoAdapter
import com.example.msise.androidmid.database.TodoDatabase
import com.example.msise.androidmid.model.TodoData
import kotlinx.android.synthetic.main.fragment_done.fragment_done_rv
import kotlinx.android.synthetic.main.fragment_todo.fragment_todo_rv

class DoneFragment : Fragment() {

    private lateinit var recView: RecyclerView
    private var todoList: MutableList<TodoData>? = ArrayList()
    private lateinit var doneAdapter: DoneAdapter
    private var mDb: TodoDatabase? = null
    private lateinit var mDbWorkerThread: DbWorkerThread
    private val mUiHandler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mDbWorkerThread = DbWorkerThread("dbWorkerThread2")
        mDbWorkerThread.start()
        mDb = TodoDatabase.getInstance(context!!)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_done, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recView = fragment_done_rv

        fillList()
    }

    private fun setupView(){
        val linearLayoutManager = LinearLayoutManager(context)

        if (context!!.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            recView!!.layoutManager = linearLayoutManager
        } else {
            recView!!.layoutManager = GridLayoutManager(context, 2)
        }

        todoList?.let {
            doneAdapter = DoneAdapter(todoList!!)
        }
    }

    private fun fillList() {
        val task = Runnable {
            val todoData =
                    mDb?.todoDataDao()?.getAll()
            mUiHandler.post({
                if (todoData == null) {

                    return@post
                } else {
                    for (todo in todoData) {
                        if (todo.flag == 2) {
                            todoList!!.add(todo)
                        }
                    }
                    setupView()
                }
            })
        }
        mDbWorkerThread.postTask(task)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()
    }
}
