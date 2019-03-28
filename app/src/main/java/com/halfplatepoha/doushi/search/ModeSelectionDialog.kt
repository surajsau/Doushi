package com.halfplatepoha.doushi.search

import android.os.Bundle
import android.view.View
import com.halfplatepoha.doushi.MODE_COMBINED
import com.halfplatepoha.doushi.MODE_FIRST_VERB
import com.halfplatepoha.doushi.MODE_SECOND_VERB
import com.halfplatepoha.doushi.R
import com.halfplatepoha.doushi.base.BaseDialogFragment
import kotlinx.android.synthetic.main.dialog_mode_selection.*
import org.kodein.di.Kodein
import org.kodein.di.android.x.kodein

class ModeSelectionDialog: BaseDialogFragment() {

    override val layoutId = R.layout.dialog_mode_selection

    private val parentKodein: Kodein by kodein()

    var listener: ModeSelectionDialogItemClickListener? = null

    override val kodein = Kodein.lazy {
        extend(parentKodein)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvCombined.setOnClickListener {
            listener?.onModeSelected(MODE_COMBINED)
            dismiss()
        }
        tvFirstVerb.setOnClickListener {
            listener?.onModeSelected(MODE_FIRST_VERB)
            dismiss()
        }
        tvSecondVerb.setOnClickListener {
            listener?.onModeSelected(MODE_SECOND_VERB)
            dismiss()
        }
    }

}

interface ModeSelectionDialogItemClickListener {
    fun onModeSelected(option: String)
}