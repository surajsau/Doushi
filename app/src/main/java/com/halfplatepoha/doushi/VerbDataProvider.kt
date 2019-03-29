package com.halfplatepoha.doushi

import com.halfplatepoha.doushi.model.*
import io.reactivex.Flowable
import io.reactivex.Single
import io.realm.Realm
import io.realm.RealmResults

class VerbDataProvider(val realm: Realm) {

    fun search(searchString: String): Flowable<RealmResults<Verb>> {
        val field = if(searchString.isJapanese()) READING else ROMAJI
        return realm.where(Verb::class.java)
            .contains(field, searchString)
            .findAllAsync()
            .asFlowable()
    }

    fun searchByPart(searchString: String, isFirstVerb: Boolean = true): Flowable<RealmResults<Verb>> {
        val field = if(isFirstVerb) {
            if(searchString.isJapanese()) FIRST_VERB_READING else FIRST_VERB_ROMAJI
        } else {
            if(searchString.isJapanese()) SECOND_VERB_READING else SECOND_VERB_ROMAJI
        }

        return realm.where(Verb::class.java)
            .contains(field, searchString)
            .findAllAsync()
            .asFlowable()
    }

    fun getDetails(verb: String): Single<Verb> {
        val result = realm.where(Verb::class.java)
            .equalTo("firstForm", verb)
            .findFirst()
        return Single.just(result)
    }

}