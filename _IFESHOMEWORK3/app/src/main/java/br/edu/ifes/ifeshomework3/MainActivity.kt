package br.edu.ifes.ifeshomework3

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import br.edu.ifes.ifeshomework3.ui.theme.IFESHOMEWORK2Theme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    // Find the SeekBar in the layout with id name as
    // R.id.seekbar1
    val seek = findViewById<SeekBar>(R.id.percentSeekBar)

    // Set up a listener for SeekBar changes
    seek?.setOnSeekBarChangeListener(
    object : SeekBar.OnSeekBarChangeListener {

        // Handle when the progress changes
        override fun onProgressChanged(seek: SeekBar,
                                       progress: Int, fromUser: Boolean) {

            // Write custom code here if needed
        }

        // Handle when the user starts tracking touch
        override fun onStartTrackingTouch(seek: SeekBar) {

            // Write custom code here if needed
        }

        // Handle when the user stops tracking touch
        override fun onStopTrackingTouch(seek: SeekBar) {

            // Message for Toast
            val output = "Progress is: " + seek.progress + "%"

            // Get the current progress and display it in a toast message
            //Toast.makeText(MainActivity::class.java, output , Toast.LENGTH_SHORT).show()
        }
    })
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    IFESHOMEWORK2Theme {
        Greeting("Android")
    }
}