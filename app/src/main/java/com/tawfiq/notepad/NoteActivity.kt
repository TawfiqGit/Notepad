package com.tawfiq.notepad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tawfiq.notepad.adapter.NoteAdapter
import com.tawfiq.notepad.model.Note

class NoteActivity : AppCompatActivity() ,View.OnClickListener{

    private lateinit var note : MutableList<Note>
    private lateinit var adapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)
        val recyclerView : RecyclerView = findViewById(R.id.recycleview_note)

        note = mutableListOf<Note>()
        note.add(Note("Luffy","Capitaine"))
        note.add(Note("Nami","Navigatrice"))
        note.add(Note("Usop","Tireur"))
        note.add(Note("Zorro","Bretteur"))

        adapter = NoteAdapter(note,this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    override fun onClick(v: View) {
        if (v.tag != null){
            Toast.makeText(baseContext,"position "+v.tag,Toast.LENGTH_LONG).show()
            showNoteDetail(v.tag as Int)
        }
    }

    fun showNoteDetail (noteIndex : Int){
        val note = note[noteIndex]
        val intent = Intent (this , NoteDetailActivity::class.java)

        intent.putExtra(NoteDetailActivity.EXTRA_NOTE,note)
        intent.putExtra(NoteDetailActivity.EXTRA_NOTE_INDEX,noteIndex)
        startActivity(intent)
    }
}