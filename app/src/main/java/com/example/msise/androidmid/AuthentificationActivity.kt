package com.example.msise.androidmid

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.EditText
import com.example.msise.androidmid.database.UserDatabase
import com.example.msise.androidmid.model.UserData
import java.util.*

private const val ID = "id"

class AuthentificationActivity : AppCompatActivity() {

    private lateinit var emailView: EditText
    private lateinit var passwordView: EditText
    private lateinit var loginButton: Button
    private var mDb: UserDatabase? = null
    private lateinit var mDbWorkerThread: DbWorkerThread
    private val mUiHandler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentification)

        mDbWorkerThread = DbWorkerThread("dbWorkerThread")
        mDbWorkerThread.start()
        mDb = UserDatabase.getInstance(this)

        setupView()

        loginButton.setOnClickListener({ saveLoginInfo() })

        if (!PreferenceManager(this).isUserLogOut()) {
            addUser()
        }
    }

    private fun setupView() {
        emailView = findViewById<EditText>(R.id.activity_et_email)
        passwordView = findViewById<EditText>(R.id.activity_et_password)
        loginButton = findViewById(R.id.activity_button_login)
    }

    private fun saveLoginInfo() {
        val email = emailView.text
        val password = passwordView.text

        PreferenceManager(this).saveLoginDetails(email = email.toString(), password = password.toString())

        val user = UserData(Random().nextLong(), email.toString(), password.toString())
        insertDataInDb(user)

        if (!PreferenceManager(this).isUserLogOut()) {
            enterNewsList(user.id!!)
        }
    }

    private fun addUser() {
        val email = emailView.text
        val task = Runnable {
            val userData = mDb?.userDataDao()?.getAll()
            mUiHandler.post({
                if (userData == null) {

                    return@post
                } else {
                    for (user in userData) {
                        if (email.equals(user.email)) {
                            enterNewsList(user.id!!)

                            return@post
                        }
                    }

                    saveLoginInfo()
                }
            })
        }

        mDbWorkerThread.postTask(task)
    }

    private fun insertDataInDb(userData: UserData) {
        val task = Runnable { mDb?.userDataDao()?.insert(userData) }
        mDbWorkerThread.postTask(task)
    }

    private fun enterNewsList(id: Long) {
        val intent: Intent = Intent(this, MainActivity::class.java)
        intent.putExtra(ID, id)
        startActivity(intent)
        finish()
    }
}
