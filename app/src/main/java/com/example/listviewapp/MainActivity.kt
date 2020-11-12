package com.example.listviewapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
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
    private var dataList = ArrayList<CryptoModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recycler_view)

        companyadapter = CompanyRecyclerAdapter(dataList, this)
        recyclerView.adapter = companyadapter

        recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
        setPeriodicWorkRequest()

        CompanyWorker.App.getList().observe(this, androidx.lifecycle.Observer {
            if (it != null) {
                dataList.clear()

                dataList.addAll(it)
                recyclerView.adapter?.notifyDataSetChanged()
            }
        })


    }


    // implementing WorkManager

    private fun setPeriodicWorkRequest() {
        val periodicWorkRequest = PeriodicWorkRequest.Builder(
            CompanyWorker::class.java, 15,
            TimeUnit.MINUTES
        )
            .build()
        WorkManager.getInstance(applicationContext).enqueue(periodicWorkRequest)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        val menuItem = menu!!.findItem(R.id.menu_search)
        if (menuItem != null) {
            val searchview = menuItem.actionView as SearchView
            searchview.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText!!.isNotEmpty()) {

                        companyadapter.filter?.filter(newText)
                        recyclerView.adapter?.notifyDataSetChanged()
                    } else {
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