package com.example.androidstaffing.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.androidstaffing.R
import com.example.androidstaffing.model.Person

class AddPerson : AppCompatActivity() {

    private var listRoles = arrayListOf<String>()
    private lateinit var toolbar: Toolbar
    private lateinit var secondNameET: EditText
    private lateinit var firstNameET: EditText
    private lateinit var ageET: EditText
    private lateinit var roleSPN: Spinner
    private lateinit var saveBTN: Button
    private lateinit var cancelBTN: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_person)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        init()

        saveBTN.setOnClickListener {
            if (checkAllIsNotEmpty()) {
                val person = Person(
                    secondNameET.text.toString(),
                    firstNameET.text.toString(),
                    ageET.text.toString().toInt(),
                    roleSPN.selectedItem.toString()
                )
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra(Person::class.java.simpleName, person)
                setResult(RESULT_OK, intent)
                finish()
            }
        }

        cancelBTN.setOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }

    }

    private fun init() {
        toolbar = findViewById(R.id.toolbar)
        secondNameET = findViewById(R.id.secondName)
        firstNameET = findViewById(R.id.firstName)
        ageET = findViewById(R.id.age)
        roleSPN = findViewById(R.id.role)
        saveBTN = findViewById(R.id.save)
        cancelBTN = findViewById(R.id.cancel)
        setSupportActionBar(toolbar)

        listRoles = intent.getStringArrayListExtra("roles")!!
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            listRoles
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        roleSPN.adapter = adapter
    }

    @SuppressLint("ResourceAsColor")
    private fun checkAllIsNotEmpty(): Boolean {
        var flag = true
        if (secondNameET.text.isEmpty()) {
            secondNameET.setHint(R.string.notEmpty)
            secondNameET.setHintTextColor(R.color.red)
            flag = false
        }
        if (firstNameET.text.isEmpty()) {
            firstNameET.setHint(R.string.notEmpty)
            firstNameET.setHintTextColor(R.color.red)
            flag = false
        }
        if (ageET.text.isEmpty()) {
            ageET.setHint(R.string.notEmpty)
            ageET.setHintTextColor(R.color.red)
            flag = false
        }
        return flag
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