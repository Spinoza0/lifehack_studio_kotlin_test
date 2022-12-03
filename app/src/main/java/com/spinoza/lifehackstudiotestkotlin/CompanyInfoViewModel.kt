package com.spinoza.lifehackstudiotestkotlin

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class CompanyInfoViewModel(application: Application) : AndroidViewModel(application) {
    private val company = MutableLiveData<CompanyInfoItem>()
    private val compositeDisposable = CompositeDisposable()

    fun getCompany(): LiveData<CompanyInfoItem> = company

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    fun loadCompany(companyItem: CompanyItem) {
        val disposable = ApiFactory.apiService.loadCompanyInfo(companyItem.getId())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { companies -> company.setValue(companies[0]) },
                { throwable ->
                    Log.d("loadCompany", throwable.toString())
                    company.setValue(
                        CompanyInfoItem(
                            companyItem.getId(),
                            companyItem.getName(),
                            companyItem.getImg()
                        )
                    )
                })

        compositeDisposable.add(disposable)
    }
}