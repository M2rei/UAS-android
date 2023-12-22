package com.example.uas_android.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.uas_android.NetworkMonitor

@Database(entities = [Movies::class], version = 1, exportSchema = false)
abstract class MoviesRoomDatabase : RoomDatabase(){
    abstract fun moviesDao(): MoviesDao

    companion object {
        private const val DATABASE_NAME = "movie"

        @Volatile
        private var instance: MoviesRoomDatabase? = null
        fun getInstance(context: Context, networkMonitor: NetworkMonitor): MoviesRoomDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context, networkMonitor).also { instance = it }
            }
        }
        private fun buildDatabase(context: Context, networkMonitor: NetworkMonitor): MoviesRoomDatabase {
            val isConnected = networkMonitor.isNetworkAvailable(context)
            val builder = Room.databaseBuilder(
                context.applicationContext,
                MoviesRoomDatabase::class.java,
                DATABASE_NAME
            )

            if (isConnected) {
                builder.fallbackToDestructiveMigration()
            } else {
                builder.fallbackToDestructiveMigrationOnDowngrade()
            }

            return builder.build()
        }
    }

}