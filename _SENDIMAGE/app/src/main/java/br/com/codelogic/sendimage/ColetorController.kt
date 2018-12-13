package br.com.codelogic.sendimage

import android.support.v4.view.accessibility.AccessibilityEventCompat.setAction



public class ColetorController {

    //Nome do diretório de imagem
    private var diretorioimagem : String

    private val fabricaConcretaStoragedir = FabricaConcretaStorageDir()

    private val arquivoController = ArquivoController()

    init {
        //fabricaConcretaStoragedir = FabricaConcretaStorageDir()

        diretorioimagem = fabricaConcretaStoragedir.getAlbumStorageDir()

        //arquivoController = ArquivoController()
    }

    fun ConverteDataParaFormatoStringSemCaracteresEspeciais(data: String): String {

        //Formato da data: 01/09/2015 16:09:52

        val datasemespaco = data.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val datasembarra =
            (datasemespaco[0] + datasemespaco[1]).split("/".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val datasemdoispontos =
            (datasembarra[0] + datasembarra[1] + datasembarra[2]).split(":".toRegex()).dropLastWhile { it.isEmpty() }
                .toTypedArray()

        return datasemdoispontos[0] + datasemdoispontos[1] + datasemdoispontos[2]
    }

    fun getDiretorioimagem(): String {
        return diretorioimagem
    }

    fun setDiretorioimagem(diretorioimagem: String) {
        this.diretorioimagem = diretorioimagem
    }

}