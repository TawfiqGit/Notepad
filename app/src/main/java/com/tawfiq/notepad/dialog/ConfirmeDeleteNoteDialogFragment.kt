package com.tawfiq.notepad.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class ConfirmeDeleteNoteDialogFragment (val noteTitle : String = "" ) : DialogFragment() {

    interface ConfirmDeleteListener{
        fun onDialogPositiveClick()
        fun onDialogNegativeClick()
    }

    var listener : ConfirmDeleteListener?  = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        builder.setMessage("Voulez vous supprimer ?")
        builder.setPositiveButton("Ok", DialogInterface.OnClickListener { dialog, which -> listener?.onDialogPositiveClick() })
        builder.setNegativeButton("Annuler", DialogInterface.OnClickListener { dialog, which -> listener?.onDialogNegativeClick()  })
        return builder.create()
    }
}