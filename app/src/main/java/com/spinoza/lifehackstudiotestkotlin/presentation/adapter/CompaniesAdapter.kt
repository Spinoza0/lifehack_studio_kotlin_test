package com.spinoza.lifehackstudiotestkotlin.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import com.spinoza.lifehackstudiotestkotlin.R
import com.spinoza.lifehackstudiotestkotlin.domain.CompanyItem

class CompaniesAdapter : ListAdapter<CompanyItem, CompanyViewHolder>(CompaniesDiffCallback()) {
    var onCompanyClickListener: ((CompanyItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CompanyViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.company_item, parent, false)
        )

    override fun onBindViewHolder(holder: CompanyViewHolder, position: Int) {
        with(holder) {
            with(getItem(position)) {
                Glide.with(itemView)
                    .load(getFullImgUrl())
                    .error(R.drawable.no_logo)
                    .into(holder.imageViewLogo)
                holder.textViewCompanyName.text = name
                holder.itemView.setOnClickListener {
                    onCompanyClickListener?.invoke(this)
                }
            }
        }
    }
}