package br.edu.ifes.ifeshomework3

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity

class Activity2 : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_2)
    }

    fun clickActivityMainA2(view: View) {

        val i: Intent = Intent(
            this@Activity2,
            MainActivity::class.java
        )
        startActivity(i)
    }

    fun clickActivity3A2(view: View) {

        val i: Intent = Intent(
            this@Activity2,
            Activity3::class.java
        )
        startActivity(i)
    }

}
