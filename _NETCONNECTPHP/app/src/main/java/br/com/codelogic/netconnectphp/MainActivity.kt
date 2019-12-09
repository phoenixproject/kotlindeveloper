package br.com.codelogic.netconnectphp

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import br.com.codelogic.netconnectphp.data.list.ListAdapter
import br.com.codelogic.netconnectphp.data.list.President
import br.com.codelogic.netconnectphp.data.list.TesteObjeto
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.*
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.R.attr.data
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import java.net.URLEncoder

class MainActivity : AppCompatActivity() {

    // Termos que levarão o nome da url ( (se requisição for via GET ou POST)
    // e o conteúdo de dados em JSON  (se requisição for via POST)
    val termos = arrayOfNulls<String>(2);

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // URLs para teste de requisição
        //termos[0] = "http://192.168.1.49/contactsbkp/public/index.php";
        termos[0] = "http://192.168.1.113/php/contactsbkp/public/index.php";
        //termos[0] = "http://192.168.1.113/presidentslist2.json"
        //termos[0] = "http://192.168.1.113/presidentslist.json"
        //termos[0] = "http://codelogic.com.br/presidentslist.json";
        //termos[0] = "http://codelogic.com.br/presidentslist2.json";

        // Tipo de objeto que será enviado ao servidor
        // (só utilizado se a requisição for via POST)
        var ot = TesteObjeto(7)
        // List contendo objetos do tipo TesteObjeto para serem enviados ao servidor
        // (só utilizado se a  requisição for via POST)
        var otlist: MutableList<TesteObjeto> = mutableListOf(ot)

        // Gera conteúdo JSON para ser enviado ao servidor (só utilizado se a requisição for via POST)
        termos[1] = GerarJSON(otlist)

        // Tipos de requisição disponíveis
        //AsyncTaskHandleJsonGET().execute(termos[0])
        //AsyncTaskHandleJsonPOST().execute(termos[0],termos[1]);
        //AsyncTaskHandleJsonPOST2().execute(termos[0],termos[1]);
        //AsyncTaskHandleJsonPOST3().execute(termos[0],termos[1]);
        //AsyncTaskHandleJsonPOST4().execute(termos[0],termos[1]);
    }

    // Exemplo de Requisição via POST
    inner class AsyncTaskHandleJsonPOST : AsyncTask<String, String, String>() {
        override fun doInBackground(vararg dado: String?): String {

            val conexao = URL(dado[0]).openConnection() as HttpURLConnection
            var json = ""

            val stringBuilder = StringBuilder()

            try {
                conexao.readTimeout = 30000
                conexao.connectTimeout = 30000
                conexao.requestMethod = "POST"
                conexao.setRequestProperty("Connection", "keep-alive")
                conexao.setRequestProperty("Content-Type", "application/json")
                conexao.setRequestProperty("Accept", "application/json")
                conexao.setRequestProperty("Accept-Encoding", "gzip")
                conexao.setRequestProperty("Content-Type", "charset=utf-8")

                conexao.doInput = true
                conexao.doOutput = true

                val outputStream = BufferedOutputStream(conexao.outputStream)
                val writer = BufferedWriter(OutputStreamWriter(outputStream, "utf-8"))

                writer.write(dado[1])
                writer.flush()
                writer.close()

                outputStream.close()

                val jsonObject = JSONObject()
                val inputStream: InputStream

                // get stream
                if (conexao.responseCode < HttpURLConnection.HTTP_BAD_REQUEST) {
                    inputStream = conexao.inputStream
                } else {
                    inputStream = conexao.errorStream
                }

                // parse stream
                val bufferedReader = BufferedReader(InputStreamReader(inputStream))

                var line: String? = null

                while ((line == bufferedReader.readLine()) != null) {
                    stringBuilder.append(line!! + "\n")
                }

                json = stringBuilder.toString()

            } catch (e: NullPointerException) {
                e.printStackTrace()
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                conexao.disconnect()
            }

            return json
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            handleJsonPOST(result)
        }

    }

    // Exemplo de Requisição via POST
    inner class AsyncTaskHandleJsonPOST2 : AsyncTask<String,String,String>() {
        override fun doInBackground(vararg dado: String): String {

            var endererourl : String = dado[0]
            var dados : String = dado[1]
            var json : String = ""

            val stringBuilder = StringBuilder()

            val serverURL: String = endererourl
            val url = URL(serverURL)
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "POST"
            connection.connectTimeout = 300000
            connection.connectTimeout = 300000
            connection.doOutput = true

            val postData: ByteArray = dados.toByteArray(Charset.defaultCharset())

            connection.setRequestProperty("charset", "utf-8")
            connection.setRequestProperty("Content-lenght", postData.size.toString())
            connection.setRequestProperty("Content-Type", "application/json")

            try {

                val outputStream: DataOutputStream = DataOutputStream(connection.outputStream)
                outputStream.write(postData)
                outputStream.flush()

            } catch (e: NullPointerException) {
                e.printStackTrace()
            } catch (e: Exception) {
                e.printStackTrace()
            }

            if (connection.responseCode != HttpURLConnection.HTTP_OK && connection.responseCode != HttpURLConnection.HTTP_CREATED) {
                try {

                    val inputStream: InputStream? = null

                    val reader: BufferedReader = BufferedReader(InputStreamReader(inputStream))

                    json = reader.readLine()

                    println("There was error while connecting the chat $json")
                    System.exit(0)

                } catch (exception: Exception) {
                    throw Exception("Exception while push the notification  $exception.message")
                }
            }

            json = connection.inputStream.use { it.reader().use{reader -> reader.readText()} }

            return json
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            handleJsonPOST(result)
        }

    }

    // Exemplo de Requisição via POST
    inner class AsyncTaskHandleJsonPOST3 : AsyncTask<String,String,String>() {
        override fun doInBackground(vararg dado: String): String {

            var endererourl : String = dado[0]
            var dados : String = dado[1]
            var json : String = ""

            val stringBuilder = StringBuilder()

            val serverURL: String = endererourl
            val url = URL(serverURL)
            val connection = url.openConnection() as HttpURLConnection

            connection.requestMethod = "POST"
            connection.connectTimeout = 300000
            connection.connectTimeout = 300000
            connection.doOutput = true

            val postData: ByteArray = dados.toByteArray(Charset.defaultCharset())

            connection.setRequestProperty("charset", "utf-8")
            connection.setRequestProperty("Content-length", postData.size.toString())
            connection.setRequestProperty("Content-Type", "application/json")

            try {
                val outputStream: DataOutputStream = DataOutputStream(connection.outputStream)
                outputStream.write(postData)
                outputStream.flush()

                json = connection.inputStream.use { it.reader().use{reader -> reader.readText()} }

            } catch (e: NullPointerException) {
                e.printStackTrace()
            } catch (e: Exception) {
                e.printStackTrace()
            }

            if (connection.responseCode != HttpURLConnection.HTTP_OK && connection.responseCode != HttpURLConnection.HTTP_CREATED) {
                try {

                    val inputStream: InputStream? = null

                    val reader: BufferedReader = BufferedReader(InputStreamReader(inputStream))

                    json = reader.readLine()

                    println("There was error while connecting the chat $json")
                    System.exit(0)

                } catch (exception: Exception) {
                    throw Exception("Exception while push the notification  $exception.message")
                } finally {
                    connection.disconnect()
                }
            }

            return json
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            handleJsonPOST(result)
        }

    }

    // Exemplo de Requisição via POST
    inner class AsyncTaskHandleJsonPOST4 : AsyncTask<String,String,String>() {
        override fun doInBackground(vararg dado: String): String {

            var endererourl : String = dado[0]
            var dados : String = URLEncoder.encode(dado[1], "UTF-8")
            var json : String = ""

            val stringBuilder = StringBuilder()

            val serverURL: String = endererourl
            val url = URL(serverURL)
            val conexao = url.openConnection() as HttpURLConnection

            with(url.openConnection() as HttpURLConnection) {
                // optional default is GET
                requestMethod = "POST"

                readTimeout = 15000
                connectTimeout = 15000

                setRequestProperty("charset", "utf-8")
                setRequestProperty("Content-Type", "application/json")
                setRequestProperty("Accept", "application/json")

                val wr = OutputStreamWriter(getOutputStream());
                wr.write(dados);
                wr.flush();

                println("URL : $url")
                println("Response Code : $responseCode")

                BufferedReader(InputStreamReader(inputStream)).use {
                    val response = StringBuffer()

                    var inputLine = it.readLine()
                    while (inputLine != null) {
                        response.append(inputLine)
                        inputLine = it.readLine()
                    }
                    it.close()
                    println("Response : $response")

                    json = response.toString()
                }
            }

            return json
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            handleJsonPOST(result)
        }

    }

    // Exemplo de Requisição via GET
    inner class AsyncTaskHandleJsonGET : AsyncTask<String, String, String>() {
        override fun doInBackground(vararg dado: String?): String {

            var text: String
            val connection = URL(dado[0]).openConnection() as HttpURLConnection

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

    // Tratamento de resposta do servidor para requisição via GET
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

    // Tratamento de resposta do servidor para requisição via POST
    private fun handleJsonPOST(jsonString: String?) {

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

    // Gera uma Array JSON a partir de uma lista de TipoObjeto
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
