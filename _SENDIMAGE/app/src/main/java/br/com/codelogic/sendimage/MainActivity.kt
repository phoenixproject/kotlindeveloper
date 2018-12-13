package br.com.codelogic.sendimage

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.CompoundButton
import android.R
import android.widget.CheckBox
import java.io.File
import android.widget.Toast
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    //Variável para armazenar a constante de captura de midia
    private val CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1

    //Objeto responsável para armazenar o diretório de onde as imagens serão guardadas
    private val fabricaConcretaStoragedir = FabricaConcretaStorageDir()

    //Guarda o diretório de imagem utilizado
    private var imageDir: File = fabricaConcretaStoragedir.getAlbumStorageDir(fabricaConcretaStoragedir.CAMERA_DIR)

    //Este valor também será utilizado para referenciar as fotos
    private var data_levantamento = ""

    //private var cbFoto1 = findViewById(R.id.coletor_btFoto1Id) as CheckBox
    //private var cbFoto2 = findViewById(R.id.coletor_btFoto2Id) as CheckBox

    private val coletorController =  ColetorController()

    //private var btnFoto: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Insere uma nova data de levantamento no registro
        val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
        data_levantamento = simpleDateFormat.format(Date())
        VerificaExistenciaDeImagensEmDiretorioParaControleDeCheckbox()

        //Captura o checkbox de primeira foto e configura conforme existência de imagem
        /*val cbFoto1 = findViewById(R.id.coletor_btFoto1Id) as CheckBox
        cbFoto1.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                VerificaExistenciaDeImagensEmDiretorioParaControleDeCheckbox()
            } else {
                ExcluiArquivoDeDiretorioPorChecboxDesmarcado()
            }
        })

        //Captura o checkbox de segunda foto e configura conforme existência de imagem
        val cbFoto2 = findViewById (R.id.coletor_cbFoto2Id) as CheckBox
        cbFoto2.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                VerificaExistenciaDeImagensEmDiretorioParaControleDeCheckbox()
            } else {
                ExcluiArquivoDeDiretorioPorChecboxDesmarcado()
            }
        })

        val btnFoto = findViewById<View>(R.id.coletor_btnFotoId) as Button
        btnFoto.setOnClickListener(object : Button.OnClickListener {
            override fun onClick(v: View) {
                takePhoto()
            }
        })*/

    }

    fun criaArquivoParaDiretorio(dataemformatostring: String): File {

        //File storageDir = new File(Environment.getExternalStorageDirectory() + "/DCIM/Coletor");
        //File storageDir = new File(coletorController.getDiretorioimagem());
        val storageDir = File(fabricaConcretaStoragedir.getAlbumStorageDir())

        //1º - Verifica se diretório existe
        //se diretório onde ficarão as fotos do Coletor não existe
        if (!storageDir.exists()) {
            //Se não existir cria o diretório
            storageDir.mkdirs()
        }

        //O diretório só suporta duas imagens de cada registro
        //Abaixo, o nome do arquivo recebe a data em formato de string e o identificador 1
        var nomearquivo =
            coletorController.ConverteDataParaFormatoStringSemCaracteresEspeciais(dataemformatostring) + "_1" + ".jpg"

        //O arquivo do diretório
        var imageDir = File(storageDir, nomearquivo)

        //2º - Verifica se dos arquivos existem
        //Se o diretório com o arquivo número 1 já existe
        if (imageDir.exists()) {

            //Se existe, o nome do arquivo recebe a identificação do arquivo número 2
            nomearquivo = coletorController.ConverteDataParaFormatoStringSemCaracteresEspeciais(dataemformatostring) +
                    "_2" + ".jpg"
            imageDir = File(storageDir, nomearquivo)

            //Se o arquivo número 2 também existir no diretório
            if (imageDir.exists()) {
                //A identificação do diretório recebe uma descrição inválida
                imageDir = File("Diretorio cheio")
            }
        }

        return imageDir
    }

    protected fun VerificaExistenciaDeImagensEmDiretorioParaControleDeCheckbox() {

        val storageDir = File(coletorController.getDiretorioimagem())

        val descricaoFoto1 =
            coletorController.ConverteDataParaFormatoStringSemCaracteresEspeciais(this.data_levantamento.toString()) + "_1" + ".jpg"
        val descricaoFoto2 =
            coletorController.ConverteDataParaFormatoStringSemCaracteresEspeciais(this.data_levantamento.toString()) + "_2" + ".jpg"

        val imageDirFoto1 = File(storageDir, descricaoFoto1)
        val imageDirFoto2 = File(storageDir, descricaoFoto2)

        /*if (imageDirFoto1.exists()) {
            //cbFoto1.setChecked(!cbFoto1.isChecked());
            cbFoto1.setChecked(true)
        }

        if (imageDirFoto2.exists()) {
            cbFoto2.setChecked(true)
        }*/
    }

    protected fun ExcluiArquivoDeDiretorioPorChecboxDesmarcado() {

        val storageDir = File(coletorController.getDiretorioimagem())

        val descricaoFoto1 =
            coletorController.ConverteDataParaFormatoStringSemCaracteresEspeciais(this.data_levantamento.toString()) + "_1" + ".jpg"
        val descricaoFoto2 =
            coletorController.ConverteDataParaFormatoStringSemCaracteresEspeciais(this.data_levantamento.toString()) + "_2" + ".jpg"

        val imageDirFoto1 = File(storageDir, descricaoFoto1)
        val imageDirFoto2 = File(storageDir, descricaoFoto2)

        /*if (imageDirFoto1.exists() && !cbFoto1.isChecked()) {
            imageDirFoto1.delete()
            Toast.makeText(this, "Imagem 1 apagada com sucesso!", Toast.LENGTH_LONG).show()
        }

        if (imageDirFoto2.exists() && !cbFoto2.isChecked()) {
            imageDirFoto2.delete()
            Toast.makeText(this, "Imagem 2 apagada com sucesso!", Toast.LENGTH_LONG).show()
        }*/
    }

    protected fun takePhoto() {

        imageDir = criaArquivoParaDiretorio(this.data_levantamento.toString())

        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(this.imageDir))

        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (resultCode == Activity.RESULT_OK) {

            when (requestCode) {

                CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE ->

                    if (imageDir.exists()) {

                        VerificaExistenciaDeImagensEmDiretorioParaControleDeCheckbox()

                        Toast.makeText(this, "Imagem salva com sucesso!", Toast.LENGTH_LONG).show()

                    } else {

                        Toast.makeText(this, "Não foi possível salvar a imagem!", Toast.LENGTH_LONG).show()
                    }

                else -> {
                }
            }
        }

        super.onActivityResult(requestCode, resultCode, data)
    }
}
