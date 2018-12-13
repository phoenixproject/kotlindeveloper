package br.com.codelogic.sendimage

import java.util.*

interface DadoColetor {

    var stop_code: String

    var stop_lat: Double

    var stop_lon: Double

    var logradouro: String

    var numero_imovel: String

    var largura_calcada: Double

    var dt_levantamento: String

    var dt_alteracao: String

    var placaSinalizacao: Dado

    var abrigo: Dado

    var tipoFixacao: Dado

    var equipamento: Dado

    var usuario: Dado

    var localParada: Dado

    var bairro: Dado

    var subreferenciaPonto: Dado

    var ds_referencia: String

    var substituicaoAbrigo: Dado

    var status: Dado

    var instalacaoNova: Dado

    var observacao: String

    var ds_referenciainstalacao: String

    var precisao: Double
    fun setDt_levantamento(dt_levantamento: Date)
    fun setDt_alteracao(dt_alteracao: Date)
}