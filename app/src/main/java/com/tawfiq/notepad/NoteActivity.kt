package com.tawfiq.notepad

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.tawfiq.notepad.NoteDetailActivity.Companion.EXTRA_NOTE
import com.tawfiq.notepad.NoteDetailActivity.Companion.EXTRA_NOTE_INDEX
import com.tawfiq.notepad.NoteDetailActivity.Companion.REQUEST_CODE_EDIT
import com.tawfiq.notepad.adapter.NoteAdapter
import com.tawfiq.notepad.model.Note
import com.tawfiq.notepad.utils.deleteNoteStock
import com.tawfiq.notepad.utils.listLoadNote
import com.tawfiq.notepad.utils.persisNote

class NoteActivity : AppCompatActivity() ,View.OnClickListener{

    private lateinit var listNote : MutableList<Note>
    private lateinit var adapter: NoteAdapter
    private lateinit var coordinatorLayout: CoordinatorLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)
        val recyclerView : RecyclerView = findViewById(R.id.recycleview_note)
        val toolbar : Toolbar = findViewById(R.id.toolbar_note)
        setSupportActionBar(toolbar)
        val floatButton : FloatingActionButton = findViewById(R.id.floatingActionButtonAdd)
        floatButton.setOnClickListener(this)
        coordinatorLayout  = findViewById(R.id.coordinator)

        listNote = listLoadNote(this )

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

        intent.putExtra(EXTRA_NOTE,note as Parcelable)
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
        persisNote(this,note)
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
        val note = listNote.removeAt(noteIndex)
        deleteNoteStock(this,note)
        adapter.notifyDataSetChanged()
        Snackbar.make(coordinatorLayout,"${note.title} supprimé",Snackbar.LENGTH_SHORT).show()
    }
}