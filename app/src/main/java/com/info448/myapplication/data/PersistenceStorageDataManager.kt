package com.info448.myapplication.data

import android.content.Context

class PersistenceStorageDataManager(val context: Context) {

    fun writeFile(str: String) {
        context.filesDir // .....
    }
}