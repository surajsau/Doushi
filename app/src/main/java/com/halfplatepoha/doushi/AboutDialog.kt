package com.halfplatepoha.doushi

import android.os.Bundle
import android.view.View
import com.halfplatepoha.doushi.base.BaseDialogFragment
import kotlinx.android.synthetic.main.dialog_about.*
import org.kodein.di.Kodein
import org.kodein.di.android.x.kodein
import android.content.Intent
import android.net.Uri


class AboutDialog: BaseDialogFragment() {

    override val layoutId = R.layout.dialog_about

    override val kodein: Kodein by kodein()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvTwitter.setOnClickListener {
            try {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?screen_name=su____ji")))
            } catch (e: Exception) {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/su____ji")))
            }
        }

        tvResource1Link.setOnClickListener { startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://db4.ninjal.ac.jp/vvlexicon/"))) }
    }

}