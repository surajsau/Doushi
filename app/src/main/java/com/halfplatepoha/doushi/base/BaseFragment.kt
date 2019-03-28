package com.halfplatepoha.doushi.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.halfplatepoha.doushi.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.direct
import org.kodein.di.generic.*

abstract class BaseFragment: Fragment(), KodeinAware {

    abstract val layoutId: Int

    private val intentProvider: IntentProvider by instance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutId, container, false)
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