package com.example.shoplist.data.repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ShopItemDbModel::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun shopListDao(): ShopListDao

    companion object {
        private var instance: AppDatabase? = null
        private val lock = Any()
        private const val DB_NAME = "shop_item.db"

        fun getInstance(context: Context): AppDatabase {
            instance?.let {
                return it
            }
            synchronized(lock) {
//                Двойная проверка на instance, чтобы при входе у потоков не добавлялись новые значения инстансов
                instance?.let {
                    return it
                }

                val appDatabase = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java, DB_NAME
                ).build()
                instance = appDatabase
                return appDatabase
            }
        }
    }


}