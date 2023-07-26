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
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("session")
        val EMAIL_KEY = stringPreferencesKey("user_email")
        val USER_KEY = stringPreferencesKey("user")
        val NAME_KEY = stringPreferencesKey("name")
        val LASTNAME_KEY = stringPreferencesKey("lastname")
        val IMAGE_KEY = stringPreferencesKey("image")
        val ID_KEY = stringPreferencesKey("id")
    }
    val getEmail: Flow<String> = context.dataStore.data
        .map {
            it[EMAIL_KEY]?: ""
        }
    val getUser: Flow<String> = context.dataStore.data
        .map {
            it[USER_KEY] ?: ""
        }

    val getName: Flow<String> = context.dataStore.data
        .map {
            it[NAME_KEY] ?: ""
        }

    val getLastname: Flow<String> = context.dataStore.data
        .map {
            it[LASTNAME_KEY] ?: ""
        }

    val getImage: Flow<String> = context.dataStore.data
        .map {
            it[IMAGE_KEY] ?: ""
        }

    val getID: Flow<String> = context.dataStore.data
        .map {
            it[ID_KEY] ?: ""
        }


    suspend fun saveSession(name: String){
        context.dataStore.edit {
            it[EMAIL_KEY] = name
        }
    }
    suspend fun saveUser(user: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_KEY] = user
        }
    }

    suspend fun saveName(name: String) {
        context.dataStore.edit { preferences ->
            preferences[NAME_KEY] = name
        }
    }

    suspend fun saveLastname(lastname: String) {
        context.dataStore.edit { preferences ->
            preferences[LASTNAME_KEY] = lastname
        }
    }

    suspend fun saveImage(image: String) {
        context.dataStore.edit { preferences ->
            preferences[IMAGE_KEY] = image
        }
    }

    suspend fun saveID(id: String) {
        context.dataStore.edit { preferences ->
            preferences[ID_KEY] = id
        }
    }
    suspend fun endSession() {
        context.dataStore.edit { preferences ->
            preferences[EMAIL_KEY] = ""
            preferences[USER_KEY] = ""
            preferences[NAME_KEY] = ""
            preferences[LASTNAME_KEY] = ""
            preferences[IMAGE_KEY] = ""
            preferences[ID_KEY] = ""
        }
    }

}