package com.halfplatepoha.doushi

import androidx.lifecycle.ViewModel
import com.halfplatepoha.doushi.detail.DetailActivity
import com.halfplatepoha.doushi.detail.DetailViewModel
import com.halfplatepoha.doushi.main.MainViewModel
import com.halfplatepoha.doushi.search.SearchViewModel
import com.halfplatepoha.doushi.singleverb.SingleVerbDialogViewModel
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

val viewModelModule = Kodein.Module(name = "viewModelModule") {
    bind<ViewModel>(tag = MainViewModel::class.java.simpleName) with provider { MainViewModel() }
    bind<ViewModel>(tag = SearchViewModel::class.java.simpleName) with provider { SearchViewModel(instance()) }
    bind<ViewModel>(tag = DetailViewModel::class.java.simpleName) with provider { DetailViewModel(instance()) }
    bind<ViewModel>(tag = SingleVerbDialogViewModel::class.java.simpleName) with provider { SingleVerbDialogViewModel(instance()) }
}