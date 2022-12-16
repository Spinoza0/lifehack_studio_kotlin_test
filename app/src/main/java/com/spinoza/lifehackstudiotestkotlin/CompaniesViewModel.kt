package com.spinoza.lifehackstudiotestkotlin

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
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
                .doOnSubscribe { isLoading.value = true }
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate { isLoading.value = false }
                .subscribe(
                    { companiesResponse ->
                        val loadedCompanies = companies.value ?: companiesResponse.toMutableList()
                        companies.value?.let { loadedCompanies.addAll(companiesResponse) }
                        companies.value = loadedCompanies
                    },
                    { throwable -> Log.d("loadCompanies", throwable.toString()) }
                )

            compositeDisposable.add(disposable)
        }
    }
}