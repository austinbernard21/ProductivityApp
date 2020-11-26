package com.example.productivityapp.fragments

import android.content.Context
import android.content.SharedPreferences.Editor
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import com.example.productivityapp.R
import kotlinx.android.synthetic.main.fragment_timer.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


/**
 * A simple [Fragment] subclass.
 * Use the [TimerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TimerFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var START_MILLI_SECONDS = 60000L

    private var countdown_timer: CountDownTimer? = null
    private var isRunning: Boolean = false;
    private var time_left_in_millis = START_MILLI_SECONDS
    private var endTime = 0L
    private var itemlist: MutableList<String> = ArrayList()


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
        return inflater.inflate(R.layout.fragment_timer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pref = activity?.getSharedPreferences("pref", Context.MODE_PRIVATE)
        val editor =  pref?.edit()
        val allEntries: Map<String, *> = pref?.getAll() as Map<String, *>
        for ((key, value) in allEntries.entries) {
            if (key.startsWith("timer_", false)) {
                itemlist.add(value as String)
            }
        }

        editor?.putInt("TIMERS", itemlist.size)
        for (item in itemlist) {
            editor?.putString("timer_$item", item)
        }
        editor?.apply()

        button_start.setOnClickListener() {
            if(!isRunning) {
                startTimer()
            }
        }


        button_pause.setOnClickListener() {
            if(isRunning) {
               pauseTimer()
            }
        }

        button_reset.setOnClickListener() {
            resetTimer()
        }

//        updateCountDownText()


        val spinner = view.findViewById<Spinner>(R.id.spinner)
//        var itemlist = arrayOf<String>("2","5")
        var adapter = ArrayAdapter<String>(view.context,android.R.layout.simple_spinner_dropdown_item,itemlist)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                //nothing
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (view != null) {
                    START_MILLI_SECONDS = itemlist[position].toLong() * 60000L
                    resetTimer()
                }
            }

        }
    }

    private fun startTimer() {
        endTime = System.currentTimeMillis() + time_left_in_millis

        countdown_timer = object : CountDownTimer(time_left_in_millis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                time_left_in_millis = millisUntilFinished
                updateCountDownText()
            }

            override fun onFinish() {
                isRunning = false
            }
        }.start()
        isRunning = true
    }

    private fun pauseTimer() {
        countdown_timer?.cancel()
        isRunning = false
    }

    private fun resetTimer() {
        time_left_in_millis = START_MILLI_SECONDS
        updateCountDownText()
    }

    private fun updateCountDownText() {
        val minute = (time_left_in_millis / 1000) / 60
        val seconds = (time_left_in_millis / 1000) % 60
        if (minute > 9) {
            text_view_countdown.text = "$minute:$seconds"
        } else {
            text_view_countdown.text = "0$minute:$seconds"
        }

    }

    override fun onStop() {
        super.onStop()

        val pref = activity?.getSharedPreferences("pref", Context.MODE_PRIVATE)
        val editor =  pref?.edit()

        editor?.putLong("millisLeft", time_left_in_millis);
        editor?.putBoolean("timerRunning", isRunning);
        editor?.putLong("endTime", endTime);

        editor?.apply()

        countdown_timer?.cancel()

    }

    override fun onStart() {
        super.onStart()

        val pref = activity?.getSharedPreferences("pref", Context.MODE_PRIVATE)

        if (pref != null) {
            time_left_in_millis = pref.getLong("millisLeft", START_MILLI_SECONDS)
            isRunning = pref.getBoolean("timerRunning", false)
        }

        updateCountDownText()

        if (isRunning) {
            if (pref != null) {
                endTime = pref.getLong("endTime", 0)
                time_left_in_millis = endTime - System.currentTimeMillis()
            }
            if (time_left_in_millis < 0) {
                time_left_in_millis = 0
                isRunning = false
                updateCountDownText()
            } else {
                startTimer()
            }

        }

    }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TimerFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TimerFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}