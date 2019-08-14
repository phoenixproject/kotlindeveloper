package codelogic.com.br.netconnectphp

import android.os.AsyncTask
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.util.Log
import br.com.codelogic.netconnectphp.data.list.ListAdapter
import br.com.codelogic.netconnectphp.data.list.President
import br.com.codelogic.netconnectphp.data.list.TesteObjeto

import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val termos = arrayOfNulls<String>(2);

        //termos[0] = "http://192.168.157.128/contactsbkp/public/index.php";
        termos[0] = "http://codelogic.com.br/presidentslist.json";
        //termos[0] = "http://codelogic.com.br/presidentslist2.json";

        var ot = TesteObjeto(7)
        var otlist: MutableList<TesteObjeto> = mutableListOf(ot)

        termos[1] = GerarJSON(otlist)

        AsyncTaskHandleJsonGET().execute(termos[0])
    }

    inner class AsyncTaskHandleJsonGET : AsyncTask<String, String, String>() {
        override fun doInBackground(vararg url: String?): String {

            var text: String
            val connection = URL(url[0]).openConnection() as HttpURLConnection

            try{
                connection.connect()
                text = connection.inputStream.use { it.reader().use{reader -> reader.readText()} }
            } finally {
                connection.disconnect()
            }

            return text
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            handleJsonGET(result)
        }

        override fun onProgressUpdate(vararg text: String?)
        {

        }
    }

    private fun handleJsonGET(jsonString: String?) {

        val jsonArray = JSONArray(jsonString)

        val list = ArrayList<President>()

        var x = 0
        while (x < jsonArray.length()){

            val jsonObject = jsonArray.getJSONObject(x)

            list.add(President(
                jsonObject.getInt("id"),
                jsonObject.getString("nm"),
                jsonObject.getString("pp"),
                jsonObject.getString("tm")
            ))

            x++
        }

        val adapter = ListAdapter(this, list)
        presidents_list.adapter = adapter
    }

    fun GerarJSON(list: List<TesteObjeto>): String {

        //Inicializa um novo objeto Json
        val jsonObject = JSONObject()

        //Inicializa um novo array Json
        val jsonArray = JSONArray()

        //Inicializa uma lista de Sistema
        var listadetestedeobjetos: List<TesteObjeto> = list

        try {
            //Percorre a lista de Sistemas recebida
            for (ot in listadetestedeobjetos) {

                //Inicializa um novo objeto Json para auxiliar
                val objetojsonauxiliar = JSONObject()

                Log.i("Valor do size: ", list.size.toString())

                //Insere os atributos dentro do objeto Json auxiliar junto com o valor
                objetojsonauxiliar.put("valor", ot.valor)

                //objetojsonauxiliar.put("quantidaderegistros", String.valueOf(listaabrigo.size()));

                //Insere no array Json o objeto auxiliar
                jsonArray.put(objetojsonauxiliar)
            }

            //Define o objeto Json com o nome de abrigo e insere um array Json dentro dele
            jsonObject.put("listadetestedeobjetos", jsonArray)

        } catch (e: JSONException) {
            e.printStackTrace()
        }

        //Retorna o objeto Json em formato de string
        return jsonObject.toString()
    }
}
