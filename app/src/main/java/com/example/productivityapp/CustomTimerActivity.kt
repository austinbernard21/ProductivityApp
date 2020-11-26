package com.example.productivityapp

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.res.Configuration
import android.media.Image
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_custom_timer.*
import kotlin.concurrent.timer


class CustomTimerActivity : AppCompatActivity() {

    val timerKeys: MutableList<String>? = ArrayList()
    val timerValues: MutableList<Any>? = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_timer)



        val pref = getSharedPreferences("pref", Context.MODE_PRIVATE)
        val allEntries: Map<String, *> = pref.getAll()
        for ((key, value) in allEntries.entries) {
            if (key.startsWith("timer_", false)) {
                timerKeys?.add(key)
                if (value != null) {
                    timerValues?.add(value)
                }
            }
        }

        val addTimerButton = findViewById<ImageButton>(R.id.add_timer_button)

        addTimerButton.setOnClickListener { view ->
            val alertDialog = AlertDialog.Builder(this)
            val newTimerText = EditText(this)
            newTimerText.setRawInputType(Configuration.KEYBOARD_12KEY)
            alertDialog.setView(newTimerText)
            alertDialog.setMessage("Enter a time in minutes")
            alertDialog.setPositiveButton("Create") { dialog, id ->
                timerKeys?.add("timer_${timerKeys?.size}")
                timerValues?.add(newTimerText.text)
                val sharedPreferences = this.getSharedPreferences("pref", Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.apply{
                    editor.putString("timer_${timerKeys?.size}",newTimerText.text.toString())
                }.apply()
                dialog.dismiss()
            }
            alertDialog.setNegativeButton("Cancel") { dialog, id ->
                dialog.cancel()
            }
            alertDialog.show()
        }


        val timerListView = findViewById<ListView>(R.id.timer_list)
        var adapter = TimerRowAdapter(this,timerKeys,timerValues)
        timerListView.adapter = adapter
        adapter.notifyDataSetChanged()



    }

    private class TimerRowAdapter(context: Context, keys: MutableList<String>?, value: MutableList<Any>?): BaseAdapter() {

        private val mContext: Context
        private val keyList: MutableList<String>?
        private val keyValues: MutableList<Any>?

        init {
            mContext = context
            keyList = keys
            keyValues = value
        }


        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val layoutInflater = LayoutInflater.from(mContext)
            val rowMain = layoutInflater.inflate(R.layout.activity_custom_timer_row,parent,false)
            val timerText = rowMain.findViewById<TextView>(R.id.custom_timer_text)
            val deleteButton = rowMain.findViewById<ImageButton>(R.id.close_button)
            timerText.text = keyValues?.get(position).toString()
            deleteButton.setOnClickListener {view ->
                val alertDialog = AlertDialog.Builder(mContext)
                alertDialog.setMessage("Do you want to delete timer").setCancelable(false)
                    .setPositiveButton("Proceed", DialogInterface.OnClickListener {
                            dialog, id ->
                        val sharedPreferences = mContext.getSharedPreferences("pref", Context.MODE_PRIVATE)
                        val editor = sharedPreferences.edit()
                        editor.apply{
                            if (keyValues != null) {
                                editor.remove(keyList?.get(position).toString())
                            }
                        }.apply()
                        dialog.dismiss()

                        keyList?.remove(keyList?.get(position).toString())
                        keyValues?.remove(keyValues?.get(position).toString())
                        this.notifyDataSetChanged()
                    }).setNegativeButton("Cancel", DialogInterface.OnClickListener {
                            dialog, id -> dialog.cancel()
                    })

                alertDialog.show()
            }

            return rowMain
        }

        override fun getItem(position: Int): Any {
            return "Test String"
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            if (keyList != null) {
                return keyList.size
            }
            return 0
        }

    }


}