package com.halfplatepoha.doushi.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.halfplatepoha.doushi.R
import com.halfplatepoha.doushi.base.BaseActivity
import com.halfplatepoha.doushi.search.SearchViewModel
import com.halfplatepoha.doushi.viewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider

class MainActivity : BaseActivity() {

    private val viewModel: MainViewModel by viewModel()

    private lateinit var navigationController: NavController

    override val layoutId: Int = R.layout.activity_main

    override val kodein : Kodein by kodein()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        navigationController = Navigation.findNavController(this, R.id.navHostFragment)
        navigationView.setupWithNavController(navigationController)
    }
}
