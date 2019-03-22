package com.halfplatepoha.doushi.model

import io.realm.RealmList
import io.realm.RealmObject

open class Verb: RealmObject() {

    var firstForm: String? = null
    var secondForm: String? = null
    var reading: String? = null
    var romaji: String? = null
    var firstVerb: String? = null
    var firstVerbReading: String? = null
    var firstVerbRomaji: String? = null
    var firstVerbMasu: String? = null
    var firstVerbMasuReading: String? = null
    var firstVerbMasuRomaji: String? = null
    var secondVerb: String? = null
    var secondVerbReading: String? = null
    var secondVerbRomaji: String? = null
    var transitive: String? = null
    var usagePattern: String? = null
    var seeAlso: String? = null
    var pronounce: String? = null
    var noun: String? = null
    var synonymn: String? = null
    var antonym: String? = null
    var remarks: String? = null
    var meanings: RealmList<Meaning>? = null
    var nlbLink: String ? = null

}