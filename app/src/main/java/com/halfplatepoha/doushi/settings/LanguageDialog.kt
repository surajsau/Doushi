package com.halfplatepoha.doushi.settings

import android.os.Bundle
import android.view.View
import com.halfplatepoha.doushi.LANGUAGE_ENGLISH
import com.halfplatepoha.doushi.LANGUAGE_JAPANESE
import com.halfplatepoha.doushi.R
import com.halfplatepoha.doushi.base.BaseDialogFragment
import kotlinx.android.synthetic.main.dialog_choose_language.*
import org.kodein.di.Kodein
import org.kodein.di.android.x.kodein

class LanguageDialog: BaseDialogFragment() {

    override val kodein: Kodein by kodein()

    override val layoutId = R.layout.dialog_choose_language

    var languageDialogListener: LanguageDialogListener? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvEnglish.setOnClickListener {
            languageDialogListener?.onLanguageSelected(LANGUAGE_ENGLISH)
            dismiss()
        }

        tvJapanese.setOnClickListener {
            languageDialogListener?.onLanguageSelected(LANGUAGE_JAPANESE)
            dismiss()
        }
    }

}

interface LanguageDialogListener {
    fun onLanguageSelected(language: String)
}