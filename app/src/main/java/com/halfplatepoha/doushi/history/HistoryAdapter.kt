package com.halfplatepoha.doushi.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.halfplatepoha.doushi.R
import com.halfplatepoha.doushi.inflater
import com.halfplatepoha.doushi.model.History

class HistoryAdapter(val historyItemClickListener: HistoryItemClickListener): RecyclerView.Adapter<HistoryViewHolder>() {

    private var items: Array<History>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view = parent.inflater().inflate(R.layout.row_history, parent, false)
        return HistoryViewHolder(view, historyItemClickListener)
    }

    override fun getItemCount(): Int {
        return items?.size?:0
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        items?.get(position)?.let {
            holder.itemView.tag = it.searchTerm
            holder.tvVerb.text = it.searchTerm
        }
    }

    fun setItems(items: Array<History>) {
        this.items = items
        notifyDataSetChanged()
    }

}

class HistoryViewHolder(itemView: View, historyItemClickListener: HistoryItemClickListener): RecyclerView.ViewHolder(itemView) {

    val tvVerb : AppCompatTextView = itemView.findViewById(R.id.tvVerb)

    init {
        itemView.setOnClickListener { it.tag?.let {tag -> historyItemClickListener.onItemClick(tag.toString())} }
    }

}

interface HistoryItemClickListener {
    fun onItemClick(verb: String)
}