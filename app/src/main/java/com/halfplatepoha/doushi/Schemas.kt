package com.halfplatepoha.doushi

import com.halfplatepoha.doushi.model.History
import com.halfplatepoha.doushi.model.Meaning
import com.halfplatepoha.doushi.model.Verb
import io.realm.annotations.RealmModule

@RealmModule(classes = arrayOf(Verb::class, Meaning::class))
class VerbSchema

@RealmModule(classes = arrayOf(History::class))
class DefaultSchema