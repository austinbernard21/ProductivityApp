package com.example.productivityapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
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


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item?.itemId) {
            R.id.ic_n_history -> {
                var intent = Intent(this,NoteHistory::class.java)
                startActivity(intent)
                return true
            }
            R.id.ic_t_history -> {
                var intent = Intent(this,TaskHistory::class.java)
                startActivity(intent)
                return true
            }
            R.id.ic_settings -> {
                var intent = Intent(this,SettingsActivity::class.java)
                startActivity(intent)
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun makeCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_wrapper, fragment)
            commit()
        }
}