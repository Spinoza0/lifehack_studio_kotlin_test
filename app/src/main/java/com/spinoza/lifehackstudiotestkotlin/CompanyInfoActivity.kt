package com.spinoza.lifehackstudiotestkotlin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.spinoza.lifehackstudiotestkotlin.databinding.ActivityCompanyInfoBinding

class CompanyInfoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCompanyInfoBinding
    private lateinit var companyInfoViewModel: CompanyInfoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCompanyInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        companyInfoViewModel = ViewModelProvider(this)[CompanyInfoViewModel::class.java]

        val companyItem = CompanyItem(
            intent.getIntExtra(EXTRA_COMPANY_ID, 0),
            intent.getStringExtra(EXTRA_COMPANY_NAME),
            intent.getStringExtra(EXTRA_COMPANY_IMG)
        )

        if (companyItem.getId() > 0) {
            setContent(companyItem)
        } else {
            finish()
        }
    }

    private fun setContent(companyItem: CompanyItem) {
        companyInfoViewModel.loadCompany(companyItem)

        companyInfoViewModel.getCompany().observe(
            this
        ) { company ->
            Glide.with(binding.imageViewLogo)
                .load(company.getUrl())
                .error(R.drawable.no_logo)
                .into(binding.imageViewLogo)

            setTextInfo(binding.textViewCompanyName, text = company.getName())
            setTextInfo(binding.textViewCompanyDescription, text = company.description)
            setTextInfo(
                binding.textViewCompanyPhone,
                getString(R.string.phone),
                company.phone
            )
            setTextInfo(
                binding.textViewCompanyWww,
                getString(R.string.www),
                company.www
            )
            setTextInfo(
                binding.textViewCompanyCoordinates,
                getString(R.string.coordinates),
                company.coordinates
            )
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
            intent.putExtra(EXTRA_COMPANY_ID, company.getId())
            intent.putExtra(EXTRA_COMPANY_NAME, company.getName())
            intent.putExtra(EXTRA_COMPANY_IMG, company.getImg())
            return intent
        }
    }
}