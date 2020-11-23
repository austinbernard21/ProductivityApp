package com.example.productivityapp.fragments

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.productivityapp.R
import kotlinx.android.synthetic.main.fragment_timer.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

//var START_MILLI_SECONDS = 60000L
//
//var countdown_timer: CountDownTimer? = null
//var isRunning: Boolean = false;
//var time_left_in_millis = START_MILLI_SECONDS

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

        updateCountDownText();
    }

    private fun startTimer() {
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

        text_view_countdown.text = "$minute:$seconds"
    }




//    private fun startTimer(time_in_seconds: Long) {
//        countdown_timer = object : CountDownTimer(time_in_seconds ,1000) {
//            override fun onFinish() {
//                // do nothing
//            }
//
//            override fun onTick(p0: Long) {
//                time_in_milli_seconds = p0
////                updateTextUI()
//            }
//        }
//        .start()
//
//        isRunning = true
//    }

//    private fun pauseTimer() {
//        countdown_timer?.cancel()
//        isRunning = false
//    }

//    private fun resetTimer() {
//        time_in_milli_seconds = START_MILLI_SECONDS
//        countdown_timer?.cancel()
//        isRunning = false
//        updateTextUI()
//    }
//
//
//    private fun updateTextUI() {
//        val minute = (time_in_milli_seconds / 1000) / 60
//        val seconds = (time_in_milli_seconds / 1000) % 60
//
//        text_view_countdown.text = "$minute:$seconds"
//    }

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