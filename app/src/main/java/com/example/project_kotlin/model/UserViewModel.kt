package com.example.project_kotlin.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.project_kotlin.repository.DataStoreRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = DataStoreRepository(application)

    val readFromDataStore = repository.readFromDataStore.asLiveData()

    fun saveToDataStore(emailConn: String) = viewModelScope.launch(Dispatchers.IO) {
        repository.saveToDataStore(emailConn)
    }

    fun deleteDataStore() = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteDataStore()
    }

}