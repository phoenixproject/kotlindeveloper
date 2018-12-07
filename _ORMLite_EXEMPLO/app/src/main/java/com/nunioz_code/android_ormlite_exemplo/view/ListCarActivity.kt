package com.nunioz_code.android_ormlite_exemplo.view

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.nunioz_code.android_ormlite_exemplo.R
import com.nunioz_code.android_ormlite_exemplo.model.DAOCar
import com.nunioz_code.android_ormlite_exemplo.model.DataBaseHelper
import kotlinx.android.synthetic.main.activity_list_car.*
import java.util.*

class ListCarActivity : AppCompatActivity() {

    var dbHelper: DataBaseHelper = DataBaseHelper(this)
    var dao: DAOCar = DAOCar(dbHelper.connectionSource)
    var carAdapter: CarAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_car)

        carAdapter = CarAdapter()
        recycler.setHasFixedSize(false)
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = carAdapter
        novoCar.setOnClickListener({
            startActivityForResult(
                    Intent(this, CreateCarActivity::class.java), 0
            )
        })
        updateList()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
       updateList()
    }

    fun updateList(){
        val list = dao.queryForAll() //lista todos os dados existentes em uma tabela
        Collections.reverse(list)
        carAdapter!!.addItens(list)
    }
    override fun onDestroy() {
        dbHelper.close()
        super.onDestroy()
    }
}
