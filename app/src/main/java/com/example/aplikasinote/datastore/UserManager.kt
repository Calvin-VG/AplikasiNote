package com.example.aplikasinote.datastore

import android.content.Context
import androidx.datastore.DataStore
import androidx.datastore.preferences.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserManager(context: Context) {

    private val dataStore : DataStore<Preferences> = context.createDataStore("user_prefs")

    companion object{
        val USERNAME = preferencesKey<String>("USER_USERNAME")
        val EMAIL = preferencesKey<String>("USER_EMAIL")
        val PASSWORD = preferencesKey<String>("USER_PASSWORD")
    }

    suspend fun saveData(username: String, email: String, password: String){
        dataStore.edit{
            it [USERNAME] = username
            it [EMAIL] = email
            it [PASSWORD] = password
        }
    }

    val userUsername : Flow<String> = dataStore.data.map {
        it[USERNAME] ?: ""
    }

    val userEmail : Flow<String> = dataStore.data.map {
        it[EMAIL] ?: ""
    }

    val userPassword : Flow<String> = dataStore.data.map {
        it[PASSWORD] ?: ""
    }

    //    Hapus data dari datasore
    suspend fun clearData(){
        dataStore.edit{
            it.clear()
        }
    }

}