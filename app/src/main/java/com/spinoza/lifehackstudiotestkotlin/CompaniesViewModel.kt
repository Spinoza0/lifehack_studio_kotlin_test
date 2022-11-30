package com.spinoza.lifehackstudiotestkotlin

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.functions.Consumer
import io.reactivex.rxjava3.schedulers.Schedulers

class CompaniesViewModel(application: Application) : AndroidViewModel(application) {
    private val companies = MutableLiveData<MutableList<CompanyItem>>()
    private val isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    private val compositeDisposable = CompositeDisposable()

    fun getCompanies(): LiveData<MutableList<CompanyItem>> = companies
    fun isLoading(): LiveData<Boolean> = isLoading

    init {
        loadCompanies()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    private fun loadCompanies() {
        if (isLoading.value == false) {
            val disposable = ApiFactory.apiService.loadCompanies()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe { isLoading.setValue(true) }
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate { isLoading.setValue(false) }
                .subscribe({ companiesResponse ->
                    val loadedCompanies: MutableList<CompanyItem>
                    if (companies.value != null) {
                        loadedCompanies = companies.value!!
                        loadedCompanies.addAll(companiesResponse)
                    } else {
                        loadedCompanies = companiesResponse.toMutableList()
                    }
                    companies.setValue(loadedCompanies)
                }, Consumer { throwable -> Log.d("loadCompanies", throwable.toString()) })

            compositeDisposable.add(disposable)
        }
    }
}