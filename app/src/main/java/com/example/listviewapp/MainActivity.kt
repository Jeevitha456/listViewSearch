package com.example.listviewapp

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.listviewapp.adapter.CompanyRecyclerAdapter
import com.example.listviewapp.model.ApiClient
import com.example.listviewapp.model.CryptoModel
import com.example.listviewapp.model.DataModel
import com.example.listviewapp.model.DataSource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    @SuppressLint("WrongConstant")
    lateinit var recyclerView: RecyclerView
    lateinit var companyadapter:CompanyRecyclerAdapter
    lateinit var progerssProgressDialog: ProgressDialog
    var dataList = ArrayList<CryptoModel>()
    var displaylist= ArrayList<CryptoModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recycler_view) as RecyclerView
        recyclerView.adapter= CompanyRecyclerAdapter(dataList,this)
        companyadapter=CompanyRecyclerAdapter(dataList,this)
        recyclerView.layoutManager=LinearLayoutManager(this@MainActivity)
        //recyclerView.layoutManager=LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)

        progerssProgressDialog=ProgressDialog(this)
        progerssProgressDialog.setTitle("Loading")
        progerssProgressDialog.setCancelable(false)
        progerssProgressDialog.show()
        getData()
       //companyadapter=CompanyRecyclerAdapter()
        //recyclerView.layoutManager=LinearLayoutManager(this@MainActivity)

        //recyclerView.adapter=companyadapter
        //addDataSet()
    }
    private fun getData() {
        val call: Call<List<CryptoModel>> = ApiClient.getClient.getPhotos()
        call.enqueue(object : Callback<List<CryptoModel>> {

            override fun onResponse(call: Call<List<CryptoModel>>?, response: Response<List<CryptoModel>>?) {
                progerssProgressDialog.dismiss()
                dataList.addAll(response!!.body()!!)
                displaylist.addAll(dataList)

                recyclerView.adapter?.notifyDataSetChanged()

            }

            override fun onFailure(call: Call<List<CryptoModel>>?, t: Throwable?) {
                progerssProgressDialog.dismiss()
            }

        })


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
       menuInflater.inflate(R.menu.main,menu)
        val menuItem=menu!!.findItem(R.id.menu_search)
        if(menuItem!=null)
        {
            val searchview=menuItem.actionView as SearchView
            searchview.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText!!.isNotEmpty()) {
                        displaylist.clear()

                        companyadapter.getFilter()?.filter(newText)
                        recyclerView.adapter?.notifyDataSetChanged()
                    } else {
                        displaylist.clear()
                        displaylist.addAll(dataList)
                        recyclerView.adapter?.notifyDataSetChanged()
                    }

                    return false
                }

            })
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }
}