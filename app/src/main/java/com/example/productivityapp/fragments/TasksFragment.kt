package com.example.productivityapp.fragments

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.productivityapp.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_tasks.*
import kotlinx.android.synthetic.main.fragment_tasks.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

private val task_list = arrayListOf<String>("Task1","Tasks2")

/**
 * A simple [Fragment] subclass.
 * Use the [TasksFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TasksFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tasks, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val listView = view.findViewById<ListView>(R.id.task_items)
//        val redColor = Color.parseColor("red")
//        listView.setBackgroundColor(redColor)
//        var itemlist = arrayListOf<String>("Yes","No")
//        var adapter = ArrayAdapter<String>(view.context,android.R.layout.simple_list_item_1,itemlist)
//        listView.adapter = adapter
        var adapter = MyCustomAdapter(view.context)
        listView.adapter = adapter

        val fab = view.findViewById<FloatingActionButton>(R.id.floatingActionButton)

        fab.setOnClickListener {view ->
            val alertDialog = AlertDialog.Builder(view.context)
            val textEditText = EditText(view.context)
            alertDialog.setTitle("Enter To Do Item")
            alertDialog.setView(textEditText)
            alertDialog.setPositiveButton("Add") {dialog, which ->
                task_list.add(textEditText.text.toString())
                adapter.notifyDataSetChanged()
            }
            alertDialog.show()
        }
    }


    private class MyCustomAdapter(context: Context): BaseAdapter() {

        private val mContext: Context


        init {
            mContext = context
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

            val layoutInflater = LayoutInflater.from(mContext)
            val rowMain = layoutInflater.inflate(R.layout.row_tasks,parent,false)
            val taskTextView = rowMain.findViewById<TextView>(R.id.item_textView)
            val taskCheck: CheckBox? = rowMain.findViewById<CheckBox>(R.id.task_checkBox)
            taskTextView.text = task_list.get(position)
            taskCheck?.setOnCheckedChangeListener {buttonView: CompoundButton?, isChecked: Boolean ->  
                println(isChecked)
            }

            return rowMain
        }

        override fun getItem(position: Int): Any {
            return "Test string"
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }
        //how many rows in list
        override fun getCount(): Int {
            return task_list.size
        }



    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TasksFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TasksFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}