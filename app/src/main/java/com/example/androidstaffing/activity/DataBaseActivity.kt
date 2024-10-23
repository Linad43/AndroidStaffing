package com.example.androidstaffing.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.androidstaffing.R
import com.example.androidstaffing.data.DBHelper
import com.example.androidstaffing.model.Person
import kotlin.jvm.Throws

class DataBaseActivity : AppCompatActivity() {

    private val db = DBHelper(this, null)
    private var listPerson = arrayListOf<Person>()
    private lateinit var toolbar: Toolbar
    private lateinit var clearDBBTN: Button
    private lateinit var backBTN: Button
    private lateinit var listPersonsTV: TextView
//    private lateinit var listRoleTV: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_data_base)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        init()
        clearDBBTN.setOnClickListener {
            db.removeAll()
            val intent = Intent(this, MainActivity::class.java)
            setResult(RESULT_OK, intent)
            finish()
        }
        backBTN.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            setResult(RESULT_CANCELED, intent)
            finish()
        }
    }

    private fun init() {
        toolbar = findViewById(R.id.toolbar)
        clearDBBTN = findViewById(R.id.clearDatabase)
        backBTN = findViewById(R.id.backBTN)
        listPersonsTV = findViewById(R.id.listPersonTV)
//        listRoleTV = findViewById(R.id.listRolesTV)

        setSupportActionBar(toolbar)

        listPerson = db.getListPersons()
        listPersonsTV.append("\n")
        for (person in listPerson){
            listPersonsTV.append(person.toString()+"\n")
        }
//        listPerson.forEach{
//            listRoleTV.append(it.toString())
//        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.exit -> {
                finishAffinity()
            }
        }
        return true
    }
}