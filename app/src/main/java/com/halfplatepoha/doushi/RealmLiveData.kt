package com.halfplatepoha.doushi

import android.util.Log
import androidx.lifecycle.LiveData
import io.realm.RealmChangeListener
import io.realm.RealmModel
import io.realm.RealmResults

class RealmLiveData <T: RealmModel>(val realmResults: RealmResults<T>): LiveData<RealmResults<T>>() {

    private val listener = RealmChangeListener<RealmResults<T>> { results ->
        Log.e("RESULTS", "result " + results.size)
        value = results
    }

    override fun onActive() {
        Log.e("RESULTS", toString() + " onActive")
        realmResults.addChangeListener(listener)
    }

    override fun onInactive() {
        Log.e("RESULTS", toString() + " onInactive")
        realmResults.removeChangeListener(listener)
    }

}