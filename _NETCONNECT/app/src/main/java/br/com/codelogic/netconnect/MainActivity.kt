package br.com.codelogic.netconnect

import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log

import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONObject

import java.net.HttpURLConnection
import java.net.URL
import org.json.JSONException
import java.io.*
import java.nio.charset.Charset


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val termos = arrayOfNulls<String>(2)

        //var url = "http://mysafeinfo.com/api/data?list=president&format=json"
        var url = "http://codelogic.com.br/presidentslist.json"
        //var url = "http://localhost:52522/teste/testepost"
        //termos[0] = "http://localhost:52522/teste/testepost"
        //termos[0] = "http://192.168.103.7:52522/teste/testepost"
        //termos[0] = "http://192.168.102.75:57075/api/values"
        //termos[0] = "http://192.168.1.21:10049/teste/testepost"
        //termos[0] = "http://192.168.1.21:10049/teste/create"
        //termos[0] = "http://192.168.103.7:52522/teste/create"
        //termos[0] = "http://192.168.104.33:52522/teste/testepost"
        //termos[0] = "http://10.0.2.2:52522/teste/testepost"

        var ot = TesteObjeto(7)
        var otlist: MutableList<TesteObjeto> = mutableListOf(ot)

        termos[1] = GerarJSON(otlist)


        //AsyncTaskHandleJsonPOST2().execute(termos[0],termos[1]);
        //AsyncTaskHandleJsonGET().execute(termos[0],termos[1]);
        //AsyncTaskHandleJsonGET().execute(termos[0])
        AsyncTaskHandleJsonGET().execute(url);
        //AsyncTaskHandleJsonPOST().execute(url);
        //AsyncTaskHandleJsonPOSTTeste().execute(termos[0],termos[1]);

    }

    inner class AsyncTaskHandleJsonPOST2 : AsyncTask<String,String,String>() {
        override fun doInBackground(vararg url: String): String {

            var endererourl : String = url[0]
            var dados : String = url[1]
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

                //outputStream.close()

            } catch (e: NullPointerException) {
                e.printStackTrace()
            } catch (e: Exception) {
                e.printStackTrace()
            }

            if (connection.responseCode != HttpURLConnection.HTTP_OK && connection.responseCode != HttpURLConnection.HTTP_CREATED) {
                try {

                    val inputStream: InputStream? = null

                    val reader: BufferedReader = BufferedReader(InputStreamReader(inputStream))
                    //val output: String = reader.readLine()
                    json = reader.readLine()

                    //println("There was error while connecting the chat $output")
                    println("There was error while connecting the chat $json")
                    System.exit(0)

                } catch (exception: Exception) {
                    throw Exception("Exception while push the notification  $exception.message")
                }
            }

            /*val jsonObject = JSONObject()
            val inputStream: InputStream

            // get stream
            if (connection.responseCode < HttpURLConnection.HTTP_BAD_REQUEST) {
                inputStream = connection.inputStream
            } else {
                inputStream = connection.errorStream
            }

            // parse stream
            val bufferedReader = BufferedReader(InputStreamReader(inputStream))

            var line: String? = null
            //while ((line = bufferedReader.readLine()) != null) {
            while ((line == bufferedReader.readLine()) != null) {
                stringBuilder.append(line!! + "\n")
            }

            json = stringBuilder.toString()*/
            json = connection.inputStream.use { it.reader().use{reader -> reader.readText()} }

            //return text
            return json
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            handleJsonGET(result)
        }

    }

    inner class AsyncTaskHandleJsonPOSTTeste : AsyncTask<String,String,String>() {
        override fun doInBackground(vararg url: String?): String {

            //var method: String = "teste/testepost/"
            var method: String = "api/values/"

            //var url: URL? = null
            //var url: URL? = URL("http://localhost:52522/" + method)
            //var conexao: HttpURLConnection? = null
            val conexao = URL(url[0]).openConnection() as HttpURLConnection
            var json = ""

            val stringBuilder = StringBuilder()

            try {
                //var urldeconexao = URL(url[0])

                //conexao = urldeconexao.openConnection() as HttpURLConnection

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

                //writer.write(data)
                writer.write(url[1])
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
                //while ((line = bufferedReader.readLine()) != null) {
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

            var teste : String = json

            return json
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            handleJsonGET(result)
        }

    }

    inner class AsyncTaskHandleJsonGET : AsyncTask<String,String,String>() {
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

    }

    inner class AsyncTaskHandleJsonPOST : AsyncTask<String,String,String>() {
        override fun doInBackground(vararg url: String?): String {

            /*var text: String
            val connection = URL(url[0]).openConnection() as HttpURLConnection

            try{
                connection.connect()
                text = connection.inputStream.use { it.reader().use{reader -> reader.readText()} }
            } finally {
                connection.disconnect()
            }

            return text*/

            var numero : String = "5"

            //var jsondata : String = JSONObject(numero).toString()
            //val obj = JSONObject(numero)

            return EnviaDadosParaOServidor("","",numero)
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            //handleJsonGET(result)
            handleJsonPOST(result)
        }

    }

    private fun handleJsonGET(jsonString: String?) {

        //val teste = JSONObject(jsonString)

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

    private fun handleJsonPOST(jsonString: String?) {

        val teste = jsonString

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

    //Envia dados em formato JSON para servidor remoto
    fun EnviaDadosParaOServidor(urldometodo: String, method: String, data: String): String {

        var method: String = "teste/testepost/"

        //var url: URL? = null
        var url: URL? = URL("http://localhost:52522/" + method)
        var conexao: HttpURLConnection? = null
        var json = ""

        val stringBuilder = StringBuilder()

        try {
            url = URL(urldometodo)

            conexao = url.openConnection() as HttpURLConnection

            conexao.readTimeout = 15000
            conexao.connectTimeout = 15000
            conexao.requestMethod = "POST"
            conexao.setRequestProperty("Connection", "keep-alive")
            conexao.setRequestProperty("Content-Type", "application/json")
            conexao.setRequestProperty("Accept", "application/json")
            conexao.setRequestProperty("Accept-Encoding", "gzip")

            conexao.doInput = true
            conexao.doOutput = true

            val outputStream = BufferedOutputStream(conexao.outputStream)
            val writer = BufferedWriter(OutputStreamWriter(outputStream, "utf-8"))

            writer.write(data)
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
            //while ((line = bufferedReader.readLine()) != null) {
            while ((line == bufferedReader.readLine()) != null) {
                stringBuilder.append(line!! + "\n")
            }

            json = stringBuilder.toString()

        } catch (e: NullPointerException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            conexao!!.disconnect()
        }

        Log.d("String JSON: ", json)

        return json
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
