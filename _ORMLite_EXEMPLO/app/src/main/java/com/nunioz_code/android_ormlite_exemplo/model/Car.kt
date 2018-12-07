package com.nunioz_code.android_ormlite_exemplo.model

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable

/**
 * Created by edvaldo on 21/03/18.
 */

@DatabaseTable(tableName = "Car")
class Car {
    @DatabaseField(generatedId = true) //Indica que o campo a baixo será o id da tabela
    private var id: Int = 0
    @DatabaseField(columnName = "marca") //Indica que o campo a baixo será uma coluna da tabela
    var marca: String = String()
    @DatabaseField(columnName = "modelo") //Indica que o campo a baixo será uma coluna da tabela
    var modelo: String = String()
    @DatabaseField(columnName = "versao") //Indica que o campo a baixo será uma coluna da tabela
    var versao: String = String()
    @DatabaseField(columnName = "matricula") //Indica que o campo a baixo será uma coluna da tabela
    var matricula: String = String()

    constructor()
    constructor(marca: String, modelo: String, versao: String, matricula: String) {
        this.marca = marca
        this.modelo = modelo
        this.versao = versao
        this.matricula = matricula
    }


}