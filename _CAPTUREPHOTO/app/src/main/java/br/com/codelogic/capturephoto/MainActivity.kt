package br.com.codelogic.capturephoto

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
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*

class MainActivity : AppCompatActivity() {

    private var btn: Button? = null
    private var imageview: ImageView? = null
    private val GALLERY = 1
    private val CAMERA = 2
    private var cb : CheckBox? = null

    override fun onCreate(savedInstanceState:Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn = findViewById<View>(R.id.btn) as Button
        imageview = findViewById<View>(R.id.iv) as ImageView

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
        val galleryIntent = Intent(Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

        // Adicionado
        galleryIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(GetStoreDir()));

        startActivityForResult(galleryIntent, GALLERY)
    }

    private fun takePhotoFromCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
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
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes)
        val wallpaperDirectory = File(
            (Environment.getExternalStorageDirectory()).toString() + IMAGE_DIRECTORY)
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
            val f = File(wallpaperDirectory, ("teste.jpg"))
            f.createNewFile()
            val fo = FileOutputStream(f)
            fo.write(bytes.toByteArray())
            MediaScannerConnection.scanFile(this,
                arrayOf(f.getPath()),
                arrayOf("image/jpeg"), null)
            fo.close()
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath())

            return f.getAbsolutePath()
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

        val storageDir = File((Environment.getExternalStorageDirectory()).toString() + IMAGE_DIRECTORY)

        val descricaoFoto = "teste.jpg"

        val imageDirFoto = File(storageDir, descricaoFoto)

        if (imageDirFoto.exists()) {
            cb!!.setChecked(true)
        }
    }

    fun ExcluiArquivoDeDiretorioPorChecboxDesmarcado() {

        val storageDir = File((Environment.getExternalStorageDirectory()).toString() + IMAGE_DIRECTORY)

        val descricaoFoto = "teste.jpg"

        val imageDirFoto = File(storageDir, descricaoFoto)

        if (imageDirFoto.exists() && !cb!!.isChecked) {
            imageDirFoto.delete()
            Toast.makeText(this@MainActivity, "Imagem apagada com sucesso!", Toast.LENGTH_LONG).show()
        }
    }

    fun GetStoreDir() : File {

        val storageDir = File(IMAGE_DIRECTORY)

        val descricaoFoto = "teste.jpg"

        val imageDirFoto = File(storageDir, descricaoFoto)

        return imageDirFoto
    }

    companion object {
        private val IMAGE_DIRECTORY = "/demonuts"
    }
}
