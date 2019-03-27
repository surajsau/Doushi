package com.halfplatepoha.doushi.search

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.halfplatepoha.doushi.*
import com.halfplatepoha.doushi.base.BaseFragment
import com.jakewharton.rxbinding3.widget.afterTextChangeEvents
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_search.*
import org.kodein.di.Kodein
import org.kodein.di.android.x.kodein
import org.kodein.di.direct
import org.kodein.di.generic.*

class SearchFragment: BaseFragment(), SearchResultItemClickListener {

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
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.searchResult.observe(this, Observer { adapter.setItems(it) })

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

}