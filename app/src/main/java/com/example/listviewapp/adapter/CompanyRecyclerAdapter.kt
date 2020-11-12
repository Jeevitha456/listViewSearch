package com.example.listviewapp.adapter

import android.content.Context
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


class CompanyRecyclerAdapter(private var cryptoList: ArrayList<CryptoModel>, private val context: Context):RecyclerView.Adapter<RecyclerView.ViewHolder>(),Filterable {
    private var searchlist=ArrayList<CryptoModel>()
    init {
        searchlist=cryptoList
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CompanyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.innerlayout, parent, false)
        )
    }

    override fun getItemCount(): Int {
       return searchlist.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val crptoModel=searchlist.get(position)
       when(holder)
       {
           is CompanyViewHolder->
           {
               holder.bindItems(crptoModel)
               holder.textCompanyName.text=crptoModel.name
               holder.textCompanyCurrency.text= crptoModel.name
               holder.textImage.text=crptoModel.symbol.substring(0,1)
           }
       }
    }

    class CompanyViewHolder constructor(itemView:View):RecyclerView.ViewHolder(itemView)
    {
        lateinit var textImage:TextView
        lateinit var textCompanyName:TextView
        lateinit var textCompanyCurrency:TextView
        init {
        {
             textImage = itemView.findViewById(R.id.imageView1) as TextView
             textCompanyName  = itemView.findViewById(R.id.company_name) as TextView
             textCompanyCurrency  = itemView.findViewById(R.id.company_currency) as TextView
        }
    }

       fun bindItems(cryptoModel: CryptoModel) {
             textImage = itemView.findViewById(R.id.imageView1) as TextView
             textCompanyName  = itemView.findViewById(R.id.company_name) as TextView
             textCompanyCurrency  = itemView.findViewById(R.id.company_currency) as TextView
        }

    }

    override fun getFilter(): Filter? {
        return object:Filter()
        {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filterResults=FilterResults()
                try
                {
                    var filteredList=ArrayList<CryptoModel>()
                    val  charSearch=constraint.toString()
                    if(charSearch.isEmpty())
                    {
                        searchlist=cryptoList
                    }
                    else
                    {

                        cryptoList.forEach {
                            if(it.name.toLowerCase(Locale.ROOT).contains(charSearch.toLowerCase(Locale.ROOT)))
                            {
                                filteredList.add(it)
                            }
                        }
                        searchlist=filteredList
                    }

                    filterResults.values=searchlist
                }
                catch (ex:Exception)
                {

                }
           return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                try {
                    searchlist.addAll(results?.values as ArrayList<CryptoModel>)
                    notifyDataSetChanged()
                }
                catch (ex:Exception)
                {

                }
            }
        }
    }
}