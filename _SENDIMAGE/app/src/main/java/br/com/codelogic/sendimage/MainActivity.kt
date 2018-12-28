package br.com.codelogic.sendimage

import android.app.Activity
import android.app.PendingIntent.getActivity
import android.content.Intent
import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.activity_main.*
import java.io.*
import java.util.*

import android.os.AsyncTask

import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONObject

import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONException
import java.io.*
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets

class MainActivity : AppCompatActivity() {

    private var btn: Button? = null
    private var btnSend: Button? = null
    private var imageview: ImageView? = null
    private val GALLERY = 1
    private val CAMERA = 2
    private var cb : CheckBox? = null

    override fun onCreate(savedInstanceState:Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn = findViewById<View>(R.id.btn) as Button
        imageview = findViewById<View>(R.id.iv) as ImageView

        btnSend = findViewById<View>(R.id.btnSend) as Button

        btnSend!!.setOnClickListener { EnviaImagemParaOServidor() }

        btn!!.setOnClickListener { showPictureDialog() }

        cb = findViewById (R.id.cbFoto) as CheckBox
        cb?.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { compoundButton, isChecked ->
            if(isChecked){
                VerificaExistenciaDeImagensEmDiretorioParaControleDeCheckbox();
            }
            else{
                ExcluiArquivoDeDiretorioPorChecboxDesmarcado();
            }
        })

        /*cb?.setOnCheckedChangeListener { buttonView, isChecked ->
            val msg = "You have " + (if (isChecked) "checked" else "unchecked") + " this Check it Checkbox."
            //Toast.makeText(this@MainActivity, msg, Toast.LENGTH_SHORT).show()
            //VerificaExistenciaDeImagensEmDiretorioParaControleDeCheckbox()
        }*/

        VerificaExistenciaDeImagensEmDiretorioParaControleDeCheckbox()
    }

    fun EnviaImagemParaOServidor(){
        val termos = arrayOfNulls<String>(2)

        //var url = "http://mysafeinfo.com/api/data?list=president&format=json"
        //var url = "http://codelogic.com.br/presidentslist.json"
        //var url = "http://localhost:52522/teste/testepost"
        //termos[0] = "http://localhost:52522/teste/testepost"
        termos[0] = "http://192.168.103.7:52522/teste/testepostimage"
        //termos[0] = "http://192.168.1.21:10049/teste/testepost"
        //termos[0] = "http://192.168.1.21:10049/teste/create"
        //termos[0] = "http://192.168.103.7:52522/teste/create"
        //termos[0] = "http://192.168.104.33:52522/teste/testepost"
        //termos[0] = "http://10.0.2.2:52522/teste/testepost"

        var ot = TesteObjeto(ConverterParaStringBase64())
        var otlist: MutableList<TesteObjeto> = mutableListOf(ot)

        termos[1] = GerarJSON(otlist)


        AsyncTaskHandleJsonPOST2().execute(termos[0],termos[1]);
        //AsyncTaskHandleJsonGET().execute(termos[0],termos[1]);
        //AsyncTaskHandleJsonGET().execute(url);
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

    private fun showPictureDialog() {
        val pictureDialog = AlertDialog.Builder(this)
        pictureDialog.setTitle("Select Action")
        val pictureDialogItems = arrayOf("Select photo from gallery", "Capture photo from camera")
        pictureDialog.setItems(pictureDialogItems
        ) { dialog, which ->
            when (which) {
                0 -> choosePhotoFromGallary()
                1 -> takePhotoFromCamera()
            }
        }
        pictureDialog.show()
    }

    fun choosePhotoFromGallary() {

        val galleryIntent : Intent = Intent(Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

        // Adicionado
        //galleryIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(GetImageOnStoreDir()));

        startActivityForResult(galleryIntent, GALLERY)
    }

    private fun takePhotoFromCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        //intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(GetImageFromBase64()));

        startActivityForResult(intent, CAMERA)
    }

    public override fun onActivityResult(requestCode:Int, resultCode:Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)
        /* if (resultCode == this.RESULT_CANCELED)
         {
         return
         }*/

        if (requestCode == GALLERY)
        {
            if (data != null)
            {
                val contentURI = data!!.data
                try
                {
                    //val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, contentURI)
                    val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, contentURI)

                    val path = saveImage(bitmap)
                    Toast.makeText(this@MainActivity, "Image Saved!", Toast.LENGTH_SHORT).show()
                    imageview!!.setImageBitmap(bitmap)
                }
                catch (e: IOException) {
                    e.printStackTrace()
                    Toast.makeText(this@MainActivity, "Failed!", Toast.LENGTH_SHORT).show()
                }

            }

        }
        else if (requestCode == CAMERA)
        {
            //VerificaExistenciaDeImagensEmDiretorioParaControleDeCheckbox()

            val thumbnail = data!!.extras!!.get("data") as Bitmap
            imageview!!.setImageBitmap(thumbnail)
            saveImage(thumbnail)
            Toast.makeText(this@MainActivity, "Image Saved!", Toast.LENGTH_SHORT).show()
        }
    }

    fun saveImage(myBitmap: Bitmap):String {

        val bytes = ByteArrayOutputStream()
        //myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes)
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)


        val wallpaperDirectory : File = GetStoreDir()

        // have the object build the directory structure, if needed.
        Log.d("fee",wallpaperDirectory.toString())

        if (!wallpaperDirectory.exists())
        {
            wallpaperDirectory.mkdirs()
        }

        try
        {
            Log.d("heel",wallpaperDirectory.toString())
            //val f = File(wallpaperDirectory, ((Calendar.getInstance().getTimeInMillis()).toString() + ".jpg"))
            val file : File = File(wallpaperDirectory, ("teste.jpg"))

            file.createNewFile()

            val fileOutputStream : FileOutputStream = FileOutputStream(file)

            fileOutputStream.write(bytes.toByteArray())

            MediaScannerConnection.scanFile(this, arrayOf(file.getPath()), arrayOf("image/jpeg"), null)

            fileOutputStream.close()

            Log.d("TAG", "File Saved::--->" + file.getAbsolutePath())

            return file.getAbsolutePath()
        }
        catch (e1: IOException) {
            e1.printStackTrace()
        }
        finally {
            VerificaExistenciaDeImagensEmDiretorioParaControleDeCheckbox()
        }

        return ""
    }

    fun VerificaExistenciaDeImagensEmDiretorioParaControleDeCheckbox() {

        val storageDir : File = GetStoreDir()

        val descricaoFoto = "teste.jpg"

        val imageDirFoto = File(storageDir, descricaoFoto)

        if (imageDirFoto.exists()) {
            cb!!.setChecked(true)
        }
    }

    fun ExcluiArquivoDeDiretorioPorChecboxDesmarcado() {

        val storageDir : File = GetStoreDir()

        val descricaoFoto = "teste.jpg"

        val imageDirFoto = File(storageDir, descricaoFoto)

        if (imageDirFoto.exists() && !cb!!.isChecked) {
            imageDirFoto.delete()
            Toast.makeText(this@MainActivity, "Imagem apagada com sucesso!", Toast.LENGTH_LONG).show()
        }
    }

    fun GetStoreDir() : File {

        val storageDir = File((Environment.getExternalStorageDirectory()).toString() + IMAGE_DIRECTORY)

        return storageDir
    }

    fun GetImageOnStoreDir() : File {

        val storageDir = GetStoreDir()

        val descricaoFoto : String = "teste.jpg"

        val imagem : File = File(storageDir, descricaoFoto)

        //imagem

        return imagem
    }

    fun GetImageFromBase64() : File {

        val storageDir = GetStoreDir()

        val descricaoFoto = "teste.jpg"

        val imageDirFoto = File(storageDir, descricaoFoto)

        return imageDirFoto
    }

    fun encoder(filePath: String): String{
        val bytes = File(filePath).readBytes()
        //val base64 = Base64.getEncoder().encodeToString(bytes)
        val base64 = android.util.Base64.encodeToString(bytes, 0)
        return base64
    }

    /*fun decoder(base64Str: String, pathFile: String): Unit{
        val imageByteArray = Base64.getDecoder().decode(base64Str)
        File(pathFile).writeBytes(imageByteArray)
    }*/

    fun ConverterParaStringBase64() : String
    {
        val arquivo = GetImageFromBase64()
        val bytes : ByteArray = arquivo.readBytes()

        val base64 = android.util.Base64.encodeToString(bytes, 0)
        return base64
    }

    private fun handleJsonGET(jsonString: String?) {

        val teste = JSONObject(jsonString)

        //val jsonArray = JSONArray(jsonString)

        /*val list = ArrayList<President>()

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
        presidents_list.adapter = adapter*/
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

    companion object {
        private val IMAGE_DIRECTORY = "/demonuts"
    }
}
