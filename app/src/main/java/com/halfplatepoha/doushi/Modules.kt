package com.halfplatepoha.doushi

import androidx.lifecycle.ViewModel
import io.realm.Realm
import org.kodein.di.Kodein
import org.kodein.di.generic.*

val realmDbModule = Kodein.Module(name = "realmDbModule") {
    bind<Realm>(tag = "verb_db") with provider { Realm.getInstance(instance(tag = "verbs_RealmConfig")) }
    bind<Realm>(tag = "def_db") with provider { Realm.getInstance(instance(tag = "def_RealmConfig")) }
}

val dataModule = Kodein.Module(name = "dataModule") {
    bind<VerbDataProvider>() with provider { VerbDataProvider(instance(tag = "verb_db")) }
}

val mainModule = Kodein.Module(name = "mainActivity") {
    bind<ViewModel>(tag = MainViewModel::class.java.simpleName) with provider { MainViewModel(instance()) }
}