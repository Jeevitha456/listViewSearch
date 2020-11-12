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

    lateinit var recyclerView: RecyclerView
    lateinit var companyadapter:CompanyRecyclerAdapter
    var cryptoList = ArrayList<CryptoModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recycler_view) as RecyclerView
        companyadapter=CompanyRecyclerAdapter(cryptoList,this)
        recyclerView.adapter= companyadapter
        recyclerView.layoutManager=LinearLayoutManager(this@MainActivity)

        setPeriodicWorkRequest()

        SingletonObject.getList().observe(this, androidx.lifecycle.Observer {
            if(it!=null)
            {
                cryptoList.clear()
                cryptoList.addAll(it)
                recyclerView.adapter?.notifyDataSetChanged()
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

    // SerachView

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
                    companyadapter.getFilter()?.filter(newText)
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