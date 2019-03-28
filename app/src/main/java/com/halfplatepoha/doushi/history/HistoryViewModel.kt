package com.halfplatepoha.doushi.history

import androidx.lifecycle.MutableLiveData
import com.halfplatepoha.doushi.HistoryDataProvider
import com.halfplatepoha.doushi.base.BaseViewModel
import com.halfplatepoha.doushi.base.IntentObject
import com.halfplatepoha.doushi.detail.DetailActivity
import com.halfplatepoha.doushi.model.History
import io.realm.RealmResults

class HistoryViewModel(private val historyDataProvider: HistoryDataProvider): BaseViewModel() {

    companion object {
        const val FILTER_MOST_VIEWED = 1
        const val FILTER_MOST_RECENT = 2
    }

    val historyList = MutableLiveData<RealmResults<History>>()

    fun list(sort: Int = FILTER_MOST_RECENT) {
        call(historyDataProvider.getHistory(sort).subscribe({historyList.value = it}, {}, {}))
    }

    fun onItemClick(verb: String) {
        val intentMap = HashMap<String, Any?>()
        intentMap.put(DetailActivity.EXTRA_VERB, verb)
        intent.value = IntentObject(DetailActivity::class.java.simpleName, intentMap)
    }

}