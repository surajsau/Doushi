package com.halfplatepoha.doushi.model

import io.realm.RealmObject

open class Meaning: RealmObject() {

    var language: String? = null
    var meaning: String? = null
    var example: String? = null
    var reading: String? = null

}