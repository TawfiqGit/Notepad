package com.tawfiq.notepad.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.tawfiq.notepad.model.Note
import com.tawfiq.notepad.R

class NoteAdapter (private val listNote : List<Note>, private val  itemClickListener: View.OnClickListener) 
    : RecyclerView.Adapter<NoteAdapter.ViewHolder>() {

    class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView_note = itemView.findViewById(R.id.textView_note) as TextView
        val textView_description = itemView.findViewById(R.id.textView_description) as TextView
        val cardview = itemView.findViewById(R.id.cardview) as CardView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewItem = LayoutInflater.from(parent.context).inflate(R.layout.items_note,parent,false)
        return ViewHolder(viewItem)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = listNote[position]
        holder.cardview.setOnClickListener(itemClickListener)
        holder.cardview.tag = position
        holder.textView_note.text = note.title
        holder.textView_description.text = note.description
    }

    override fun getItemCount(): Int {
        return listNote.size
    }
}