package com.example.androidstaffing.servise

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.androidstaffing.R
import com.example.androidstaffing.model.Person

class PersonAdapter(context: Context, personList: List<Person>):
ArrayAdapter<Person>(context, R.layout.item_staff, personList){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        val person = getItem(position)
        if(view==null){
            view=LayoutInflater.from(context)
                .inflate(R.layout.item_staff, parent, false)
        }
        val secondName = view?.findViewById<TextView>(R.id.secondName)
        val firstName = view?.findViewById<TextView>(R.id.firstName)
        val age = view?.findViewById<TextView>(R.id.age)
        val role = view?.findViewById<TextView>(R.id.role)

        secondName?.text = person?.secondName
        firstName?.text = person?.firstName
        age?.text = person?.age.toString()
        role?.text = person?.role

        return view!!
    }
}