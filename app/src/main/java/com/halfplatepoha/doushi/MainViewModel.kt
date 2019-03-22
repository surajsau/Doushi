package com.halfplatepoha.doushi

import android.util.Log
import androidx.arch.core.util.Function
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.halfplatepoha.doushi.model.Verb
import io.realm.RealmResults

class MainViewModel(val dataProvider: VerbDataProvider): ViewModel() {

    private val query = MutableLiveData<String>()

    val results: LiveData<RealmResults<Verb>> = Transformations.switchMap(query){
        dataProvider.searchQuery(it)
    }

    fun onTextChange(searchString: String)  {
        query.value = searchString
    }

}