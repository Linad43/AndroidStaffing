package com.example.androidstaffing.servise

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.androidstaffing.model.Person

class MyAlertPerson : DialogFragment() {

    private var removable: Removable? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        removable = context as Removable?
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val person = requireArguments().getParcelable<Person>(Person::class.java.simpleName)
        val builder = AlertDialog.Builder(
            requireActivity()
        )

        return builder
            .setTitle("Внимание")
            .setMessage("Удалить сотрудника?")
            .setPositiveButton("Да") { dialog, which ->
                removable?.remove(person as Person)
            }.setNegativeButton("Отмена", null)
            .create()
    }
}