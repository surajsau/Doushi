package com.halfplatepoha.doushi

import com.halfplatepoha.doushi.model.History
import com.halfplatepoha.doushi.model.SEARCH_TERM
import io.realm.Realm

class HistoryDataProvider(private val realm: Realm) {

    fun addHistory(verb: String) {
        realm.beginTransaction()

        val result = realm.where(History::class.java).equalTo(SEARCH_TERM, verb).findFirst()
        result?.let {
            it.count?.plus(1) ?: 1
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

    fun getHistory(): RealmLiveData<History> {
        return realm.where(History::class.java).findAllAsync().asLiveData()
    }

}