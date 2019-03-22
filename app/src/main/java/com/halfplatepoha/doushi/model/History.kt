package com.halfplatepoha.doushi.model

import io.realm.RealmObject

open class History: RealmObject() {

    var searchTerm: String? = null
    var timestamp: Long? = null

}