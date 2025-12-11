package com.example.myapplication001

import android.app.Application
import com.example.myapplication001.data.local.AppDatabase
import com.example.myapplication001.data.repository.MuseumRepository
import com.example.myapplication001.data.repository.UserDataRepository

class MyApplication : Application() {
    // Manual Dependency Injection
    val database by lazy { AppDatabase.getDatabase(this) }
    
    val museumRepository by lazy { 
        MuseumRepository(database.museumDao(), database.eventDao()) 
    }
    
    val userDataRepository by lazy { 
        UserDataRepository(database.tripDao(), database.photoDao()) 
    }
}
