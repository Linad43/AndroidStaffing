package com.example.androidstaffing.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.AdapterView
import android.widget.Button
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.androidstaffing.R
import com.example.androidstaffing.model.Person
import com.example.androidstaffing.servise.MyAlertPerson
import com.example.androidstaffing.servise.PersonAdapter
import com.example.androidstaffing.servise.Removable

class ListStaff : AppCompatActivity(), Removable {

    private var listStaff = arrayListOf<Person>()
    private lateinit var adapter:PersonAdapter
    private lateinit var toolbar: Toolbar
    private lateinit var listStaffLV:ListView
    private lateinit var backBTN:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_list_staff)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        init()

        backBTN.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra(Person::class.java.simpleName, listStaff)
            setResult(RESULT_OK, intent)
            finish()
        }

        listStaffLV.onItemClickListener =
            AdapterView.OnItemClickListener{ parent, viev, position, id ->
                val person = adapter.getItem(position)
                val dialog = MyAlertPerson()
                val args = Bundle()
                args.putParcelable(Person::class.java.simpleName, person)
                dialog.arguments = args
                dialog.show(supportFragmentManager, "custom")
            }

    }
    @SuppressLint("NewApi")
    private fun init() {
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        listStaffLV = findViewById(R.id.listStaffLV)
        backBTN = findViewById(R.id.backBTN)
        listStaff = intent?.getParcelableArrayListExtra(Person::class.java.simpleName)!!
        adapter = PersonAdapter(this, listStaff)
        listStaffLV.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_list_staff, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.exit -> {
                finishAffinity()
            }
            R.id.toFirstName->{
                listStaff.sortBy { it.firstName }
                adapter.notifyDataSetChanged()
            }

            R.id.toSecondName->{
                listStaff.sortBy { it.secondName }
                adapter.notifyDataSetChanged()
            }

            R.id.toAge->{
                listStaff.sortBy { it.age }
                adapter.notifyDataSetChanged()
            }

            R.id.toRole->{
                listStaff.sortBy { it.role }
                adapter.notifyDataSetChanged()
            }
        }
        return true
    }
    override fun remove(element: Any) {
        adapter.remove(element as Person)
    }
}