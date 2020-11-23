package com.example.productivityapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import androidx.fragment.app.Fragment
import com.example.productivityapp.fragments.NotesFragment
import com.example.productivityapp.fragments.TasksFragment
import com.example.productivityapp.fragments.TimerFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val timerFragment = TimerFragment()
        val tasksFragment = TasksFragment()
        val notesFragment = NotesFragment()

        makeCurrentFragment(timerFragment)

        bottom_navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.ic_alarm -> makeCurrentFragment(timerFragment)
                R.id.ic_tasks -> makeCurrentFragment(tasksFragment)
                R.id.ic_notes -> makeCurrentFragment(notesFragment)
            }
            true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.options_menu, menu)
        return true
    }

    private fun makeCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_wrapper, fragment)
            commit()
        }
}