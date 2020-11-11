package com.example.listviewapp

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.work.*
import com.example.listviewapp.adapter.CompanyRecyclerAdapter
import com.example.listviewapp.model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    @SuppressLint("WrongConstant")
    lateinit var recyclerView: RecyclerView
    lateinit var companyadapter:CompanyRecyclerAdapter
    lateinit var progerssProgressDialog: ProgressDialog
    var dataList = ArrayList<CryptoModel>()
    var searchlist= ArrayList<CryptoModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recycler_view) as RecyclerView

        companyadapter=CompanyRecyclerAdapter(searchlist,this)
        recyclerView.adapter= CompanyRecyclerAdapter(dataList,this)

        recyclerView.layoutManager=LinearLayoutManager(this@MainActivity)

        //progerssProgressDialog=ProgressDialog(this)
        //progerssProgressDialog.setTitle("Loading")
       // progerssProgressDialog.setCancelable(false)
       // progerssProgressDialog.show()
        setPeriodicWorkRequest()

        CompanyWorker.App.getList().observe(this, androidx.lifecycle.Observer {
            if(it!=null)
            {
                dataList.clear()

                dataList.addAll(it)
                searchlist.addAll(dataList)
                recyclerView.adapter?.notifyDataSetChanged()
            }
        })

        //setOneTimeWorkRequest()

       //getData()

    }

     fun getData() {
        val call: Call<List<CryptoModel>> = ApiClient.getClient.getPhotos()
        call.enqueue(object : Callback<List<CryptoModel>> {

            override fun onResponse(call: Call<List<CryptoModel>>?, response: Response<List<CryptoModel>>?) {
                progerssProgressDialog.dismiss()
                dataList.addAll(response!!.body()!!)
                searchlist.addAll(dataList)

                recyclerView.adapter?.notifyDataSetChanged()

            }

            override fun onFailure(call: Call<List<CryptoModel>>?, t: Throwable?) {
                progerssProgressDialog.dismiss()
            }

        })


    }



    // implementing WorkManager

    private fun setPeriodicWorkRequest(){
        val periodicWorkRequest=PeriodicWorkRequest.Builder(CompanyWorker::class.java,15,
            TimeUnit.MINUTES)
            .build()
        WorkManager.getInstance(applicationContext).enqueue(periodicWorkRequest)

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
                        searchlist.clear()

                        companyadapter.getFilter()?.filter(newText)
                        recyclerView.adapter?.notifyDataSetChanged()
                    } else {
                        searchlist.clear()
                        searchlist.addAll(dataList)
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