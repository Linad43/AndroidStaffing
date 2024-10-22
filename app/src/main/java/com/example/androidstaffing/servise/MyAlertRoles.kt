package com.example.androidstaffing.servise

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.androidstaffing.model.Person

class MyAlertRoles : DialogFragment() {

    private var removable: Removable? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        removable = context as Removable?
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val role = requireArguments().getString("role")
        val builder = AlertDialog.Builder(
            requireActivity()
        )

        return builder
            .setTitle("Внимание")
            .setMessage("Удалить должность?")
            .setPositiveButton("Да") { dialog, which ->
                removable?.remove(role!!)
            }.setNegativeButton("Отмена", null)
            .create()
    }
}