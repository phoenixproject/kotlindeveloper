package br.com.luizmarcus.exemploroom

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

@Dao
interface CarChildDao {

    @Insert
    fun insert(car: CarChild)

    @Query("SELECT MAX(id) FROM CarChild")
    fun findLastCarId (): Long

    @Query("SELECT * from CarChild ORDER BY name ASC")
    fun getAllCar(): List<CarChild>

    @Query("DELETE from CarChild")
    fun deleteAll()
}