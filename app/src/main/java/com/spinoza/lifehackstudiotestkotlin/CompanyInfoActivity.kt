package com.spinoza.lifehackstudiotestkotlin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide

class CompanyInfoActivity : AppCompatActivity() {

    private lateinit var imageViewLogo: ImageView
    private lateinit var textViewCompanyName: TextView
    private lateinit var textViewCompanyDescription: TextView
    private lateinit var textViewCompanyPhone: TextView
    private lateinit var textViewCompanyWww: TextView
    private lateinit var textViewCompanyCoordinates: TextView
    private lateinit var companyInfoViewModel: CompanyInfoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_company_info)
        initViews()

        companyInfoViewModel = ViewModelProvider(this)[CompanyInfoViewModel::class.java]

        val companyId = intent.getIntExtra(EXTRA_COMPANY_ID, 0)
        if (companyId > 0) {
            setContent(companyId)
        } else {
            finish()
        }
    }

    private fun setContent(companyId: Int) {
        companyInfoViewModel.loadCompany(companyId)

        companyInfoViewModel.getCompany().observe(
            this
        ) { company ->
            Glide.with(imageViewLogo)
                .load(company.img)
                .error(R.drawable.no_logo)
                .into(imageViewLogo)

            setTextInfo(textViewCompanyName, text = company.name)
            setTextInfo(textViewCompanyDescription, text = company.description)
            setTextInfo(
                textViewCompanyPhone,
                getString(R.string.phone),
                company.phone
            )
            setTextInfo(
                textViewCompanyWww,
                getString(R.string.www),
                company.www
            )
            setTextInfo(
                textViewCompanyCoordinates,
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

    private fun initViews() {
        imageViewLogo = findViewById(R.id.imageViewLogo)
        textViewCompanyName = findViewById(R.id.textViewCompanyName)
        textViewCompanyDescription = findViewById(R.id.textViewCompanyDescription)
        textViewCompanyPhone = findViewById(R.id.textViewCompanyPhone)
        textViewCompanyWww = findViewById(R.id.textViewCompanyWww)
        textViewCompanyCoordinates = findViewById(R.id.textViewCompanyCoordinates)
    }

    companion object {
        const val EXTRA_COMPANY_ID = "id"

        fun newIntent(context: Context, companyId: Int): Intent {
            val intent = Intent(context, CompanyInfoActivity::class.java)
            intent.putExtra(EXTRA_COMPANY_ID, companyId)
            return intent
        }
    }
}