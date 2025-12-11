package com.example.myapplication001.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myapplication001.data.local.dao.EventDao
import com.example.myapplication001.data.local.dao.MuseumDao
import com.example.myapplication001.data.local.dao.PhotoDao
import com.example.myapplication001.data.local.dao.TripDao
import com.example.myapplication001.data.local.entity.EventEntity
import com.example.myapplication001.data.local.entity.MuseumEntity
import com.example.myapplication001.data.local.entity.PhotoEntity
import com.example.myapplication001.data.local.entity.TripEntity

@Database(
    entities = [MuseumEntity::class, EventEntity::class, TripEntity::class, PhotoEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun museumDao(): MuseumDao
    abstract fun eventDao(): EventDao
    abstract fun tripDao(): TripDao
    abstract fun photoDao(): PhotoDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
