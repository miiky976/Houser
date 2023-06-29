package com.miiky.houser.data.persistent

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class StoreSession(private val context: Context) {
    companion object{
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("email")
        val EMAIL_KEY = stringPreferencesKey("user_email")
//        val USER_KEY = stringPreferencesKey("user")
//        val NAME = stringPreferencesKey("name")

    }
    val getEmail: Flow<String> = context.dataStore.data
        .map {
            it[EMAIL_KEY]?: ""
        }
    suspend fun saveSession(name: String){
        context.dataStore.edit {
            it[EMAIL_KEY] = name
        }
    }
    suspend fun endSession(){
        context.dataStore.edit {
            it[EMAIL_KEY] = ""
        }
    }
}