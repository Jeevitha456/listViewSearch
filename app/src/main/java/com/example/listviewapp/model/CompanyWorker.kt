package com.example.listviewapp.model


import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class CompanyWorker(context: Context,workerParameters: WorkerParameters):Worker(context,workerParameters) {

    override fun doWork(): Result {
        try {
            val call: Call<List<CryptoModel>> = ApiClient.getClient.getCryptoList()
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