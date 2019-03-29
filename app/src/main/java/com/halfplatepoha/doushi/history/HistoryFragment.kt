package com.halfplatepoha.doushi.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.halfplatepoha.doushi.IntentProvider
import com.halfplatepoha.doushi.R
import com.halfplatepoha.doushi.base.BaseFragment
import com.halfplatepoha.doushi.viewModel
import kotlinx.android.synthetic.main.fragment_history.*
import org.kodein.di.Kodein
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider

class HistoryFragment: BaseFragment(), HistoryItemClickListener {

    companion object {
        const val ACTION_OPEN_SORT_DIALOG = 100
    }

    override val layoutId = R.layout.fragment_history

    private val viewModel:HistoryViewModel by viewModel()

    private val parentKodein: Kodein by kodein()

    private val adapter: HistoryAdapter by instance()

    override val kodein = Kodein.lazy {
        extend(parentKodein)
        bind<HistoryItemClickListener>() with instance(this@HistoryFragment)
        bind<IntentProvider>() with provider { IntentProvider(this@HistoryFragment.context) }
        bind<HistoryAdapter>() with provider { HistoryAdapter(instance()) }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rlHistory.layoutManager = LinearLayoutManager(context)
        rlHistory.adapter = adapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.historyList.observe(this, Observer { adapter.setItems(it.toTypedArray()) })

        viewModel.intent.observe(this, Observer { launchActivity(it.targetClass, it.intentMap) })

        viewModel.list(HistoryViewModel.FILTER_MOST_RECENT)
    }

    override fun onItemClick(verb: String) {
        viewModel.onItemClick(verb)
    }

}