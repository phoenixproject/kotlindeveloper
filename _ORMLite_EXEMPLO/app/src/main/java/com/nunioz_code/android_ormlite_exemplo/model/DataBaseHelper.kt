package com.nunioz_code.android_ormlite_exemplo.model

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper
import com.j256.ormlite.support.ConnectionSource
import com.j256.ormlite.table.TableUtils

/**
 * Created by edvaldo on 21/03/18.
 */
class DataBaseHelper : OrmLiteSqliteOpenHelper {

    companion object {
        private val db = "car.db"
        private val versao = 1
    }
    constructor(context:Context) : super(context,db,null,versao)

    override fun onCreate(database: SQLiteDatabase?, connectionSource: ConnectionSource?) {
        //Cria a tabela
        // O primeiro campo é a conexão e o segundo é a classe que serã persistida no banco de dados
        TableUtils.createTable(connectionSource,Car::class.java)

    }

    override fun onUpgrade(database: SQLiteDatabase?, connectionSource: ConnectionSource?, oldVersion: Int, newVersion: Int) {}

     override fun close() {
        super.close()
    }
}