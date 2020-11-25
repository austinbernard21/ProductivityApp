package com.example.productivityapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView

const val NOTE_TITLE = "com.example.productivityapp.NOTE"

class NoteHistory : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_history)


        var listView = findViewById<ListView>(R.id.note_list)

        var files: Array<String> = this.fileList()

        var adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,files)
        listView.adapter = adapter

        listView.setOnItemClickListener { parent, view, position, id ->
            val element = adapter.getItem(position)
            val intent = Intent(this, EditNoteActivity::class.java).apply {
                putExtra(NOTE_TITLE, element)
            }
            startActivity(intent)
        }

    }


}