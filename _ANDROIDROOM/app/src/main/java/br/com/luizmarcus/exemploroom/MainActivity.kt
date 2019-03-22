package br.com.luizmarcus.exemploroom

import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

class MainActivity : AppCompatActivity() {


    private var database: CarDatabase? = null
    private var databaseCarChild: CarChildDatabase? = null

    private var textView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        database = CarDatabase.getInstance(this)
        databaseCarChild = CarChildDatabase.getInstance(this)

        textView = findViewById(R.id.text)

        UpdateViewTask(database,textView).execute()
        UpdateViewTaskChild(databaseCarChild,textView).execute()
    }

    /**
     * AsyncTask que insere os carros no banco de dados
     */
    private class InsertCarTask(val database: CarDatabase?): AsyncTask<Void,Void,Void?>(){

        override fun doInBackground(vararg params: Void?): Void? {

            var lastId = database?.carDao()?.findLastCarId()

            if (lastId == null){
                lastId = 1
            }else{
                lastId ++
            }

            val car = Car(null,"Car$lastId")
            database?.carDao()?.insert(car)

            return null
        }

    }

    private class InsertCarChildTask(val database: CarChildDatabase?): AsyncTask<Void,Void,Void?>(){

        override fun doInBackground(vararg params: Void?): Void? {

            var lastId = database?.carChildDao()?.findLastCarId()

            if (lastId == null){
                lastId = 1
            }else{
                lastId ++
            }

            var teste = "Car$lastId"

            val car = CarChild(null,"Car$lastId")
            database?.carChildDao()?.insert(car)

            return null
        }

    }

    /**
     * AsyncTask que limpa o banco de dados
     */
    private class DeleteAllTask(val database: CarDatabase?): AsyncTask<Void,Void,Void?>(){

        override fun doInBackground(vararg params: Void?): Void? {

            database?.carDao()?.deleteAll()

            return null
        }

    }

    private class DeleteAllTaskChild(val database: CarChildDatabase?): AsyncTask<Void,Void,Void?>(){

        override fun doInBackground(vararg params: Void?): Void? {

            database?.carChildDao()?.deleteAll()

            return null
        }

    }

    /**
     * AsyncTask que atualiza a interface com a lista de carros armazenados
     */
    private class UpdateViewTask(val database: CarDatabase?, var textView: TextView?): AsyncTask<Void,Void,List<Car>?>(){

        override fun doInBackground(vararg params: Void?): List<Car>? {

            return database?.carDao()?.getAllCar()
        }

        override fun onPostExecute(result: List<Car>?) {

            if (result!!.isNotEmpty()){
                var text = ""
                for (car in result) {
                    text +=  "Id: ${car.id}, Name: ${car.name}"+"\n"
                }

                textView?.text = text
            }else{
                textView?.text = textView?.context?.getString(R.string.empty)
            }

        }

    }

    private class UpdateViewTaskChild(val database: CarChildDatabase?, var textView: TextView?): AsyncTask<Void,Void,List<CarChild>?>(){

        override fun doInBackground(vararg params: Void?): List<CarChild>? {

            return database?.carChildDao()?.getAllCar()
        }

        override fun onPostExecute(result: List<CarChild>?) {

            if (result!!.isNotEmpty()){
                var text = ""
                for (car in result) {
                    text +=  "Id: ${car.id}, Name: ${car.name}"+"\n"
                }

                textView?.text = text
            }else{
                textView?.text = textView?.context?.getString(R.string.empty)
            }

        }

    }

    fun insertCar(view: View){
        InsertCarTask(database).execute()
        UpdateViewTask(database,textView).execute()
    }

    fun insertCarChild(view: View){
        InsertCarChildTask(databaseCarChild).execute()
        UpdateViewTaskChild(databaseCarChild,textView).execute()
    }

    fun deleteAll(view: View){
        DeleteAllTask(database).execute()
        UpdateViewTask(database,textView).execute()
    }

    fun deleteAllCarChild(view: View){
        DeleteAllTaskChild(databaseCarChild).execute()
        UpdateViewTaskChild(databaseCarChild,textView).execute()
    }


}
