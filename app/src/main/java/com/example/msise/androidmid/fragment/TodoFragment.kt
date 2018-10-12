package com.example.msise.androidmid.fragment

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.FloatingActionButton
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
import com.example.msise.androidmid.adapter.TodoAdapter
import com.example.msise.androidmid.database.TodoDatabase
import com.example.msise.androidmid.model.TodoData
import kotlinx.android.synthetic.main.fragment_todo.activity_fab
import kotlinx.android.synthetic.main.fragment_todo.fragment_todo_rv
import java.util.*

private const val ID = "id"
private const val TITLE = "title"
private const val FLAG = "flag"
private const val EMAILID = "emailId"

class TodoFragment : Fragment() {

    private lateinit var recView: RecyclerView
    private var todoList: MutableList<TodoData>? = ArrayList()
    private lateinit var todoAdapter: TodoAdapter
    private var mDb: TodoDatabase? = null
    private lateinit var mDbWorkerThread: DbWorkerThread
    private val mUiHandler = Handler()
    private lateinit var flButton: FloatingActionButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mDbWorkerThread = DbWorkerThread("dbWorkerThread1")
        mDbWorkerThread.start()
        mDb = TodoDatabase.getInstance(context!!)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_todo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recView = fragment_todo_rv

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
            todoAdapter = TodoAdapter(todoList!!, object : TodoClickListener {
                override fun onClick(view: View, position: Int) {
                    val postArticle = todoList!![position]
                    val intent: Intent = Intent(context, TodoFormActivity::class.java)
                    intent.putExtra(ID, postArticle.id)
                    intent.putExtra(TITLE, postArticle.title)
                    intent.putExtra(FLAG, postArticle.flag)
                    intent.putExtra(EMAILID, postArticle.emailId)
                    startActivityForResult(intent, 1)
                }
            })
        }

        flButton = activity_fab
        flButton.setOnClickListener {
            val intent: Intent = Intent(context!!, TodoFormActivity::class.java)
            intent.putExtra(ID, Random().nextLong())
            intent.putExtra(TITLE, "")
            intent.putExtra(FLAG,"")
            intent.putExtra(EMAILID, "")
            startActivityForResult(intent, 1)
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
                        if (todo.flag == 1) {
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
