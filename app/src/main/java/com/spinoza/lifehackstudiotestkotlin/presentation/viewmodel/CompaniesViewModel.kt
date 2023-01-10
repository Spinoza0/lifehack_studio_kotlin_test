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
    private val compositeDisposable = CompositeDisposable()

    private val _companies = MutableLiveData<List<CompanyItem>>()
    val companies: LiveData<List<CompanyItem>>
        get() = _companies

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    init {
        loadCompanies()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    private fun loadCompanies() {
        if (_isLoading.value == false) {
            val disposable = apiService.loadCompanies()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe { _isLoading.value = true }
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate { _isLoading.value = false }
                .subscribe(
                    { companiesResponse ->
                        val loadedCompanies = mutableListOf<CompanyItem>()
                        _companies.value?.let { loadedCompanies.addAll(it) }
                        loadedCompanies.addAll(companiesResponse)
                        _companies.value = loadedCompanies
                    },
                    { throwable -> _error.value = throwable.toString() }
                )

            compositeDisposable.add(disposable)
        }
    }
}