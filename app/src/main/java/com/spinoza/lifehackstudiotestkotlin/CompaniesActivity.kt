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
        binding.recyclerViewCompanies.adapter = companiesAdapter
        binding.recyclerViewCompanies.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )

        companiesAdapter.setOnCompanyClickListener(
            object : CompaniesAdapter.OnCompanyClickListener {
                override fun onCompanyClick(company: CompanyItem) {
                    startActivity(
                        CompanyInfoActivity.newIntent(this@CompaniesActivity, company)
                    )
                }
            })

        companiesViewModel.getCompanies()
            .observe(this) { companies -> companiesAdapter.setCompanies(companies) }

        companiesViewModel.isLoading().observe(this) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }
}