package com.spinoza.lifehackstudiotestkotlin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.spinoza.lifehackstudiotestkotlin.CompaniesAdapter.CompanyViewHolder

class CompaniesAdapter : RecyclerView.Adapter<CompanyViewHolder>() {
    private var companies: List<CompanyItem> = ArrayList()
    private var onCompanyClickListener: OnCompanyClickListener? = null

    interface OnCompanyClickListener {
        fun onCompanyClick(company: CompanyItem)
    }

    override fun getItemCount() = companies.size

    class CompanyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageViewLogo: ImageView
        val textViewCompanyName: TextView

        init {
            imageViewLogo = itemView.findViewById(R.id.imageViewLogo)
            textViewCompanyName = itemView.findViewById(R.id.textViewCompanyName)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CompanyViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.company_item, parent, false)
        )


    override fun onBindViewHolder(holder: CompanyViewHolder, position: Int) {
        val company = companies[position]
        Glide.with(holder.itemView)
            .load(company.getUrl())
            .error(R.drawable.no_logo)
            .into(holder.imageViewLogo)
        holder.textViewCompanyName.text = company.getName()
        holder.itemView.setOnClickListener { onCompanyClickListener?.onCompanyClick(company) }
    }

    fun setCompanies(companies: List<CompanyItem>) {
        this.companies = companies
        notifyDataSetChanged()
    }

    fun setOnCompanyClickListener(onCompanyClickListener: OnCompanyClickListener) {
        this.onCompanyClickListener = onCompanyClickListener
    }
}