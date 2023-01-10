package com.spinoza.lifehackstudiotestkotlin.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.spinoza.lifehackstudiotestkotlin.domain.ApiService
import com.spinoza.lifehackstudiotestkotlin.domain.CompanyInfoItem
import com.spinoza.lifehackstudiotestkotlin.domain.CompanyItem
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class CompanyInfoViewModel(private val apiService: ApiService) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    private val _company = MutableLiveData<CompanyInfoItem>()
    val company: LiveData<CompanyInfoItem>
        get() = _company

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    fun loadCompany(companyItem: CompanyItem) {
        val disposable = apiService.loadCompanyInfo(companyItem.id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { companies -> _company.value = companies[0] },
                { throwable ->
                    _company.value =
                        CompanyInfoItem(companyItem.id, companyItem.name, companyItem.img)
                })
        compositeDisposable.add(disposable)
    }
}