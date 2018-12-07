package com.nunioz_code.android_ormlite_exemplo.model

import com.j256.ormlite.dao.BaseDaoImpl
import com.j256.ormlite.support.ConnectionSource

/**
 * Created by edvaldo on 21/03/18.
 */
class DAOCar : BaseDaoImpl<Car, Int> {
    constructor(connectionSource: ConnectionSource?) : super(Car::class.java){
        setConnectionSource(connectionSource)
        initialize()
    }
}