package com.halfplatepoha.doushi

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.halfplatepoha.doushi.base.BaseDialogFragment
import kotlinx.android.synthetic.main.dialog_feedback.*
import org.kodein.di.Kodein
import org.kodein.di.android.x.kodein
import android.content.ActivityNotFoundException
import android.net.Uri


class FeedbackDialog: BaseDialogFragment() {

    override val layoutId = R.layout.dialog_feedback

    override val kodein: Kodein by kodein()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnSend.setOnClickListener {
            val mailto = "mailto:i@surajsau.in?" +
                    "&subject=" + Uri.encode("Feedback for 動詞") +
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