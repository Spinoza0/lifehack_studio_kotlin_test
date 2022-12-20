package com.spinoza.lifehackstudiotestkotlin

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.spinoza.lifehackstudiotestkotlin.databinding.ActivityCompaniesBinding


class CompaniesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCompaniesBinding
    private lateinit var companiesAdapter: CompaniesAdapter
    private lateinit var companiesViewModel: CompaniesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCompaniesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        companiesViewModel = ViewModelProvider(this)[CompaniesViewModel::class.java]

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

        companiesAdapter.onCompanyClickListener = object : CompaniesAdapter.OnCompanyClickListener {
            override fun onCompanyClick(company: CompanyItem) {
                startActivity(CompanyInfoActivity.newIntent(this@CompaniesActivity, company))
            }
        }

        companiesViewModel.getCompanies()
            .observe(this) { companies -> companiesAdapter.setCompanies(companies) }
    }
}