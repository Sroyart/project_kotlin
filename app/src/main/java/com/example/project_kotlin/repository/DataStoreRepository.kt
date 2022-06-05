package com.example.project_kotlin.repository

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.createDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import okio.IOException

const val PREFERENCE_NAME = "my_preference"

class DataStoreRepository(context: Context) {

    private object PreferencesKeys {
        val name = preferencesKey<String>("my_name")
    }

    private val dataStore: DataStore<Preferences> = context.createDataStore(
        name = PREFERENCE_NAME
    )

    suspend fun saveToDataStore(name: String) {
        dataStore.edit { preference ->
            preference[PreferencesKeys.name] = name
        }
    }

    val readFromDataStore: Flow<String> = dataStore.data.catch { exception ->
        if (exception is IOException) {
            Log.d("DataStore", exception.message.toString())
            emit(emptyPreferences())
        } else {
            throw exception
        }
    }.map { preference ->
        val emailConn: String = preference[PreferencesKeys.name] ?: "none"
        emailConn
    }

    suspend fun deleteDataStore() {
        dataStore.edit {
            it.clear()
        }
    }

    val userEmailStore: Flow<String> = dataStore.data.map {
        it[PreferencesKeys.name] ?: "none"
    }
}