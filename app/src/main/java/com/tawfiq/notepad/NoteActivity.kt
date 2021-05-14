package com.tawfiq.notepad

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.tawfiq.notepad.NoteDetailActivity.Companion.EXTRA_NOTE
import com.tawfiq.notepad.NoteDetailActivity.Companion.EXTRA_NOTE_INDEX
import com.tawfiq.notepad.NoteDetailActivity.Companion.REQUEST_CODE_EDIT
import com.tawfiq.notepad.adapter.NoteAdapter
import com.tawfiq.notepad.model.Note

class NoteActivity : AppCompatActivity() ,View.OnClickListener{

    private lateinit var listNote : MutableList<Note>
    private lateinit var adapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)
        val recyclerView : RecyclerView = findViewById(R.id.recycleview_note)
        val toolbar : Toolbar = findViewById(R.id.toolbar_note)
        setSupportActionBar(toolbar)
        val floatButton : FloatingActionButton = findViewById(R.id.floatingActionButtonAdd)
        floatButton.setOnClickListener(this)

        listNote = mutableListOf<Note>()
        listNote.add(Note("Luffy","Capitaine"))
        listNote.add(Note("Nami","Navigatrice"))
        listNote.add(Note("Usop","Tireur"))
        listNote.add(Note("Zorro","Bretteur"))

        adapter = NoteAdapter(listNote,this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode != Activity.RESULT_OK || data == null){
            return super.onActivityResult(requestCode, resultCode, data)
        }

        when (requestCode){
            REQUEST_CODE_EDIT -> processEdit(data)
        }
    }

    override fun onClick(v: View) {
        if (v.tag != null){
            Toast.makeText(baseContext,"position "+v.tag,Toast.LENGTH_LONG).show()
            showNoteDetail(v.tag as Int)
        }else {
            when (v.id){
                R.id.floatingActionButtonAdd -> createNote()
            }
        }
    }

    fun showNoteDetail (noteIndex : Int){
        //If -1 position else new note sinon update data position selected
        val note = if (noteIndex < 0 ) Note() else listNote[noteIndex]
        val intent = Intent (this , NoteDetailActivity::class.java)

        intent.putExtra(EXTRA_NOTE,note)
        intent.putExtra(EXTRA_NOTE_INDEX,noteIndex)
        startActivityForResult(intent,REQUEST_CODE_EDIT)
    }

    private fun processEdit (intent : Intent){
        val noteIndex = intent.getIntExtra(EXTRA_NOTE_INDEX ,-1)
        when(intent.action){
            NoteDetailActivity.ACTION_SAVE_NOTE ->{
                val note = intent.getParcelableExtra<Note>(EXTRA_NOTE)
                saveData(note,noteIndex)
            }

            NoteDetailActivity.ACTION_DELETE_NOTE ->{
                deleteNote(noteIndex)
            }
        }
    }

    private fun saveData (note : Note, noteIndex: Int){
        if (noteIndex < 0){
            listNote.add(0,note)
        }else{
            listNote[noteIndex] = note
        }
        adapter.notifyDataSetChanged()
    }

    private fun createNote(){
        showNoteDetail(-1)
    }

    private fun deleteNote (noteIndex: Int){
        if (noteIndex < 0){
            return
        }
        listNote.removeAt(noteIndex)
        adapter.notifyDataSetChanged()
    }
}