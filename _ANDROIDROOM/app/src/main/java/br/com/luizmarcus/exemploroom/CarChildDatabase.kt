package br.com.luizmarcus.exemploroom

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

@Database(entities = [CarChild::class], version = 1)
abstract class CarChildDatabase : RoomDatabase(){

    abstract fun carChildDao(): CarChildDao

    companion object {
        private var INSTANCE: CarChildDatabase? = null

        fun getInstance(context: Context): CarChildDatabase? {
            if (INSTANCE == null) {
                synchronized(CarDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                            CarChildDatabase::class.java, "carchild.db")
                            .build()
                }
            }
            return INSTANCE
        }
    }

}