package com.halfplatepoha.doushi

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.halfplatepoha.doushi.base.BaseDialogFragment
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.dialog_feedback.*
import org.kodein.di.Kodein
import org.kodein.di.android.x.kodein
import android.content.ActivityNotFoundException
import android.net.Uri


class DetailsFeedbackDialog: BaseDialogFragment() {

    companion object {

        const val EXTRA_VERB = "verb"

        @JvmStatic
        fun newInstance(verb: String? = ""): DetailsFeedbackDialog {
            val dialog = DetailsFeedbackDialog()
            val bundle = Bundle()
            bundle.putString(EXTRA_VERB, verb)
            dialog.arguments = bundle
            return dialog
        }

    }

    override val layoutId = R.layout.dialog_details_feedback

    override val kodein: Kodein by kodein()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val verb = arguments?.getString(EXTRA_VERB)
        tvTitle.text = "Anything wrong with ${verb}?"

        btnSend.setOnClickListener {
            val mailto = "mailto:i@surajsau.in?" +
                    "&subject=" + Uri.encode("Feedback for ${verb}") +
                    "&body=" + Uri.encode("動詞 v${BuildConfig.VERSION_NAME}")

            val emailIntent = Intent(Intent.ACTION_SENDTO)
            emailIntent.data = Uri.parse(mailto)

            try {
                startActivity(emailIntent)
            } catch (e: ActivityNotFoundException) {
                //TODO: Handle case where no email app is available
            }

            dismiss()
        }
    }

}