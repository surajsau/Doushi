package com.halfplatepoha.doushi.settings

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.halfplatepoha.doushi.*
import com.halfplatepoha.doushi.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_settings.*
import org.kodein.di.Kodein
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import java.util.*

class SettingsFragment: BaseFragment(), LanguageDialogListener {

    companion object {
        const val ACTION_OPEN_LANGUAGE_DIALOG = 100
        const val ACTION_OPEN_FEEDBACK_DIALOG = 101
    }

    private val parentKodein: Kodein by kodein()

    override val kodein = Kodein.lazy {
        extend(parentKodein)
    }

    override val layoutId = R.layout.fragment_settings

    private val viewModel:SettingsFragmentViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.onViewCreated()

        tvVersion.text = "動詞 v${BuildConfig.VERSION_NAME}"

        tvFeedbackTitle.setOnClickListener { viewModel.clickFeedback() }

        tvAboutTitle.setOnClickListener { viewModel.clickAbout() }

        tvLanguagePreference.setOnClickListener { viewModel.clickPreference() }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.action.observe(this, Observer {
            when(it) {
                ACTION_OPEN_LANGUAGE_DIALOG -> LanguageDialog()
                    .apply { this.languageDialogListener = this@SettingsFragment }
                    .show(childFragmentManager, "language_dialog")

                ACTION_OPEN_FEEDBACK_DIALOG -> FeedbackDialog().show(childFragmentManager, "feedback_dialog")
            }
        })

        viewModel.languagePreference.observe(this, Observer {
            when(it) {
                LANGUAGE_JAPANESE -> {
                    tvLanguagePreference.text = getString(R.string.lang_jp)
                }
                LANGUAGE_ENGLISH -> {
                    tvLanguagePreference.text = getString(R.string.lang_eng)
                }
            }
        })
    }

    override fun onLanguageSelected(language: String) {
        viewModel.onLanguageSelected(language)
    }

}