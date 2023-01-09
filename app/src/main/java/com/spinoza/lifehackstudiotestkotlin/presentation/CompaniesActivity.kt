package com.spinoza.lifehackstudiotestkotlin.presentation

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.spinoza.lifehackstudiotestkotlin.data.ApiFactory
import com.spinoza.lifehackstudiotestkotlin.databinding.ActivityCompaniesBinding
import com.spinoza.lifehackstudiotestkotlin.presentation.adapter.CompaniesAdapter
import com.spinoza.lifehackstudiotestkotlin.presentation.viewmodel.CompaniesViewModel
import com.spinoza.lifehackstudiotestkotlin.presentation.viewmodel.ViewModelFactory


class CompaniesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCompaniesBinding
    private lateinit var companiesAdapter: CompaniesAdapter
    private lateinit var companiesViewModel: CompaniesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCompaniesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        companiesViewModel = ViewModelProvider(
            this,
            ViewModelFactory(ApiFactory.apiService)
        )[CompaniesViewModel::class.java]

        setContent()
    }


    private fun setContent() {
        companiesAdapter = CompaniesAdapter()

        with(binding) {
            recyclerViewCompanies.adapter = companiesAdapter
            recyclerViewCompanies.layoutManager = LinearLayoutManager(
                this@CompaniesActivity,
                LinearLayoutManager.VERTICAL,
                false
            )
            companiesViewModel.isLoading()
                .observe(this@CompaniesActivity) { isLoading ->
                    progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
                }
        }

        companiesAdapter.onCompanyClickListener = {
            startActivity(CompanyInfoActivity.newIntent(this@CompaniesActivity, it))
        }

        companiesViewModel.getCompanies()
            .observe(this) { companies -> companiesAdapter.setCompanies(companies) }

        companiesViewModel.isError().observe(this) {
            Toast.makeText(this@CompaniesActivity, it, Toast.LENGTH_LONG).show()
        }
    }
}