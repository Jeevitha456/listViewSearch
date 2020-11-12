package com.example.listviewapp.model

import androidx.lifecycle.MutableLiveData

object SingletonObject {

    private var listLiveData = MutableLiveData<ArrayList<CryptoModel>>()

    fun list(data: ArrayList<CryptoModel>) {
        listLiveData.value = data
    }

    fun getList(): MutableLiveData<ArrayList<CryptoModel>> {
        return listLiveData
    }
}