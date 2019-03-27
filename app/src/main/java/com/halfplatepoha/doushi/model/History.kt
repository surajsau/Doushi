package com.halfplatepoha.doushi.model

import io.realm.RealmObject

const val SEARCH_TERM = "searchTerm"
const val TIME_STAMP = "timestamp"
const val COUNT = "count"

open class History: RealmObject() {

    var searchTerm: String? = null
    var timestamp: Long? = null
    var count: Int? = null

}