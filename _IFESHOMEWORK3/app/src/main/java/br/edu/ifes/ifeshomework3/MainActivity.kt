package br.edu.ifes.ifeshomework3

import android.content.Intent
import android.os.Bundle
import android.view.View
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

    fun clickActivity2(view: View) {
        //Log.e("clickItemHoro", view!!.tag.toString())

        val i: Intent = Intent(
            this@MainActivity,
            Activity2::class.java
        )
        startActivity(i)
    }

    fun clickActivity3(view: View) {

        val i: Intent = Intent(
            this@MainActivity,
            Activity3::class.java
        )
        startActivity(i)
    }
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