package com.example.listviewapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.work.*
import com.example.listviewapp.adapter.CompanyRecyclerAdapter
import com.example.listviewapp.model.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var companyadapter: CompanyRecyclerAdapter
    private var cryptoList = ArrayList<CryptoModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        try {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)

            recyclerView = findViewById(R.id.recycler_view)
            companyadapter = CompanyRecyclerAdapter(cryptoList)
            recyclerView.adapter = companyadapter
            recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)

            setPeriodicWorkRequest()

            SingletonObject.getList().observe(this, androidx.lifecycle.Observer {
                if (it != null) {
                    cryptoList.clear()
                    cryptoList.addAll(it)
                    recyclerView.adapter?.notifyDataSetChanged()
                }
            })
        } catch (e: Exception) {

        }
    }


    // implementing WorkManager
    private fun setPeriodicWorkRequest() {
        try {
            val periodicWorkRequest = PeriodicWorkRequest.Builder(CompanyWorker::class.java, 15,
                    TimeUnit.MINUTES)
                    .build()
            WorkManager.getInstance(applicationContext).enqueue(periodicWorkRequest)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // SearchView
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        val menuItem = menu!!.findItem(R.id.menu_search)
        if (menuItem != null) {
            val searchView = menuItem.actionView as SearchView
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    companyadapter.filter?.filter(newText)
                    return false
                }

            })
        }
        return super.onCreateOptionsMenu(menu)
    }

}