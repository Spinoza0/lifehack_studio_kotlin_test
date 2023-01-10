package com.spinoza.lifehackstudiotestkotlin.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.spinoza.lifehackstudiotestkotlin.R
import com.spinoza.lifehackstudiotestkotlin.data.ApiFactory
import com.spinoza.lifehackstudiotestkotlin.databinding.ActivityCompanyInfoBinding
import com.spinoza.lifehackstudiotestkotlin.domain.CompanyItem
import com.spinoza.lifehackstudiotestkotlin.presentation.viewmodel.CompanyInfoViewModel
import com.spinoza.lifehackstudiotestkotlin.presentation.viewmodel.ViewModelFactory

class CompanyInfoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCompanyInfoBinding
    private lateinit var companyInfoViewModel: CompanyInfoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCompanyInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        companyInfoViewModel = ViewModelProvider(
            this,
            ViewModelFactory(ApiFactory.apiService)
        )[CompanyInfoViewModel::class.java]

        val companyItem = CompanyItem(
            intent.getIntExtra(EXTRA_COMPANY_ID, 0),
            intent.getStringExtra(EXTRA_COMPANY_NAME),
            intent.getStringExtra(EXTRA_COMPANY_IMG)
        )

        if (companyItem.id > 0) {
            setContent(companyItem)
        } else {
            finish()
        }
    }

    private fun setContent(companyItem: CompanyItem) {
        companyInfoViewModel.loadCompany(companyItem)

        companyInfoViewModel.company.observe(
            this
        ) { company ->
            with(binding) {
                Glide.with(imageViewLogo)
                    .load(company.getFullImgUrl())
                    .error(R.drawable.no_logo)
                    .into(imageViewLogo)

                setTextInfo(textViewCompanyName, text = company.name)
                setTextInfo(textViewCompanyDescription, text = company.description)
                setTextInfo(textViewCompanyPhone, getString(R.string.phone), company.phone)
                setTextInfo(textViewCompanyWww, getString(R.string.www), company.www)
                setTextInfo(textViewCompanyCoordinates,
                    getString(R.string.coordinates),
                    company.coordinates)
            }
        }
    }

    private fun setTextInfo(textView: TextView, title: String = "", text: String?) {
        if (text != null && text.isNotEmpty()) {
            val str = "$title$text"
            textView.text = str
        } else {
            textView.visibility = View.GONE
        }
    }

    companion object {
        const val EXTRA_COMPANY_ID = "id"
        const val EXTRA_COMPANY_NAME = "name"
        const val EXTRA_COMPANY_IMG = "img"

        fun newIntent(context: Context, company: CompanyItem): Intent {
            val intent = Intent(context, CompanyInfoActivity::class.java)
            with(intent) {
                putExtra(EXTRA_COMPANY_ID, company.id)
                putExtra(EXTRA_COMPANY_NAME, company.name)
                putExtra(EXTRA_COMPANY_IMG, company.img)
            }
            return intent
        }
    }
}