package com.mvvm.notesapp.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.mvvm.notesapp.Models.Note
import com.mvvm.notesapp.R
import kotlin.random.Random

class NotesAdapter(private val context: Context, val listener: NotesClickListener) :
    RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    private val NoteList = ArrayList<Note>()
    private val fullList = ArrayList<Note>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return NoteList.size
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val current = NoteList[position]

        holder.tv_title.text = current.title
        holder.tv_title.isSelected = true

        holder.tv_note.text = current.note

        holder.tv_date.text = current.date
        holder.tv_date.isSelected = true

        holder.notes_layout.setBackgroundColor(
            holder.itemView.resources.getColor(
                randomColor(),
                null
            )
        )

        holder.notes_layout.setOnClickListener {
            listener.onItemClicked(NoteList[holder.adapterPosition])
        }

        holder.notes_layout.setOnLongClickListener {
            listener.onLongItemCLicked(NoteList[holder.adapterPosition], holder.notes_layout)
            true
        }
    }

    fun updateList(newList: List<Note>) {
        fullList.clear()
        fullList.addAll(newList)

        NoteList.clear()
        NoteList.addAll(fullList)

        notifyDataSetChanged()
    }

    fun filterList(search: String) {
        NoteList.clear()

        for (item in fullList) {
            if (item.title?.lowercase()?.contains(search.lowercase()) == true ||
                item.note?.lowercase()?.contains(search.lowercase()) == true
            ) {
                NoteList.add(item)
            }
        }

        notifyDataSetChanged()
    }

    fun randomColor(): Int {

        val list = ArrayList<Int>()
        list.add(R.color.note_color1)
        list.add(R.color.note_color2)
        list.add(R.color.note_color3)
        list.add(R.color.note_color4)
        list.add(R.color.note_color5)
        list.add(R.color.note_color6)

        val seed = System.currentTimeMillis().toInt()
        val randomIndex = Random(seed).nextInt(list.size)

        return list[randomIndex]
    }

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val notes_layout = itemView.findViewById<CardView>(R.id.card_layout)
        val tv_title = itemView.findViewById<TextView>(R.id.tv_title)
        val tv_note = itemView.findViewById<TextView>(R.id.tv_note)
        val tv_date = itemView.findViewById<TextView>(R.id.tv_date)

    }

    interface NotesClickListener {
        fun onItemClicked(note: Note)
        fun onLongItemCLicked(note: Note, cardView: CardView)
    }
}