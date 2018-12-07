package com.nunioz_code.android_ormlite_exemplo.view

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.nunioz_code.android_ormlite_exemplo.R
import com.nunioz_code.android_ormlite_exemplo.model.Car

/**
 * Created by edvaldo on 21/03/18.
 */
class CarAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder> {

    var listCar: List<Car> = ArrayList()

    constructor() {
        this.listCar = ArrayList()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_car, parent, false)
        return ViewCar(view)
    }

    override fun getItemCount(): Int {
        return listCar.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val car = listCar.get(position)

        (holder as ViewCar).marca!!.setText("" + car.marca)
        (holder as ViewCar).modelo!!.setText("" + car.modelo)
        (holder as ViewCar).versao!!.setText("" + car.versao)
    }

    class ViewCar(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var marca: TextView? = null
        var modelo: TextView? = null
        var versao: TextView? = null

        init {
            marca = itemView.findViewById(R.id.tvMarca)
            modelo = itemView.findViewById(R.id.tvModelo)
            versao = itemView.findViewById(R.id.tvVersao)
        }

    }

    fun addItens(list: List<Car>) {
        listCar = list
        notifyDataSetChanged()
    }
}