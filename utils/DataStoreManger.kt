package com.example.assignment.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.assignment.model.UserDetails
import kotlinx.coroutines.flow.map


const val USER_DATASTORE = "user_data"

val Context.preferenceDataStore : DataStore<Preferences> by preferencesDataStore(name = USER_DATASTORE)

class DataStoreManger(val context: Context) {

    companion object{

        val EMAIL = stringPreferencesKey("EMAIL")
        val PASSWORD = stringPreferencesKey("PASSWORD")
        val MOBILE_NUMBER = stringPreferencesKey("PHONE")
        val NAME = stringPreferencesKey("NAME")
    }

    suspend fun saveToDataStore(userDetails: UserDetails){
        context.preferenceDataStore.edit {
            it[EMAIL] = userDetails.emailAddress
            it[PASSWORD] = userDetails.password
            it[MOBILE_NUMBER] = userDetails.mobileNumber
            it[NAME] = userDetails.name
        }
    }

    fun getFromDataStore() = context.preferenceDataStore.data.map {
        UserDetails(
            emailAddress = it[EMAIL]?:"",
            name = it[NAME]?:"",
            mobileNumber = it[MOBILE_NUMBER]?:""
        )
    }

    suspend fun clearDataStore() = context.preferenceDataStore.edit {
        it.clear()
    }
}