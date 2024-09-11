package com.example.sqldatabasekotlin.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MyDBHelper(context: Context) : SQLiteOpenHelper(
    context,
    "todos:db",
    null,
    1
) {

    // below called "when" database is created for the first time
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(TodoTable.CMD_CREATE_TABLE)
    }

    // below when database schema is changed
    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }

}