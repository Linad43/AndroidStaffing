package com.example.androidstaffing.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.androidstaffing.R
import com.example.androidstaffing.data.DBHelper
import com.example.androidstaffing.data.EnumActivity
import com.example.androidstaffing.model.Person

class MainActivity : AppCompatActivity() {

    private val db = DBHelper(this, null)
    private var listStaff = arrayListOf<Person>()
    private var listRoles = arrayListOf<String>()
    private lateinit var toolbar: Toolbar
    private lateinit var listStaffBTN: Button
    private lateinit var addPersonBTN: Button
    private lateinit var redactListRolesBTN: Button
    private lateinit var databaseBTN: Button
    private lateinit var exitBTN: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        init()

        redactListRolesBTN.setOnClickListener {
            val intent = Intent(this, ListRole::class.java)
            intent.putExtra("roles", listRoles)
            startActivityForResult(intent, EnumActivity.ListRole.ordinal)
        }

        listStaffBTN.setOnClickListener {
            if (listStaff.isEmpty()) {
                Toast.makeText(
                    this,
                    "Список сотрудников пуст",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val intent = Intent(this, ListStaff::class.java)
                intent.putExtra(Person::class.java.simpleName, listStaff)
//                startActivity(intent)
                startActivityForResult(intent, EnumActivity.ListStaff.ordinal)
            }
        }

        addPersonBTN.setOnClickListener {
            val intent = Intent(this, AddPerson::class.java)
            intent.putExtra("roles", listRoles)
//            resultLauncher.launch(intent)
            startActivityForResult(intent, EnumActivity.AddPerson.ordinal)
        }

        databaseBTN.setOnClickListener {
            val intent = Intent(this, DataBaseActivity::class.java)
            startActivityForResult(intent, EnumActivity.DataBaseActivity.ordinal)
        }

        exitBTN.setOnClickListener {
            finishAffinity()
        }
    }

    private fun fillingRoles() {
        listRoles.add("Стажер")
        listRoles.add("Преподаватель")
        listRoles.add("Лаборант")
        listRoles.add("Старший преподаватель")
        listRoles.add("Декан")
        listRoles.add("Ректор")
    }

    private fun init() {
        if (listRoles.isEmpty()) {
            fillingRoles()
        }
        toolbar = findViewById(R.id.toolbar)
        listStaffBTN = findViewById(R.id.listStaffBTN)
        addPersonBTN = findViewById(R.id.addPersonBTN)
        redactListRolesBTN = findViewById(R.id.redactListRolesBTN)
        databaseBTN = findViewById(R.id.databaseBTN)
        exitBTN = findViewById(R.id.exitBTN)

        setSupportActionBar(toolbar)

        listStaff = db.getListPersons()
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

    @SuppressLint("NewApi")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            EnumActivity.AddPerson.ordinal -> {
                if (resultCode == Activity.RESULT_OK) {
                    val person =
                        data?.extras?.getParcelable(Person::class.java.simpleName) as Person?
                    listStaff.add(person!!)
                    db.addPerson(person)
                    Toast.makeText(
                        this,
                        "Сотрудник добавлен",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            EnumActivity.ListStaff.ordinal -> {
                if (resultCode == Activity.RESULT_OK) {
                    listStaff = data?.extras?.getParcelableArrayList(
                        Person::class.java.simpleName,
                        Person::class.java
                    ) as ArrayList<Person>
                    db.addListPersons(listStaff)
                }
            }

            EnumActivity.ListRole.ordinal -> {
                if (resultCode == Activity.RESULT_OK) {
                    listRoles = data?.getStringArrayListExtra("roles")!!
                }
            }

            EnumActivity.DataBaseActivity.ordinal -> {
                if (resultCode == Activity.RESULT_OK) {
                    listStaff = db.getListPersons()
                }
            }
        }

    }
//    var resultLauncher = registerForActivityResult(
//        ActivityResultContracts.StartActivityForResult()
//    ){result->
//        if(result.resultCode == Activity.RESULT_OK){
//            val data = result.data?.extras?.getParcelable(Person::class.java.simpleName) as Person?
//            //val data1 = result.data?.extras
//            listStaff.add(data as Person)
//            Toast.makeText(
//                this,
//                "Сотрудник добавлен",
//                Toast.LENGTH_SHORT
//            ).show()
//        }
//    }
}