package com.halfplatepoha.doushi

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import io.realm.Realm
import io.realm.RealmConfiguration
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.direct
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class DoushiApp: Application(), KodeinAware {

    override val kodein by Kodein.lazy {
        import(realmDbModule)
        import(mainModule)
        import(dataModule)

        bind<ViewModelProvider.Factory>() with singleton { ViewModelFactory(direct) }

        bind<RealmConfiguration>(tag = "verbs_RealmConfig") with provider { this@DoushiApp.verbsRealmConfiguration() }
        bind<RealmConfiguration>(tag = "def_RealmConfig") with provider { this@DoushiApp.defaultRealmConfiguration() }
    }

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
    }

    fun defaultRealmConfiguration(): RealmConfiguration {
        return RealmConfiguration.Builder()
            .name("doushi.realm")
            .modules(DefaultSchema())
            .schemaVersion(1)
            .build()
    }

    fun verbsRealmConfiguration(): RealmConfiguration {
        return RealmConfiguration.Builder()
            .assetFile("verb.realm")
            .modules(VerbSchema())
            .schemaVersion(1)
            .build()
    }

}