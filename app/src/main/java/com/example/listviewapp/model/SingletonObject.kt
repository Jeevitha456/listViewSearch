package com.example.listviewapp.model

import androidx.lifecycle.MutableLiveData

object SingletonObject {

    var listlivedata = MutableLiveData<ArrayList<CryptoModel>>()

    fun list(data: ArrayList<CryptoModel>) {
        listlivedata.value = data
    }

    fun getList(): MutableLiveData<ArrayList<CryptoModel>> {
        return listlivedata
    }
}