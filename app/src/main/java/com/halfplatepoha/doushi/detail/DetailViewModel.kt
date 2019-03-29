package com.halfplatepoha.doushi.detail

import androidx.lifecycle.*
import com.halfplatepoha.doushi.*
import com.halfplatepoha.doushi.base.BaseViewModel
import io.reactivex.disposables.CompositeDisposable

class DetailViewModel(val verbDataProvider: VerbDataProvider,
                      val historyDataProvider: HistoryDataProvider,
                      val doushiPref: DoushiPref): BaseViewModel() {

    val title = MutableLiveData<String>()

    val subTitle = MutableLiveData<String>()

    val firstVerb = MutableLiveData<VerbPart>()

    val secondVerb = MutableLiveData<VerbPart>()

    val loaderVisibility = MutableLiveData<Boolean>()

    val error = MutableLiveData<String>()

    val meanings = MutableLiveData<List<MeaningAdapterModel>?>()

    val clickedItem = MutableLiveData<ClickedVerbPart>()

    private val disposables: CompositeDisposable

    private val language = doushiPref.getFromPref(PREF_LANGUAGE, LANGUAGE_JAPANESE)

    val transitive = MutableLiveData<String>()

    lateinit var verbString: String

    init {
        disposables = CompositeDisposable()
    }

    fun setVerb(verb: String) {
        verbString = verb
        title.value = verb
        loaderVisibility.value = true

        historyDataProvider.addHistory(verb)

        disposables.add(verbDataProvider.getDetails(verb)
            .subscribe({ verb ->
                loaderVisibility.value = false
                verb?.let {
                    subTitle.value = it.reading
                    firstVerb.value = VerbPart(it.firstVerb, it.firstVerbReading, it.firstVerbRomaji)
                    secondVerb.value = VerbPart(it.secondVerb, it.secondVerbReading, it.secondVerbRomaji)

                    transitive.value = it.transitive?.run {
                        when (language) {
                            LANGUAGE_ENGLISH -> this.transitiveEnglish()
                            else -> this
                        }
                    }

                    val usagePatternParts = it.usagePattern?.
                        replace("(1)", "")?.
                        replace("(2)", "")?.
                        trim()?.
                        split(";")

                    val japaneseMeanings = it.meanings?.filter { LANGUAGE_JAPANESE.equals(it.language) }

                    meanings.value = when(language) {
                        LANGUAGE_ENGLISH -> {
                            verb.meanings?.filter { LANGUAGE_ENGLISH.equals(it.language) }?.
                                    mapIndexed { index, meaning -> meaning.toAdapterModel(japaneseMeanings?.get(index)?.example!!, usagePatternParts!![index].usageToEnglish())}
                        }

                        LANGUAGE_JAPANESE ->
                            japaneseMeanings?.mapIndexed { index, meaning -> meaning.toAdapterModel(usagePatternParts!![index].usageToHiragana()) }

                        else -> {
                            it.meanings?.filter { LANGUAGE_JAPANESE.equals(it.language) }?.
                                mapIndexed { index, meaning -> meaning.toAdapterModel(usagePatternParts!![index].usageToHiragana()) }
                        }
                    }
                }
            }, {
                loaderVisibility.value = false
                error.value = it.localizedMessage
            }))
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    fun backClicked() {
        finishScreen()
    }

    fun firstVerbClicked() {
        clickedItem.value = ClickedVerbPart(firstVerb.value?.form!!, true)
    }

    fun secondVerbClicked() {
        clickedItem.value = ClickedVerbPart(secondVerb.value?.form!!, false)
    }

    fun feedbackClicked() {
        action.value = DetailActivity.ACTION_OPEN_FEEDBACK_DIALOG
    }

}

data class VerbPart(val form: String?, val reading: String?, val english: String?)

data class ClickedVerbPart(val verb: String, val isFirstVerb: Boolean)