package com.halfplatepoha.doushi

import android.os.Bundle
import com.halfplatepoha.doushi.base.BaseActivity
import org.kodein.di.Kodein
import org.kodein.di.android.kodein
import com.halfplatepoha.doushi.main.MainActivity
import android.content.Intent
import android.os.Handler
import androidx.core.os.HandlerCompat.postDelayed



class SplashActivity: BaseActivity() {
    override val layoutId = R.layout.activity_splash

    override val kodein: Kodein by kodein()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Handler().postDelayed(
            Runnable /*
                 * Showing splash screen with a timer. This will be useful when you
                 * want to show case your app logo / company
                 */

            {
                // This method will be executed once the timer is over
                // Start your app main activity
                val i = Intent(this@SplashActivity, MainActivity::class.java)
                startActivity(i)

                // close this activity
                finish()
            }, 700
        )
    }

}