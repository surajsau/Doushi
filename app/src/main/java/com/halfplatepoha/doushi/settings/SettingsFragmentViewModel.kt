package com.halfplatepoha.doushi.settings

import androidx.lifecycle.MutableLiveData
import com.halfplatepoha.doushi.DoushiPref
import com.halfplatepoha.doushi.LANGUAGE_JAPANESE
import com.halfplatepoha.doushi.PREF_LANGUAGE
import com.halfplatepoha.doushi.base.BaseViewModel

class SettingsFragmentViewModel(val pref: DoushiPref): BaseViewModel() {

    val languagePreference = MutableLiveData<String>()

    fun clickPreference() {
        action.value = SettingsFragment.ACTION_OPEN_LANGUAGE_DIALOG
    }

    fun onLanguageSelected(language: String) {
        languagePreference.value = language
        pref.setInPref(PREF_LANGUAGE, language)
    }

    fun onViewCreated() {
        languagePreference.value = pref.getFromPref(PREF_LANGUAGE, LANGUAGE_JAPANESE)
    }

}