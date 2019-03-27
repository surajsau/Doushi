package com.halfplatepoha.doushi.search

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import com.halfplatepoha.doushi.VerbDataProvider
import com.halfplatepoha.doushi.base.BaseViewModel
import com.halfplatepoha.doushi.base.IntentObject
import com.halfplatepoha.doushi.detail.DetailActivity
import com.halfplatepoha.doushi.model.Verb
import io.realm.RealmResults
import kotlin.reflect.KClass

class SearchViewModel(val verbDataProvider: VerbDataProvider): BaseViewModel() {

    val loaderVisibilityState = MutableLiveData<Boolean>()

    private val dataProviderResult = MutableLiveData<RealmResults<Verb>>()

    private val mappedResult : LiveData<Array<SearchResultModel>>

    val searchResult = MediatorLiveData<Array<SearchResultModel>>()

    init {
        mappedResult = Transformations.map(dataProviderResult) {
            it.map { verb -> SearchResultModel(verb.firstForm, verb.reading) }.toTypedArray()
        }

        searchResult.addSource(mappedResult) {
            it?.let {
                loaderVisibilityState.value = false
                searchResult.value = it
            }
        }
    }

    fun onSearchTextChange(searchString: String) {
        if(searchString.isNotEmpty()) {
            loaderVisibilityState.value = true
            call(verbDataProvider.search(searchString).subscribe({dataProviderResult.value = it}, {}, {}))
        } else {
            loaderVisibilityState.value = false
        }
    }

    fun onItemClick(verb: String?) {
        val intentMap = HashMap<String, Any?>()
        intentMap[DetailActivity.EXTRA_VERB] = verb
        intent.value = IntentObject(DetailActivity::class.java.simpleName, intentMap)
    }

}