package br.com.codelogic.sendimage

import android.util.Log
import org.json.JSONObject
import java.io.*


class ArquivoController {

    //Objeto responsável para armazenar o diretório de onde as imagens serão guardadas
    private val fabricaConcretaStoragedir = FabricaConcretaStorageDir()

    val nometabela: String?
        get() = null

    val diretorioimagem: String?
        get() = null

    fun SincronizaDados() {

    }

    fun ConverteDataParaFormatoStringSemCaracteresEspeciais(data: String): String? {
        return null
    }

    fun VerificaSeExistemAteDuasImagensEmUmDiretorioParaDeterminadaData(data: String): Boolean {
        return false
    }

    fun EliminaImagemDeDiretorio(nomeimagem: String): Boolean {
        return false
    }

    fun TratamentoDeRespostaDeServidor(dados: String) {

    }

    /**
     * Escreve no arquivo texto.
     *
     * @param texto Texto a ser escrito.
     * @return True se o texto foi escrito com sucesso.
     */
    fun GravarArquivoTexto(texto: String): Boolean {
        try {
            //File file = new File(context.getExternalFilesDir(null),"dados.txt");
            val file = File(fabricaConcretaStoragedir.getAlbumStorageDir(), "dados.txt")
            val out = FileOutputStream(file, true)
            out.write(texto.toByteArray())
            out.write("\n".toByteArray())
            out.flush()
            out.close()
            return true

        } catch (e: Exception) {
            Log.e("Falha ao gravar arquivo", e.toString())
            return false
        }
    }

    fun LerArquivoTexto(): String {

        var backup = ""

        try {
            // AQUI É O BASICO PARA LER O ARQUIVO
            val fReader = FileReader(fabricaConcretaStoragedir.getAlbumStorageDir() + "/backup.txt")
            val bReader = BufferedReader(fReader)

            // AQUI LEIO O CONTEUDO DO ARQUIVO E GUARDO NA VARIAVEL conteudo
            while (bReader.ready()) {
                backup += bReader.readLine() + "\n"
            }

            //System.out.println(conteudo);
            bReader.close()
        } catch (e: FileNotFoundException) {
            Log.e("Arquivo não existe", e.toString())
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return backup

    }
}