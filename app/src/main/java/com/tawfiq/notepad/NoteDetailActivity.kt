package com.tawfiq.notepad

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.tawfiq.notepad.model.Note

class NoteDetailActivity : AppCompatActivity() {

    companion object{
        const val EXTRA_NOTE ="note"
        const val EXTRA_NOTE_INDEX ="note_index"
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

        note = intent.getParcelableExtra<Note>(EXTRA_NOTE)
        noteIndex = intent.getIntExtra (EXTRA_NOTE_INDEX,-1)

        //Initilisation
        textViewTitle.text = note.title
        textViewDetail.text = note.description
    }
}