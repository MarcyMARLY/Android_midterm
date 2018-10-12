package com.example.msise.androidmid

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.TextView
import com.example.msise.androidmid.database.TodoDatabase
import com.example.msise.androidmid.model.TodoData
import kotlinx.android.synthetic.main.activity_todo_form.activity_button
import kotlinx.android.synthetic.main.activity_todo_form.activity_et_title

private const val ID = "id"
private const val TITLE = "title"
private const val FLAG = "flag"
private const val EMAILID = "emailId"

class TodoFormActivity : AppCompatActivity() {

    private lateinit var title: TextView
    private lateinit var titleValue: String
    private var id: Long = 0
    private var flag: Int = 0
    private lateinit var emailId: String
    private var mDb: TodoDatabase? = null
    private lateinit var mDbWorkerThread: DbWorkerThread
    private val mUiHandler = Handler()
    private lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo_form)
        mDbWorkerThread = DbWorkerThread("dbWorkerThread1")
        mDbWorkerThread.start()
        mDb = TodoDatabase.getInstance(this)
        getData()
        setupView()
    }

    private fun getData() {
        val intent = intent
        titleValue = intent.extras[TITLE] as String
        id = intent.extras[ID] as Long
        flag = intent.extras[FLAG] as Int
        emailId = intent.extras[EMAILID] as String
    }

    private fun setupView() {
        title = activity_et_title
        button = activity_button
        title.text = titleValue
        if (titleValue != "") {
            button.text = "SAVE"
        } else {
            button.text = "DONE"
        }
        button.setOnClickListener{

        }
    }

    private fun insertWeatherDataInDb(todoData: TodoData) {
        val task = Runnable { mDb?.todoDataDao()?.insert(todoData = todoData) }
        mDbWorkerThread.postTask(task)
    }

}
