package com.info448.myapplication.adapter

import androidx.recyclerview.widget.RecyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.info448.myapplication.R

class NotesAdapter(
    var notes: List<String>
): RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    var onItemClickedListener: ((note: String, position: Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false))
    }

    override fun getItemCount(): Int = notes.size

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(notes[position], position)
    }

    inner class NoteViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val tv = itemView.findViewById<TextView>(R.id.tvNoteContent)

        fun bind(note: String, position: Int) {
            tv.text = note
            itemView.setOnClickListener {
                onItemClickedListener?.invoke(note, position)
            }
        }
    }

    fun addNewNote() {
        notes = notes.toMutableList().apply {
            add("Suppppp")
        }
        notifyDataSetChanged()
    }


}