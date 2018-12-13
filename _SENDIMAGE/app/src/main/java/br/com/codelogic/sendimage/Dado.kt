package br.com.codelogic.sendimage

interface Dado : DadoColetor {

    var codigo: Int?
    var descricao: String
    var dadoAbstrato: Dado

    //Exclusivo para Usuário
    var login: String
    var senha: String
    override var status: Dado
    var tipoUsuario: Dado
}