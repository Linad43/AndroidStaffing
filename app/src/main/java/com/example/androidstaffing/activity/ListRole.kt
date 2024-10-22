package com.example.androidstaffing.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.androidstaffing.R
import com.example.androidstaffing.servise.MyAlertPerson
import com.example.androidstaffing.servise.MyAlertRoles
import com.example.androidstaffing.servise.Removable

class ListRole : AppCompatActivity(), Removable {

    private var listRole = arrayListOf<String>()
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var toolbar: Toolbar
    private lateinit var listRoleLV: ListView
    private lateinit var addRoleBTN: Button
    private lateinit var roleET: EditText
    private lateinit var backBTN: Button

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_list_role)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        init()

        addRoleBTN.setOnClickListener {
            if (roleET.text.isNotEmpty()) {
                listRole.add(roleET.text.toString())
                adapter.notifyDataSetChanged()
                roleET.text.clear()
                roleET.setHint(R.string.enterRole)
                roleET.setHintTextColor(R.color.gray)
            } else {
                roleET.hint = "Поле не может быть пустым"
                roleET.setHintTextColor(R.color.red)
            }
        }

        backBTN.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("roles", listRole)
            setResult(RESULT_OK, intent)
            finish()
        }

        listRoleLV.onItemClickListener =
            AdapterView.OnItemClickListener{ parent, viev, position, id ->
                val role = adapter.getItem(position)
                val dialog = MyAlertRoles()
                val args = Bundle()
                args.putString("role", role)
                dialog.arguments = args
                dialog.show(supportFragmentManager, "custom")
            }

    }

    private fun init() {
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        listRoleLV = findViewById(R.id.listRoleLV)
        addRoleBTN = findViewById(R.id.addRoleBTN)
        roleET = findViewById(R.id.roleET)
        backBTN = findViewById(R.id.backBTN)
        listRole = intent.getStringArrayListExtra("roles") as ArrayList<String>
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, listRole)
        listRoleLV.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_list_role, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.exit -> {
                finishAffinity()
            }

            R.id.sort -> {
                listRole.sort()
                adapter.notifyDataSetChanged()
            }
        }
        return true
    }

    override fun remove(element: Any) {
        adapter.remove(element as String)
    }
}