package com.halfplatepoha.doushi.singleverb

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.halfplatepoha.doushi.VerbDataProvider
import com.halfplatepoha.doushi.base.BaseViewModel
import com.halfplatepoha.doushi.base.IntentObject
import com.halfplatepoha.doushi.detail.DetailActivity
import com.halfplatepoha.doushi.model.Verb
import com.halfplatepoha.doushi.search.SearchResultModel
import io.realm.RealmResults

class SingleVerbDialogViewModel(private val verbDataProvider: VerbDataProvider): BaseViewModel() {

    val loaderVisibilityState = MutableLiveData<Boolean>()

    val title = MutableLiveData<String>()

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

    fun onItemClick(verb: String?) {
        val intentMap = HashMap<String, Any?>()
        intentMap[DetailActivity.EXTRA_VERB] = verb
        intent.value = IntentObject(DetailActivity::class.java.simpleName, intentMap)
    }

    fun searchString(string: String?, isFirstVerb: Boolean = true) {
        loaderVisibilityState.value = true
        title.value = string
        call(verbDataProvider.searchByPart(string!!, isFirstVerb).subscribe({dataProviderResult.value = it}, {}, {}))
    }

}