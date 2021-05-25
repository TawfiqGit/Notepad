package com.tawfiq.notepad.utils

import android.content.Context
import android.text.TextUtils
import android.util.Log
import com.tawfiq.notepad.model.Note
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.util.*

private val TAG = "stock"

fun persisNote (context :Context, note :Note){
    if(TextUtils.isEmpty(note.filename)){
        note.filename  = UUID.randomUUID().toString() + ".note"
    }

    Log.i(TAG,"Saving note $note")
    val fileOutput = context.openFileOutput(note.filename,Context.MODE_PRIVATE)//Only open by app
    val outputStream = ObjectOutputStream(fileOutput)
    outputStream.writeObject(note)
    outputStream.close()
}

fun listLoadNote(context: Context) : MutableList<Note>{
    val listNote = mutableListOf<Note>()
    val noteDir = context.filesDir

    for (filename in noteDir.list()){
        val note = loadNote(context,filename)
        Log.i(TAG,"Loaded note $filename")
        listNote.add(note)
    }
    return listNote
}

fun deleteNoteStock (context: Context,note: Note){
    context.deleteFile(note.filename)
}

private fun loadNote (context: Context, filename : String) : Note{
    Log.i(TAG,"Loading note $filename")
    val fileOutput = context.openFileInput(filename)
    val inputStream = ObjectInputStream(fileOutput)
    val note = inputStream.readObject() as Note
    inputStream.close()
    return note
}