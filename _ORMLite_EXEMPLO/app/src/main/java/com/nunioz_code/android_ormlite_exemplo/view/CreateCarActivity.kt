package com.nunioz_code.android_ormlite_exemplo.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.nunioz_code.android_ormlite_exemplo.R
import com.nunioz_code.android_ormlite_exemplo.model.Car
import com.nunioz_code.android_ormlite_exemplo.model.DAOCar
import com.nunioz_code.android_ormlite_exemplo.model.DataBaseHelper
import kotlinx.android.synthetic.main.activity_create_car.*

class CreateCarActivity : AppCompatActivity() {

    var dbHelper: DataBaseHelper = DataBaseHelper(this)
    var dao: DAOCar = DAOCar(dbHelper.connectionSource)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_car)

        idRegistar.setOnClickListener({
            registar()
        })

    }

    fun registar() {
        val car = Car()
        car.marca = idMarca.text.toString()
        car.modelo = idModelo.text.toString()
        car.versao = idVersao.text.toString()
        car.matricula = idMatricula.text.toString()

        val value = dao.create(car) // armazena no banco de dados
        if (value == 1) {
            Toast.makeText(this, "Sucesso", Toast.LENGTH_LONG).show()
            finish()
        } else {
            Toast.makeText(this, "falha no registo", Toast.LENGTH_LONG).show()
        }
    }

    override fun onDestroy() {
        dbHelper.close()
        super.onDestroy()
    }
}
