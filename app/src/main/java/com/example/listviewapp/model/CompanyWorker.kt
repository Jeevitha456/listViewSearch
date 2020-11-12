package com.example.listviewapp.model

import android.app.ProgressDialog
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.listviewapp.MainActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class CompanyWorker(context: Context,workerParameters: WorkerParameters):Worker(context,workerParameters) {

    override fun doWork(): Result {
        try {
            val call: Call<List<CryptoModel>> = ApiClient.getClient.getPhotos()
            call.enqueue(object : Callback<List<CryptoModel>> {

                override fun onResponse(call: Call<List<CryptoModel>>?, response: Response<List<CryptoModel>>?)
                {
                    SingletonObject.list((response!!.body() as ArrayList<CryptoModel>?)!!)
                }

                override fun onFailure(call: Call<List<CryptoModel>>?, t: Throwable?) {
                }
            })

            return Result.success()
        }
        catch (e: Exception) {
            return Result.failure()
        }
    }
}