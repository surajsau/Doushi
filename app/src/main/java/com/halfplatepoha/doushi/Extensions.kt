package com.halfplatepoha.doushi

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.halfplatepoha.doushi.detail.MeaningAdapterModel
import com.halfplatepoha.doushi.model.Meaning
import com.jakewharton.rxbinding3.widget.TextViewAfterTextChangeEvent
import io.realm.RealmModel
import io.realm.RealmResults
import org.kodein.di.KodeinAware
import org.kodein.di.direct
import org.kodein.di.generic.instance

inline fun<reified VM: ViewModel, T>T.viewModel(): Lazy<VM> where T: KodeinAware, T: AppCompatActivity {
    return lazy { ViewModelProviders.of(this, direct.instance()).get(VM::class.java.simpleName, VM::class.java) }
}

inline fun<reified VM: ViewModel, T>T.viewModel(): Lazy<VM> where T: KodeinAware, T: Fragment {
    return lazy { ViewModelProviders.of(this, direct.instance()).get(VM::class.java.simpleName, VM::class.java) }
}

fun<T: RealmModel> RealmResults<T>.asLiveData() = RealmLiveData<T>(this)

fun TextViewAfterTextChangeEvent.text(): String = this.editable?.toString()?:""

fun String.isJapanese(): Boolean = !this.matches(".*[a-z].*.".toRegex())

fun ViewGroup.inflater(): LayoutInflater = LayoutInflater.from(this.context)

fun Meaning.toAdapterModel(usagePatternString: String): MeaningAdapterModel {
    val parts = usagePatternString.split(":")
    val usagePatterns = ArrayList<String>()

    parts.forEach { usagePatterns.add(it.replace("|", " ")) }

    return MeaningAdapterModel(this.meaning!!.replace("(1)", "").replace("(2)", ""),
        this.example!!,
        usagePatterns.toTypedArray())
}

fun Meaning.toAdapterModel(example: String, usagePatternString: String): MeaningAdapterModel {
    val parts = usagePatternString.split(":")
    val usagePatterns = ArrayList<String>()

    parts.forEach { usagePatterns.add(it.replace("|", " ")) }

    return MeaningAdapterModel(this.meaning!!.replace("(1)", "").replace("(2)", ""),
        example,
        usagePatterns.toTypedArray())
}

fun String.usageToHiragana() = this.replace("ガ", "が")
                                        .replace("ヲ", "を")
                                        .replace("ノ", "の")
                                        .replace("ト", "と")
                                        .replace("ハ", "は")
                                        .replace("ニ", "に")

fun String.usageToEnglish() = this.replace("ガ", " ga")
    .replace("ヲ", " o")
    .replace("ノ", " no")
    .replace("ト", " to")
    .replace("ハ", " wa")
    .replace("ニ", " ni")

fun String.transitiveEnglish(): String = when(this) {
    "他" -> "transitive"
    "自（意志）" -> "volitional intransitive"
    "自" -> "intransitive"
    else -> ""
}

fun DoushiApp.setLocale() {

}