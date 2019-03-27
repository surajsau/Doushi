package com.halfplatepoha.doushi

import android.content.Context
import android.content.Intent
import com.halfplatepoha.doushi.detail.DetailActivity
import java.lang.Exception

class IntentProvider(private val context: Context?) {

    fun getIntent(className: String): Intent {
        when(className) {
            DetailActivity::class.java.simpleName -> return Intent(context, DetailActivity::class.java)
        }
        throw Exception("No Activity Found")
    }

}