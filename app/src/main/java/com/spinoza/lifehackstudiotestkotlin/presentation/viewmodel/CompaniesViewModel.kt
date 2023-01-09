package com.spinoza.lifehackstudiotestkotlin.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.spinoza.lifehackstudiotestkotlin.domain.ApiService
import com.spinoza.lifehackstudiotestkotlin.domain.CompanyItem
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class CompaniesViewModel(private val apiService: ApiService) : ViewModel() {
    private val companies = MutableLiveData<MutableList<CompanyItem>>()
    private val isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    private val error = MutableLiveData<String>()
    private val compositeDisposable = CompositeDisposable()

    fun getCompanies(): LiveData<MutableList<CompanyItem>> = companies
    fun isLoading(): LiveData<Boolean> = isLoading
    fun isError(): LiveData<String> = error

    init {
        loadCompanies()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    private fun loadCompanies() {
        if (isLoading.value == false) {
            val disposable = apiService.loadCompanies()
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
                    { throwable -> error.value = throwable.toString() }
                )

            compositeDisposable.add(disposable)
        }
    }
}