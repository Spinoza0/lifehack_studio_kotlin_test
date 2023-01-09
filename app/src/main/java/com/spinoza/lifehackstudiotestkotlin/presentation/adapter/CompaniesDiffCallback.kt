package com.spinoza.lifehackstudiotestkotlin.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.spinoza.lifehackstudiotestkotlin.domain.CompanyItem

class CompaniesDiffCallback : DiffUtil.ItemCallback<CompanyItem>() {
    override fun areItemsTheSame(oldItem: CompanyItem, newItem: CompanyItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CompanyItem, newItem: CompanyItem): Boolean {
        return oldItem.img == newItem.img && oldItem.name == newItem.name
    }
}