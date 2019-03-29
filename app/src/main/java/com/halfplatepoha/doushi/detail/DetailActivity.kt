package com.halfplatepoha.doushi.detail

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.halfplatepoha.doushi.DetailsFeedbackDialog
import com.halfplatepoha.doushi.R
import com.halfplatepoha.doushi.base.BaseActivity
import com.halfplatepoha.doushi.singleverb.SingleVerbDialog
import com.halfplatepoha.doushi.viewModel
import kotlinx.android.synthetic.main.activity_details.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider

class DetailActivity: BaseActivity(), KodeinAware {

    override val layoutId = R.layout.activity_details

    companion object {
        const val EXTRA_VERB = "extra_verb"
        const val ACTION_OPEN_FEEDBACK_DIALOG = 100
    }

    private val viewModel: DetailViewModel by viewModel()

    private val adapter: MeaningAdapter by instance()

    private val parentKodein: Kodein by kodein()

    override val kodein = Kodein.lazy {
        extend(parentKodein)
        bind<String>(tag = "verb") with provider {this@DetailActivity.intent?.getStringExtra(EXTRA_VERB) ?: ""}
        bind<MeaningAdapter>() with provider { MeaningAdapter(instance(tag = "verb")) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        rlMeanings.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rlMeanings.adapter = adapter

        btnBack.setOnClickListener { viewModel.backClicked() }
        btnSendFeedback.setOnClickListener { viewModel.feedbackClicked() }
        cardFirstVerb.setOnClickListener { viewModel.firstVerbClicked() }
        cardSecondVerb.setOnClickListener { viewModel.secondVerbClicked() }

        viewModel.title.observe(this, Observer { tvTitle.text = it })

        viewModel.transitive.observe(this, Observer { tvTransitive.text = it })

        viewModel.subTitle.observe(this, Observer { tvSubtitle.text = it })

        viewModel.firstVerb.observe(this, Observer {
            tvFirstVerbForm.text = it.form
            tvFirstVerbReading.text = it.reading
        })

        viewModel.secondVerb.observe(this, Observer {
            tvSecondVerbForm.text = it.form
            tvSecondVerbReading.text = it.reading
        })

        viewModel.action.observe(this, actionObserver)

        viewModel.meanings.observe(this, Observer { adapter.setItems(it?.toTypedArray()) })

        viewModel.loaderVisibility.observe(this, Observer {
            if(it) searchProgress.visibility = View.VISIBLE
            else searchProgress.visibility = View.GONE
        })

        viewModel.clickedItem.observe(this, Observer {

            SingleVerbDialog().apply {
                val bundle = Bundle()
                bundle.putString(SingleVerbDialog.EXTRA_VERB, it.verb)
                bundle.putBoolean(SingleVerbDialog.EXTRA_IS_FIRST_VERB, it.isFirstVerb)
                this.arguments = bundle
            }.show(supportFragmentManager, "single_verb")
        })

        intent?.let { viewModel.setVerb(it.getStringExtra(EXTRA_VERB)) }

    }

    override fun handleActions(action: Int) {
        when(action) {
            ACTION_OPEN_FEEDBACK_DIALOG -> DetailsFeedbackDialog.newInstance(viewModel.verbString).show(supportFragmentManager, "feedback_dialog")
        }
        super.handleActions(action)
    }

}