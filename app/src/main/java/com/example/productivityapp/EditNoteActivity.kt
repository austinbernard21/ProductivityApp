package com.example.productivityapp

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_edit_note.*
import java.io.*
import java.lang.StringBuilder

class EditNoteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_note)

        var editText = findViewById<EditText>(R.id.note_edit_text)
        var updateButton = findViewById<Button>(R.id.update_button)
        var clearButton = findViewById<Button>(R.id.clear_text_button)

        val noteTitle = intent.getStringExtra(NOTE_TITLE)

        val newText: String? = null

        var fis:FileInputStream? = null
        var sb = StringBuilder()
        try {
            fis = openFileInput(noteTitle)
            val isr = InputStreamReader(fis)
            val br = BufferedReader(isr)
            var text:String

            if(br.readLine().let { text = it;it != null }) {
                sb.append(text).append("\n")
            }
            editText.setText((sb.toString()))
        }
        catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
        catch (e: IOException) {
            e.printStackTrace()
        }
        finally {
            if (fis != null)
            {
                try
                {
                    fis.close()
                }
                catch (e:IOException) {
                    e.printStackTrace()
                }
            }
        }

        updateButton.setOnClickListener {view ->
            val alertDialog = AlertDialog.Builder(this)
            var fileName = noteTitle
            alertDialog.setMessage("Do you want to overwrite file").setCancelable(false)
                .setPositiveButton("Proceed", DialogInterface.OnClickListener {
                    dialog, id ->
                    this.openFileOutput(fileName, Context.MODE_PRIVATE).use {
                        it.write(editText.text.toString().toByteArray())
                        finish()
                    }
                }).setNegativeButton("Cancel", DialogInterface.OnClickListener {
                    dialog, id -> dialog.cancel()
                })

            alertDialog.show()

        }

        clearButton.setOnClickListener {
            editText.text.clear()
        }

    }

    }