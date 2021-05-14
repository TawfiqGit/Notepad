package com.tawfiq.notepad

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.tawfiq.notepad.dialog.ConfirmeDeleteNoteDialogFragment
import com.tawfiq.notepad.model.Note

class NoteDetailActivity : AppCompatActivity() {

    companion object{
        const val REQUEST_CODE_EDIT = 1
        const val EXTRA_NOTE ="note"
        const val EXTRA_NOTE_INDEX ="note_index"
        const val ACTION_SAVE_NOTE ="com.tawfiq.notepad.actions.ACTION_SAVE_NOTE"
        const val ACTION_DELETE_NOTE ="com.tawfiq.notepad.actions.ACTION_DELETE_NOTE"
    }

    lateinit var note : Note
    var noteIndex : Int = -1
    lateinit var textViewTitle: TextView
    lateinit var textViewDetail: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_detail)
        textViewTitle = findViewById(R.id.editTextTitre)
        textViewDetail = findViewById(R.id.editTextDetail)
        val toolbar : Toolbar = findViewById(R.id.toolbar_note_detail)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        note = intent.getParcelableExtra<Note>(EXTRA_NOTE)
        noteIndex = intent.getIntExtra (EXTRA_NOTE_INDEX,-1)

        textViewTitle.text = note.title
        textViewDetail.text = note.description
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_note_detail,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.action_save -> {
                saveNote()
                true
            }
            R.id.action_delete->{
                confirmationDelete()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun saveNote (){
        note.title =textViewTitle.text.toString()
        note.description = textViewDetail.text.toString()

        intent = Intent(ACTION_SAVE_NOTE)
        intent.putExtra(EXTRA_NOTE,note)
        intent.putExtra(EXTRA_NOTE_INDEX,noteIndex)
        setResult(Activity.RESULT_OK,intent)
        finish()
    }

    fun confirmationDelete(){
        val confirmerDelete = ConfirmeDeleteNoteDialogFragment(note.title)
        confirmerDelete.listener = object : ConfirmeDeleteNoteDialogFragment.ConfirmDeleteListener{
            override fun onDialogPositiveClick() {
                deleteNote()
            }

            override fun onDialogNegativeClick() {

            }
        }
        confirmerDelete.show(supportFragmentManager,"confimationDelete")
    }

    fun deleteNote(){
        intent = Intent(ACTION_DELETE_NOTE)
        intent.putExtra(EXTRA_NOTE_INDEX,noteIndex)
        setResult(Activity.RESULT_OK,intent)
        finish()
    }
}