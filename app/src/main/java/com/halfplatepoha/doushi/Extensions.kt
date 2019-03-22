package com.halfplatepoha.doushi

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import io.realm.RealmModel
import io.realm.RealmResults
import org.kodein.di.KodeinAware
import org.kodein.di.direct
import org.kodein.di.generic.instance

inline fun<reified VM: ViewModel, T>T.viewModel(): Lazy<VM> where T: KodeinAware, T: AppCompatActivity {
    return lazy { ViewModelProviders.of(this, direct.instance()).get(VM::class.java) }
}

fun<T: RealmModel> RealmResults<T>.asLiveData() = RealmLiveData<T>(this)