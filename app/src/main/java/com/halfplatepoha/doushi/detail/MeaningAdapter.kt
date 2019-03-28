package com.halfplatepoha.doushi.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.halfplatepoha.doushi.R
import com.halfplatepoha.doushi.model.Meaning
import java.lang.StringBuilder

class MeaningAdapter(private val verb: String): RecyclerView.Adapter<MeaningViewHolder>() {

    private var meanings: Array<MeaningAdapterModel>?= null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MeaningViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_meaning, parent, false)
        return MeaningViewHolder(view)
    }

    override fun getItemCount(): Int {
        return meanings?.size?:0
    }

    override fun onBindViewHolder(holder: MeaningViewHolder, position: Int) {
        meanings?.get(position)?.let {
            holder.tvMeaning.text = it.meaning
            holder.tvExample.text = it.example

            val usagePattern = StringBuilder("")
            it.usagePattern.forEach { usagePatternPart -> usagePattern.append(usagePatternPart).append(" ").append(verb) }

            holder.tvUsagePattern.text = usagePattern
        }
    }

    fun setItems(meanings: Array<MeaningAdapterModel>?) {
        this.meanings = meanings
        notifyDataSetChanged()
    }

}

class MeaningViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    val tvMeaning: AppCompatTextView = itemView.findViewById(R.id.tvMeaning)
    val tvExample: AppCompatTextView = itemView.findViewById(R.id.tvExample)
    val tvUsagePattern: AppCompatTextView = itemView.findViewById(R.id.tvUsagePattern)


}

data class MeaningAdapterModel(val meaning: String,
                               val example: String,
                               val usagePattern: Array<String>)