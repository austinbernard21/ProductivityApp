package com.example.productivityapp.fragments

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.productivityapp.R
import kotlinx.android.synthetic.main.fragment_tasks.*
import kotlinx.android.synthetic.main.fragment_tasks.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

lateinit var rowMain:View
lateinit var taskCheck: CheckBox

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
        var itemlist = arrayListOf<String>("Yes","No")
        var adapter = ArrayAdapter<String>(view.context,android.R.layout.simple_list_item_1,itemlist)
//        listView.adapter = adapter
        listView.adapter = MyCustomAdapter(view.context)


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
            if (taskCheck != null) {
                if (taskCheck.isChecked) {
                    println(position)
                    println("checked")
                } else {
                    println("not checked")
                }
            } else {
                println("null")
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
            return 4
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