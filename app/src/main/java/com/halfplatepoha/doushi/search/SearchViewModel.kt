package com.halfplatepoha.doushi.search

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import com.halfplatepoha.doushi.*
import com.halfplatepoha.doushi.base.BaseViewModel
import com.halfplatepoha.doushi.base.IntentObject
import com.halfplatepoha.doushi.detail.DetailActivity
import com.halfplatepoha.doushi.model.Verb
import io.realm.RealmResults
import kotlin.reflect.KClass

class SearchViewModel(val verbDataProvider: VerbDataProvider,
                      val pref: DoushiPref): BaseViewModel() {

    val loaderVisibilityState = MutableLiveData<Boolean>()

    val modeText = MutableLiveData<String>()

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
            val request = when (pref.getFromPref(PREF_MODE, MODE_COMBINED)) {
                MODE_COMBINED -> verbDataProvider.search(searchString)
                MODE_FIRST_VERB -> verbDataProvider.searchByPart(searchString, true)
                MODE_SECOND_VERB -> verbDataProvider.searchByPart(searchString, false)
                else -> verbDataProvider.search(searchString)
            }
            call(request.subscribe({dataProviderResult.value = it}, {}, {}))
        } else {
            loaderVisibilityState.value = false
        }
    }

    fun onItemClick(verb: String?) {
        val intentMap = HashMap<String, Any?>()
        intentMap[DetailActivity.EXTRA_VERB] = verb
        intent.value = IntentObject(DetailActivity::class.java.simpleName, intentMap)
    }

    fun clickMode() {
        action.value = SearchFragment.ACTION_OPEN_CHOOSE_MODE_DIALOG
    }

    fun onModeSelected(option: String) {
        pref.setInPref(PREF_MODE, option)
        modeText.value = option

    }

    fun onViewCreated() {
        modeText.value = pref.getFromPref(PREF_MODE, MODE_COMBINED)
    }

}