package com.example.androidstaffing.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.androidstaffing.model.Person

class DBHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {
    companion object {
        private val DATABASE_NAME = "PERSON_DATABASE"
        private val DATABASE_VERSION = 1
        val TABLE_PERSONS = "person_table"
        val KEY_ID = "id"
        val KEY_SECOND_NAME = "second_name"
        val KEY_FIRST_NAME = "first_name"
        val KEY_AGE = "age"
        val KEY_ROLE = "role"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val query = ("CREATE TABLE $TABLE_PERSONS " +
                "($KEY_ID INTEGER PRIMARY KEY, " +
                "$KEY_SECOND_NAME TEXT, " +
                "$KEY_FIRST_NAME TEXT, " +
                "$KEY_AGE TEXT, " +
                "$KEY_ROLE TEXT)")
        db.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_PERSONS")
    }

    fun addPerson(person: Person) {
        val values = ContentValues()
        values.put(KEY_SECOND_NAME, person.secondName)
        values.put(KEY_FIRST_NAME, person.firstName)
        values.put(KEY_AGE, person.age.toString())
        values.put(KEY_ROLE, person.role)
        val db = this.writableDatabase
        db.insert(TABLE_PERSONS, null, values)
        db.close()
    }

    fun addListPersons(listPersons: List<Person>) {
        removeAll()
        listPersons.forEach {
            addPerson(it)
        }
    }

    fun getInfo(): Cursor? {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_PERSONS", null)
    }

    fun removeAll() {
        val db = this.writableDatabase
        db.delete(TABLE_PERSONS, null, null)
    }

    fun getListPersons(): ArrayList<Person> {
        val listPersons = arrayListOf<Person>()
        val cursor = this.getInfo()
        if (cursor != null && cursor.moveToFirst()) {
            cursor.moveToFirst()
            createAndAddPersonToList(cursor, listPersons)
        }
        while (cursor!!.moveToNext()) {
            createAndAddPersonToList(cursor, listPersons)
        }
        return listPersons
    }

    private fun createAndAddPersonToList(
        cursor: Cursor,
        listPersons: ArrayList<Person>,
    ) {
        val secondName = cursor.getString(cursor.getColumnIndex(KEY_SECOND_NAME))
        val firstName = cursor.getString(cursor.getColumnIndex(KEY_FIRST_NAME))
        val age = cursor.getString(cursor.getColumnIndex(KEY_AGE))
        val role = cursor.getString(cursor.getColumnIndex(KEY_ROLE))
        val person = Person(secondName, firstName, age.toInt(), role)
        listPersons.add(person)
    }
}