package com.halfplatepoha.doushi.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.halfplatepoha.doushi.R
import com.halfplatepoha.doushi.model.Verb

class SearchAdapter(val listener: SearchResultItemClickListener? = null): RecyclerView.Adapter<SearchResultViewHolder>() {

    private var items: Array<SearchResultModel>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultViewHolder {
        val view =  LayoutInflater.from(parent.context).inflate(R.layout.row_search_result, parent, false)
        return SearchResultViewHolder(view, listener)
    }

    override fun getItemCount(): Int {
        return items?.size?:0
    }

    override fun onBindViewHolder(holder: SearchResultViewHolder, position: Int) {
        items?.get(position)?.let {
            holder.tvVerbForm.text = it.verbForm
            holder.tvVerbReading.text = it.verbReading
            holder.itemView.tag = it.verbForm
        }
    }

    fun setItems(items: Array<SearchResultModel>?) {
        if(!items.isNullOrEmpty()) {
            this.items = items
            notifyDataSetChanged()
        }
    }

}

data class SearchResultModel(val verbForm: String?, val verbReading: String?)

class SearchResultViewHolder(itemView: View, clickListener: SearchResultItemClickListener?) : RecyclerView.ViewHolder(itemView) {

    val tvVerbForm: AppCompatTextView = itemView.findViewById(R.id.tvVerbForm)
    val tvVerbReading: AppCompatTextView = itemView.findViewById(R.id.tvVerbReading)

    init {
        itemView.setOnClickListener { clickListener?.onItemClick(it.tag?.toString()) }
    }

}

interface SearchResultItemClickListener {
    fun onItemClick(verb: String?)
}