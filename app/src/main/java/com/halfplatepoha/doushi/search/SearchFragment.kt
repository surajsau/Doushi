package com.halfplatepoha.doushi.search

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.halfplatepoha.doushi.*
import com.halfplatepoha.doushi.base.BaseFragment
import com.jakewharton.rxbinding3.widget.afterTextChangeEvents
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_search.*
import org.kodein.di.Kodein
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.*

class SearchFragment: BaseFragment(), SearchResultItemClickListener, ModeSelectionDialogItemClickListener {

    companion object {
        const val ACTION_OPEN_CHOOSE_MODE_DIALOG = 100
    }

    private val parentKodein: Kodein by kodein()

    override val kodein = Kodein.lazy {
        extend(parentKodein)
        bind<SearchResultItemClickListener>() with instance(this@SearchFragment)
        bind<IntentProvider>() with provider { IntentProvider(this@SearchFragment.context) }
        bind<SearchAdapter>() with provider { SearchAdapter(instance()) }
    }

    override val layoutId = R.layout.fragment_search

    private val viewModel: SearchViewModel by viewModel()

    private var editTextChangeDisposable: Disposable? = null

    private val adapter: SearchAdapter by instance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rlSearchResults.layoutManager = LinearLayoutManager(context)
        rlSearchResults.adapter = adapter

        btnMode.setOnClickListener { viewModel.clickMode() }

        viewModel.onViewCreated()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.searchResult.observe(this, Observer { adapter.setItems(it) })

        viewModel.modeText.observe(this, Observer { btnMode.text = it })

        viewModel.action.observe(this, Observer {
            when(it) {
                ACTION_OPEN_CHOOSE_MODE_DIALOG -> {
                    ModeSelectionDialog().apply { this.listener = this@SearchFragment }.show(childFragmentManager, "mode_select")
                }
            }
        })

        viewModel.loaderVisibilityState.observe(this, Observer {
            if(it) {
                searchProgress.visibility = View.VISIBLE
            } else {
                searchProgress.visibility = View.GONE
            }
        })

        viewModel.intent.observe(this, Observer { launchActivity(it.targetClass, it.intentMap) })

        editTextChangeDisposable = etSearch.afterTextChangeEvents()
            .subscribe({viewModel.onSearchTextChange(it.text())}, {it.printStackTrace()})
    }

    override fun onDestroyView() {
        editTextChangeDisposable?.let {
            if(!it.isDisposed)
                it.dispose()
        }
        super.onDestroyView()
    }

    override fun onItemClick(verb: String?) {
        viewModel.onItemClick(verb)
    }

    override fun onModeSelected(option: String) {
        viewModel.onModeSelected(option)
    }

}