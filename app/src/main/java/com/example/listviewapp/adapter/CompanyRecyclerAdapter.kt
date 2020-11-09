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
import com.example.listviewapp.model.Company
import com.example.listviewapp.model.CryptoModel
import com.example.listviewapp.model.DataModel
import java.util.*
import kotlin.collections.ArrayList


class CompanyRecyclerAdapter(private var dataList: List<CryptoModel>, private val context: Context):RecyclerView.Adapter<RecyclerView.ViewHolder>(),Filterable {

   // private var items:List<Company> = ArrayList()
    var displaylist:ArrayList<CryptoModel> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return CompanyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.innerlayout, parent, false)
        )
    }

    override fun getItemCount(): Int {
       return dataList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val dataModel=dataList.get(position)


       when(holder)
       {
           is CompanyViewHolder->
           {
              // holder.(items.get(position))
               holder.bindItems(dataModel)
               holder.textCompanyName.text=dataModel.name
               holder.textCompanyCurrency.text= dataModel.name
               holder.textImage.text=dataModel.symbol.substring(0,1)
           }
             }
    }

    fun submitList(companylist:List<Company>){
         //items=companylist

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

           // textImage.text = company.image
            //textCompanyName.text = company.comanyName
            //textCompanyCurrency.text = company.currency
        }

    }

    override fun getFilter(): Filter? {
        return object:Filter()
        {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
             val  charSearch=constraint.toString()
               if(charSearch.isEmpty())
               {
                   displaylist.addAll(dataList)
               }
                else
               {
                   val resultList=ArrayList<CryptoModel>()
                   dataList.forEach {
                       if(it.name.toLowerCase(Locale.ROOT).contains(charSearch.toLowerCase(Locale.ROOT)))
                       {
                           resultList.add(it)
                       }
                   }
                   displaylist=resultList
               }
                val filterResults=FilterResults()
                filterResults.values=displaylist
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                displaylist.clear()
                displaylist.addAll(results?.values as ArrayList<CryptoModel>)
                //displaylist=results?.values as ArrayList<CryptoModel>
                notifyDataSetChanged()
            }
        }
    }
}