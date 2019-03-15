package br.com.codelogic.androidroom.cdp

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.io.Serializable
import java.util.*

@Entity(tableName = "address")
data class Address(

    @PrimaryKey
    @ColumnInfo(name = "id")
    var uuid: String = "",

    var street: String? = null,

    var number: String? = null,

    var district: String? = null,

    var city: String? = null,

    var state: String? = null,

    var complement: String? = null
) : Serializable