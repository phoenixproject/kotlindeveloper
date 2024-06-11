package br.edu.ifes.ifeshomework4

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity

class Activity3 : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_3)
    }

    fun clickActivityMainA3(view: View) {

        val i: Intent = Intent(
            this@Activity3,
            MainActivity::class.java
        )
        startActivity(i)
    }

    fun clickActivity2A3(view: View) {

        val i: Intent = Intent(
            this@Activity3,
            Activity2::class.java
        )
        startActivity(i)
    }

}

