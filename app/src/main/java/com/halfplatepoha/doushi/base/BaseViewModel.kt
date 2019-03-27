package com.halfplatepoha.doushi.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseViewModel: ViewModel() {

    open val intent = MutableLiveData<IntentObject>()

    open val action = MutableLiveData<Int>()

    private val disposables = CompositeDisposable()

    protected fun call(disposable: Disposable) {
        disposables.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    protected open fun finishScreen() {
        action.value = STATE_FINISH
    }

}

const val STATE_FINISH = 1

data class IntentObject (val targetClass: String, val intentMap: HashMap<String, Any?>? = null)