package br.com.codelogic.sendimage

import android.view.View
import org.json.JSONObject


interface Controller {

    val nometabela: String
    val diretorioimagem: String

    fun Inserir(dado: Dado): Boolean
    fun Atualizar(dado: Dado): Boolean
    fun Salvar(dado: Dado): Boolean
    fun Excluir(dado: Dado): Boolean
    fun ExcluirPorId(id: Int): Boolean
    fun ExcluirDadosSemDescricao()
    fun TrazerDadoPorDataDeLevantamentoESemDescricao(dt_levantamento: String): Dado
    fun TrazerTodosOsDados(): List<Dado>
    fun TrazerDadosComDescricao(): List<Dado>
    fun TrazerDadosSemDescricao(): List<Dado>
    fun TrazerDadoPorId(id: Int): Dado
    fun TrazerDadoPorDescricao(descricao: String): Dado
    fun TrazerDadosPorDescricao(descricao: String): List<Dado>
    fun TrazerDadosPorDadoAbstrato(dado: Dado): List<Dado>

    fun fecharConexao()

    fun RetornaPosicaoDeElementoDeLista(listadado: List<Dado>, idelemento: Int): Int
    fun AutoIncrementReset(nometabela: String): Boolean

    fun EnviarJson()
    fun ReceberJson(view: View)
    fun GerarJSON(listadedados: List<Dado>): String
    fun GerarJSON(arraydebytes: ByteArray): String
    fun ConverterJSONParaObjeto(data: String): List<Dado>
    fun ConverterStringArrayJSONParaObjetoJson(data: String): JSONObject
    fun ConverterJSONParaObjeto(data: JSONObject): List<Dado>

    fun GravaDadosDeServidorRemoto(listadados: List<Dado>): Boolean
    fun FazerChamadaAoServidor(method: String, jsondata: String, controller: String, action: String)

    fun ConverteNumeroComVirgulaParaNumeroComPonto(numero: String): String

    fun ConverteDataParaFormatoStringSemCaracteresEspeciais(data: String): String
    fun VerificaSeExistemAteDuasImagensEmUmDiretorioParaDeterminadaData(data: String): Boolean
    fun EliminaImagemDeDiretorio(nomeimagem: String): Boolean

    fun TratamentoDeRespostaDeServidor(dados: String)

    fun GravarArquivoTexto(texto: String): Boolean
    fun LerArquivoTexto(): String
}