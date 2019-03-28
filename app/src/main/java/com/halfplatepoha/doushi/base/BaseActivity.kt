package com.halfplatepoha.doushi.base

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.halfplatepoha.doushi.*
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance

abstract class BaseActivity: AppCompatActivity(), KodeinAware {

    abstract val layoutId: Int

    private val intentProvider: IntentProvider by instance()

    open val actionObserver = Observer<Int> { handleActions(it) }
    /**
     * override this function to handle custom actions
     */
    open fun handleActions(action: Int) {
        when(action) {
            STATE_FINISH -> finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)
    }

    open fun launchActivity(targetClass: String, intentMap: HashMap<String, Any?>? = null) {
            val launchIntent = intentProvider.getIntent(targetClass)
            intentMap?.forEach {
                if(it.value is Int)
                    launchIntent.putExtra(it.key, it.value as Int)
                else if(it.value is Float)
                    launchIntent.putExtra(it.key, it.value as Float)
                else if(it.value is Double)
                    launchIntent.putExtra(it.key, it.value as Double)
                else if(it.value is Boolean)
                    launchIntent.putExtra(it.key, it.value as Boolean)
                else if(it.value is String)
                    launchIntent.putExtra(it.key, it.value as String)
            }
            startActivity(launchIntent)
    }

}