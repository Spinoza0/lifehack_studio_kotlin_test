package com.spinoza.lifehackstudiotestkotlin

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class CompaniesActivity : AppCompatActivity() {
    private lateinit var recyclerViewCompanies: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var companiesAdapter: CompaniesAdapter
    private lateinit var companiesViewModel: CompaniesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_companies)
        initViews()
        companiesViewModel = ViewModelProvider(this)[CompaniesViewModel::class.java]
        setContent()
    }

    private fun initViews() {
        recyclerViewCompanies = findViewById(R.id.recyclerViewCompanies)
        progressBar = findViewById(R.id.progressBar)
        companiesAdapter = CompaniesAdapter()
        recyclerViewCompanies.adapter = companiesAdapter
        recyclerViewCompanies.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )

        companiesAdapter.setOnCompanyClickListener(object :
            CompaniesAdapter.OnCompanyClickListener {
            override fun onCompanyClick(company: CompanyItem) {
                startActivity(
                    CompanyInfoActivity.newIntent(
                        this@CompaniesActivity,
                        company.id
                    )
                )
            }
        })
    }

    private fun setContent() {
        companiesViewModel.getCompanies()
            .observe(this) { companies -> companiesAdapter.setCompanies(companies) }

        companiesViewModel.isLoading().observe(this) { isLoading ->
            if (isLoading)
                progressBar.visibility = View.VISIBLE
            else
                progressBar.visibility = View.GONE
        }
    }
}