package com.halfplatepoha.doushi

import com.halfplatepoha.doushi.model.Verb
import io.realm.Realm

class VerbDataProvider(val realm: Realm) {

    fun searchQuery(searchString: String): RealmLiveData<Verb> {
        return realm.where(Verb::class.java)
            .contains("romaji", searchString)
            .findAllAsync()
            .asLiveData()
    }

}