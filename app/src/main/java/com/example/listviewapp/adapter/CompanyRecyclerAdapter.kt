package com.example.listviewapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.listviewapp.R
import com.example.listviewapp.model.CryptoModel
import java.util.*
import kotlin.collections.ArrayList


class CompanyRecyclerAdapter(private var cryptoList: ArrayList<CryptoModel>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), Filterable {
    private var filterList = ArrayList<CryptoModel>()

    init {
        filterList = cryptoList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CompanyViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.innerlayout, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return filterList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val cryptoModel = filterList[position]
        when (holder) {
            is CompanyViewHolder -> {
                holder.textCompanyName.text = cryptoModel.name
                holder.textCompanyCurrency.text = cryptoModel.name
                holder.textImage.text = cryptoModel.symbol.substring(0, 1)
            }
        }
    }

    class CompanyViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var textImage: TextView
        lateinit var textCompanyName: TextView
        lateinit var textCompanyCurrency: TextView

        init {
            run {
                textImage = itemView.findViewById(R.id.imageView1) as TextView
                textCompanyName = itemView.findViewById(R.id.company_name) as TextView
                textCompanyCurrency = itemView.findViewById(R.id.company_currency) as TextView
            }
        }


    }

    override fun getFilter(): Filter? {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filterResults = FilterResults()
                try {
                    var filteredList = ArrayList<CryptoModel>()
                    val charSearch = constraint.toString()
                    if (charSearch.isEmpty()) {
                        filteredList = cryptoList
                    } else {

                        cryptoList.forEach {
                            if (it.name.toLowerCase(Locale.ROOT).contains(charSearch.toLowerCase(Locale.ROOT))) {
                                filteredList.add(it)
                            }
                        }
                    }

                    filterResults.values = filteredList
                } catch (ex: Exception) {
                    ex.printStackTrace()
                }
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filterList = results?.values as ArrayList<CryptoModel>
                notifyDataSetChanged()
            }
        }
    }
}