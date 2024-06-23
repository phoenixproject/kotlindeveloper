package br.edu.ifes.ifeshomework6

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import br.edu.ifes.ifeshomework6.ui.theme.IFESHOMEWORK6Theme
import java.io.File


class MainActivity : ComponentActivity() {

    private val fileName = "novo_arquivo.txt"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val writeButton: Button = findViewById(R.id.writeButton)
        val readButton: Button = findViewById(R.id.readButton)
        val fileContent: TextView = findViewById(R.id.fileContent)

        writeButton.setOnClickListener {
            writeToFile("Lendo e escrevendo em um arquivo texto!")
        }

        readButton.setOnClickListener {
            val content = readFromFile()
            fileContent.text = content
        }
    }

    private fun writeToFile(data: String) {
        val file = File(filesDir, fileName)
        file.writeText(data)
    }

    private fun readFromFile(): String {
        val file = File(filesDir, fileName)
        return if (file.exists()) {
            file.readText()
        } else {
            "Arquivo não encontrado"
        }
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
    IFESHOMEWORK6Theme {
        Greeting("Android")
    }
}