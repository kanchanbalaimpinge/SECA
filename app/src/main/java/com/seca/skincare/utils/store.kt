package com.seca.skincare.utils

import android.content.Context
import androidx.datastore.DataStore
import androidx.datastore.preferences.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

import kotlin.String as String

class store(context: Context) {

    object PreferenceKeys {
        val USER_NAME = preferencesKey<String>("user_name")
        val USER_ID = preferencesKey<Int>("user_id")



    }

    private val dataStore: DataStore<Preferences> = context.createDataStore(
        name = "ui_mode_preference"
    )


    public suspend fun updateUserName(key:String, userName: String) {
        dataStore.edit {
            it[preferencesKey<String>(key)] = userName
        }
    }

    private fun emitStoredValue(key: String): Flow<String> {
        return dataStore.data.catch {
            if (it is IOException) {
                emit(emptyPreferences())
            } else {
                throw it
            }
        }.map {
            it[preferencesKey<String>(key)] ?: "Not Found"
        }
    }
}