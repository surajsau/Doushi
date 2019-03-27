package com.halfplatepoha.doushi.detail

import androidx.lifecycle.*
import com.halfplatepoha.doushi.LANGUAGE_JAPANESE
import com.halfplatepoha.doushi.VerbDataProvider
import com.halfplatepoha.doushi.base.BaseViewModel
import com.halfplatepoha.doushi.base.STATE_FINISH
import com.halfplatepoha.doushi.model.Meaning
import io.reactivex.disposables.CompositeDisposable

class DetailViewModel(val verbDataProvider: VerbDataProvider): BaseViewModel() {

    val title = MutableLiveData<String>()

    val subTitle = MutableLiveData<String>()

    val firstVerb = MutableLiveData<VerbPart>()

    val secondVerb = MutableLiveData<VerbPart>()

    val loaderVisibility = MutableLiveData<Boolean>()

    val error = MutableLiveData<String>()

    val meanings = MutableLiveData<List<Meaning>?>()

    val clickedItem = MutableLiveData<ClickedVerbPart>()

    private val disposables: CompositeDisposable

    init {
        disposables = CompositeDisposable()
    }

    fun setVerb(verb: String) {
        title.value = verb
        loaderVisibility.value = true
        disposables.add(verbDataProvider.getDetails(verb)
            .subscribe({

                loaderVisibility.value = false
                it?.let {
                    subTitle.value = it.reading
                    firstVerb.value = VerbPart(it.firstVerb, it.firstVerbReading, it.firstVerbRomaji)
                    secondVerb.value = VerbPart(it.secondVerb, it.secondVerbReading, it.secondVerbRomaji)

                    meanings.value = it.meanings?.filter { LANGUAGE_JAPANESE.equals(it.language) }
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

}

data class VerbPart(val form: String?, val reading: String?, val english: String?)

data class ClickedVerbPart(val verb: String, val isFirstVerb: Boolean)