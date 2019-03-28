package com.halfplatepoha.doushi

import android.content.Context
import androidx.lifecycle.ViewModel
import com.halfplatepoha.doushi.detail.DetailActivity
import com.halfplatepoha.doushi.detail.DetailViewModel
import com.halfplatepoha.doushi.history.HistoryViewModel
import com.halfplatepoha.doushi.main.MainViewModel
import com.halfplatepoha.doushi.search.SearchViewModel
import com.halfplatepoha.doushi.settings.SettingsFragmentViewModel
import com.halfplatepoha.doushi.singleverb.SingleVerbDialogViewModel
import io.realm.Realm
import org.kodein.di.Kodein
import org.kodein.di.generic.*

val realmDbModule = Kodein.Module(name = "realmDbModule") {
    bind<Realm>(tag = "verb_db") with provider { Realm.getInstance(instance(tag = "verbs_RealmConfig")) }
    bind<Realm>(tag = "def_db") with provider { Realm.getInstance(instance(tag = "def_RealmConfig")) }
}

val dataModule = Kodein.Module(name = "dataModule") {
    bind<VerbDataProvider>() with singleton { VerbDataProvider(instance(tag = "verb_db")) }
    bind<HistoryDataProvider>() with singleton { HistoryDataProvider(instance(tag = "def_db")) }
}

val viewModelModule = Kodein.Module(name = "viewModelModule") {
    bind<ViewModel>(tag = MainViewModel::class.java.simpleName) with provider { MainViewModel() }
    bind<ViewModel>(tag = SearchViewModel::class.java.simpleName) with provider { SearchViewModel(instance(), instance()) }
    bind<ViewModel>(tag = DetailViewModel::class.java.simpleName) with provider { DetailViewModel(instance(), instance(), instance()) }
    bind<ViewModel>(tag = SingleVerbDialogViewModel::class.java.simpleName) with provider { SingleVerbDialogViewModel(instance()) }
    bind<ViewModel>(tag = HistoryViewModel::class.java.simpleName) with provider { HistoryViewModel(instance()) }
    bind<ViewModel>(tag = SettingsFragmentViewModel::class.java.simpleName) with provider { SettingsFragmentViewModel(instance()) }
}