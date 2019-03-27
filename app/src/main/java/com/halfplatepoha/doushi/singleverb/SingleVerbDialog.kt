package com.halfplatepoha.doushi.singleverb

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.halfplatepoha.doushi.IntentProvider
import com.halfplatepoha.doushi.R
import com.halfplatepoha.doushi.base.BaseDialogFragment
import com.halfplatepoha.doushi.search.SearchAdapter
import com.halfplatepoha.doushi.search.SearchResultItemClickListener
import com.halfplatepoha.doushi.viewModel
import kotlinx.android.synthetic.main.dialog_single_verb.*
import org.kodein.di.Kodein
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider

class SingleVerbDialog: BaseDialogFragment(), SearchResultItemClickListener {

    companion object {
        const val EXTRA_VERB = "verb"
        const val EXTRA_IS_FIRST_VERB = "is_first_verb"
    }

    override val layoutId = R.layout.dialog_single_verb

    private val parentKodein: Kodein by kodein()

    private val viewModel: SingleVerbDialogViewModel by viewModel()

    private val adapter: SearchAdapter by instance()

    override val kodein = Kodein.lazy {
        extend(parentKodein)
        bind<SearchResultItemClickListener>() with instance(this@SingleVerbDialog)
        bind<IntentProvider>() with provider { IntentProvider(this@SingleVerbDialog.context) }
        bind<SearchAdapter>() with provider { SearchAdapter(instance()) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rlSearchResults.layoutManager = LinearLayoutManager(context)
        rlSearchResults.adapter = adapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.searchResult.observe(this, Observer { adapter.setItems(it) })

        viewModel.title.observe(this, Observer { tvTitle.text = it })

        viewModel.loaderVisibilityState.observe(this, Observer { searchProgress.visibility = if(it) View.VISIBLE else View.GONE })

        viewModel.intent.observe(this, Observer { launchActivity(it.targetClass, it.intentMap) })

        arguments?.let { viewModel.searchString(it.getString(EXTRA_VERB), it.getBoolean(EXTRA_IS_FIRST_VERB)) }
    }

    override fun onItemClick(verb: String?) {
        viewModel.onItemClick(verb)
    }

}