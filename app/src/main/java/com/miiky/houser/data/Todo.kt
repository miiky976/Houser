package com.miiky.houser.data

import android.content.Context
import android.database.sqlite.SQLiteOpenHelper
import androidx.compose.ui.platform.LocalContext

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.miiky.houser.models.TodoModel

class DatabaseHandler(context: Context) : SQLiteOpenHelper(context, NAME, null, VERSION) {

    companion object {
        private const val VERSION = 1
        private const val NAME = "TodoDatabase"
        private const val TODO_TABLE = "ToDo"
        private const val ID = "id"
        private const val TASK = "task"
        private const val STATUS = "status"
        private const val CREATE_TODO_TABLE = "CREATE TABLE $TODO_TABLE ($ID INTEGER PRIMARY KEY AUTOINCREMENT, $TASK TEXT, $STATUS INTEGER)"
    }

    private lateinit var db: SQLiteDatabase

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TODO_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS $TODO_TABLE")
        // Create tables again
        onCreate(db)
    }

    fun openDatabase() {
        db = this.writableDatabase
    }

    fun insertTask(task: TodoModel) {
        val cv = ContentValues()
        cv.put(TASK, task.task)
        cv.put(STATUS, 0)
        db.insert(TODO_TABLE, null, cv)
    }

    fun getAllTasks(): List<TodoModel> {
        val taskList = ArrayList<TodoModel>()
        var cur: Cursor? = null
        db.beginTransaction()
        try {
            cur = db.query(
                TODO_TABLE,
                null,
                null,
                null,
                null,
                null,
                null,
                null
            )
            cur?.let {
                if (it.moveToFirst()) {
                    do {
                        val task = TodoModel(it.getInt(it.getColumnIndexOrThrow(ID)), it.getInt(it.getColumnIndexOrThrow(STATUS)), it.getString(it.getColumnIndexOrThrow(TASK)))
                        taskList.add(task)
                    } while (it.moveToNext())
                }
            }
        } finally {
            db.endTransaction()
            cur?.close()
        }
        return taskList
    }

    fun updateStatus(id: Int, status: Int) {
        val cv = ContentValues()
        cv.put(STATUS, status)
        db.update(TODO_TABLE, cv, "$ID=?", arrayOf(id.toString()))
    }

    fun updateTask(id: Int, task: String) {
        val cv = ContentValues()
        cv.put(TASK, task)
        db.update(TODO_TABLE, cv, "$ID=?", arrayOf(id.toString()))
    }

    fun deleteTask(id: Int) {
        db.delete(TODO_TABLE, "$ID=?", arrayOf(id.toString()))
    }
}
