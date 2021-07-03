package ru.softmine.imageconverter.ui

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class ProgressDialogFragment(private val onButtonClickListener: OnButtonClickListener? = null) :
    DialogFragment() {

    interface OnButtonClickListener {
        fun onPositiveClick()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            AlertDialog.Builder(it).setMessage("In progress")
                .setPositiveButton("Stop") { _, _ ->
                    onButtonClickListener?.onPositiveClick()
                }.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}