package com.halfplatepoha.doushi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import com.jakewharton.rxbinding3.widget.afterTextChangeEvents
import kotlinx.android.synthetic.main.activity_main.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein

class MainActivity : AppCompatActivity(), KodeinAware {

    override val kodein by kodein()

    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.results.observe(this, Observer {
            for(verb in it) {
                Log.e("RESULTS", verb.firstForm)
            }
        })

        etSearch.afterTextChangeEvents()
            .subscribe({ viewModel.onTextChange(it.editable?.toString() ?: "") }, { it.printStackTrace() })
    }
}
