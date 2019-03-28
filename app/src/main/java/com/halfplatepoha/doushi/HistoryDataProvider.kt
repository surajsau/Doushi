package com.halfplatepoha.doushi

import com.halfplatepoha.doushi.history.HistoryViewModel
import com.halfplatepoha.doushi.model.COUNT
import com.halfplatepoha.doushi.model.History
import com.halfplatepoha.doushi.model.SEARCH_TERM
import com.halfplatepoha.doushi.model.TIME_STAMP
import io.reactivex.Flowable
import io.realm.Realm
import io.realm.RealmResults
import io.realm.Sort

class HistoryDataProvider(private val realm: Realm) {

    fun addHistory(verb: String) {
        realm.beginTransaction()

        val result = realm.where(History::class.java).equalTo(SEARCH_TERM, verb).findFirst()
        result?.let {
            it.count?.plus(1)
            it.timestamp = System.currentTimeMillis()
        }?: let {
            val newObject = realm.createObject(History::class.java)
            newObject.count = 1
            newObject.searchTerm = verb
            newObject.timestamp = System.currentTimeMillis()

            realm.insert(newObject)
        }

        realm.commitTransaction()
    }

    fun getHistory(sortType: Int): Flowable<RealmResults<History>>  {
        val sortField = if(sortType == HistoryViewModel.FILTER_MOST_RECENT) TIME_STAMP else COUNT

        return realm.where(History::class.java)
            .sort(sortField, Sort.DESCENDING)
            .findAllAsync()
            .asFlowable()
    }

}